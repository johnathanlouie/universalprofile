package socialmedia.googlePlus;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.plus.*;
import com.google.api.services.plus.model.PeopleFeed;
import com.google.api.services.plus.model.Person;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entity.Education;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class GooglePlusExtractor {

    private static final String APPLICATION_NAME = "UniversalProfile";

    /** Directory to store user credentials. */
    private static final java.io.File DATA_STORE_DIR =
            new java.io.File(System.getProperty("user.home"), ".store/plus_sample");

    /**
     * Global instance of the {@link DataStoreFactory}. The best practice is to make it a single
     * globally shared instance across your application.
     */
    private static FileDataStoreFactory dataStoreFactory;

    /** Global instance of the HTTP transport.*/
    private static HttpTransport httpTransport;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static Plus plus;

    /** Authorizes the installed application to access user's protected data. */
    private static Credential authorize() throws Exception {
        // load client secrets
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(GooglePlusExtractor.class.getResourceAsStream("/client_secrets.json")));
        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
            System.out.println(
                    "Enter Client ID and Secret from https://code.google.com/apis/console/?api=plus "
                            + "into plus-cmdline-sample/src/main/resources/client_secrets.json");
            System.exit(1);
        }
        // set up authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets,
                Collections.singleton(PlusScopes.PLUS_ME)).setDataStoreFactory(
                dataStoreFactory).build();
        // authorize
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    private static void initializeSearch() {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
            // authorization
            Credential credential = authorize();
            // set up global Plus instance
            plus = new Plus.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();;
            return;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /** Get the profile for the authenticated user. */
    private static List<BasicDBObject> getProfiles() throws IOException {
        List<BasicDBObject> documentsToInsert = new ArrayList<>();
        BigInteger id = new BigInteger("100000000000000000000");

        for (;;) {
            if(documentsToInsert.size() > 50) break;
            id = id.add(BigInteger.ONE);
            System.out.println(id);
            Person profile;
            try{
                profile = plus.people().get(id.toString()).execute(); //plus.people().search("Mark").execute() *test id - 117862007149936554018*
            }
            catch (GoogleJsonResponseException e) {
                continue;
            }
            if(profile != null){
                List<Person.Organizations> personOrganizations = profile.getOrganizations();
                StringBuilder org = new StringBuilder();

                for(Person.Organizations organization : personOrganizations){
                    org.append(organization.getName() + ", ");
                }


                BasicDBObject person = new BasicDBObject("gender", profile.getGender())
                        .append("birthdate", profile.getBirthday())
                        .append("full_name", profile.getDisplayName())
                        .append("email", profile.getEmails().toString())
                        .append("personId", profile.getId())
                        .append("organizations", org.toString());
                documentsToInsert.add(person);
            }
        }

        return documentsToInsert;
    }

    private static List<BasicDBObject> getHardcodedIdProfiles() throws IOException {
        List<BasicDBObject> documentsToInsert = new ArrayList<>();
        documentsToInsert.add(getProfileById("117862007149936554018"));
        documentsToInsert.add(getProfileById("109116848218380695048"));
        documentsToInsert.add(getProfileById("100371831234606833066"));
        documentsToInsert.add(getProfileById("118311014595020491993"));
        documentsToInsert.add(getProfileById("114950978560072080765"));
        documentsToInsert.add(getProfileById("100110992705034515071"));
        documentsToInsert.add(getProfileById("101328142155738019653"));
        documentsToInsert.add(getProfileById("111600273142869149167"));
        documentsToInsert.add(getProfileById("113075362423639773428"));
        documentsToInsert.add(getProfileById("101503259830535887138"));
        return documentsToInsert;
    }

    private static BasicDBObject getProfileById(String id) {
        try {
            Person profile = plus.people().get(id).execute(); //plus.people().search("Mark").execute() *test id - 117862007149936554018*
            if(profile != null){
                List<Person.Organizations> personOrganizations = profile.getOrganizations();
                StringBuilder org = new StringBuilder();

                if(personOrganizations != null && personOrganizations.size() > 0){
                    for(Person.Organizations organization : personOrganizations){
                        org.append(organization.getName() + ", ");
                    }
                }

                BasicDBObject person = new BasicDBObject("gender", profile.getGender())
                        .append("birthdate", profile.getBirthday())
                        .append("full_name", profile.getDisplayName())
                        .append("email", profile.getEmails() != null ? profile.getEmails().toString() : null)
                        .append("personId", profile.getId())
                        .append("organizations", org.toString());
                return person;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<entity.Person> getGooglePlusUserByName(String fullName){
        initializeSearch();
        List<entity.Person> persons = new ArrayList<>();
        Long currentLimit = 5l;
        try {
            PeopleFeed people = plus.people().search(fullName).setMaxResults(currentLimit).execute();
            for(Person gPerson : people.getItems()){
                entity.Person entityPerson = convertGooglePersonToPersonEntity(plus.people().get(gPerson.getId()).execute());
                persons.add(entityPerson);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return persons;
    }

    private static entity.Person convertGooglePersonToPersonEntity(Person person){
        entity.Person entityPerson = new entity.Person();
        LinkedList<String> emails = new LinkedList<>();
        LinkedList<Education> educations = new LinkedList<>();
        if(person.getName() != null){
            entityPerson.setFirstName(person.getName().getGivenName());
            entityPerson.setLastName(person.getName().getFamilyName());
            entityPerson.setMiddleName(person.getName().getMiddleName() == null ? "" : person.getName().getMiddleName());
        }
        if(person.getEmails() != null && person.getEmails().size() > 0){
            for(Person.Emails email : person.getEmails()){
                emails.add(email.getValue());
            }
        }
        entityPerson.setEmail(emails);
        entityPerson.setBirthDate(person.getBirthday() != null ? person.getBirthday() : "");

        for(Person.Organizations organization : person.getOrganizations()){
            Education edu = new Education();
            edu.setSchool(organization.getName());
            educations.add(edu);
        }

        entityPerson.setEducation(educations);
        return entityPerson;
    }

    public static void main(String[] args) {
        MongoCredential mongoCredential = MongoCredential
                .createCredential("universal", "universalprofile",
                        "password123".toCharArray());
        ServerAddress adr = new ServerAddress("ds115870.mlab.com", 15870);
        MongoClient mongoClient = new MongoClient(adr, Arrays.asList(mongoCredential));
        MongoDatabase database = mongoClient.getDatabase("universalprofile");
        MongoCollection<BasicDBObject> collection = database.getCollection("GProfiles", BasicDBObject.class);
        initializeSearch();

        try {
            collection.insertMany(getHardcodedIdProfiles());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}


