package combiner;

import entity.Person;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import rest.Rest;
import rest.RestException;

/**
 * @author sashi
 */
public class StartCombiner {

    public CombinerEngine ce;

    public StartCombiner() {
        this.ce = new CombinerEngine(new SpecialPersonChecker());
    }

    public LinkedList<Person> combine(LinkedList<LinkedList> collection) {
        for (LinkedList list : collection) {
            this.ce.addCollection(list);
        }
        return this.ce.combineAll();
    }

    public List<Person> start(String coll1, String coll2) {
        LinkedList<LinkedList> collec = new LinkedList();
        LinkedList<Person> combined, c1, c2;
        c1 = retrieveProfiles(coll1);
        c2 = retrieveProfiles(coll2);
        if (c1 != null && c1.size() > 0) {
            collec.add(c1);
        }
        if (c2 != null && c2.size() > 0) {
            collec.add(c2);
        }
        combined = combine(collec);
        return combined;
    }

    public LinkedList retrieveProfiles(String nameCollec) {
        String json = Rest.getAll(nameCollec);
        return jsonProfilesToPersonList(json);
    }

    public static LinkedList<Person> jsonProfilesToPersonList(String json) {
        LinkedList<Person> listP = new LinkedList();
        JSONArray jar = new JSONArray(json);
        for (Object xx : jar) {
            Person curP = Person.fromJson((JSONObject) xx);
            listP.add(curP);
        }
        return listP;
    }

    public static String getJSON(List<Person> list) {
        StringBuilder json = new StringBuilder();
        json.append('[');
        for (Person p : list) {
            json.append(p.toJson());
            json.append(',');
        }
        json.deleteCharAt(json.length() - 1);
        json.append(']');
        return json.toString();
    }

    public static void main(String[] args) {
        System.out.println("running StartCombiner");
        if (args.length == 3) {
            System.out.println("success: args num");
            String srcName1 = args[0];
            String srcName2 = args[1];
            String destName = args[2];
            StartCombiner sC = new StartCombiner();
            List<Person> com;
            try {
                com = sC.start(srcName1, srcName2);
                for (Person p : com) {
                    System.out.println(p);
                    System.out.println(p.toJson());
                }
                String json = getJSON(com);
                System.out.println("====start list of profiles after combining====");
                System.out.println(json);
                System.out.println("====end list of profiles after combining====");
                try {
                    Rest.insert(destName, json);
                    System.out.println("success: insert");
                } catch (Exception ex) {
                    System.out.println("failure: insert");
                }
            } catch (RestException ex) {
                System.out.println("failed to connect to server");
            }
        } else {
            System.out.println("failure: args num");
        }
    }
}
