
package combiner;

import entity.Person;
import java.io.File;
import java.util.LinkedList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author sashi
 */
public class PersonReader 
{
    public static LinkedList<String> xmlAllEmail(NodeList email)
    {
        //System.out.println(((Element)email.item(0)).getElementsByTagName("Email").getLength());
        LinkedList<String> emails;
        NodeList emList;
        emails = new LinkedList();
        emList = ((Element)email.item(0)).getElementsByTagName("Email");
        
        for(int i=0; i<emList.getLength(); i++)
        {
            if(emList.item(i).getTextContent().length()>1)
                emails.add(emList.item(i).getTextContent());
        }
        return emails;
    }
    
    public static Person xmlPerson(Element profile)
    {
        Person per;
        per = new Person();
        
        
        NodeList firstName= profile.getElementsByTagName("FirstName");
        NodeList middleName= profile.getElementsByTagName("MiddleName");
        NodeList lastName= profile.getElementsByTagName("LastName");
        NodeList gender= profile.getElementsByTagName("Gender");
        NodeList emails= profile.getElementsByTagName("Emails");
        NodeList bdate= profile.getElementsByTagName("BirthDate");
        NodeList city= profile.getElementsByTagName("City");
        NodeList state= profile.getElementsByTagName("State");
        NodeList country= profile.getElementsByTagName("Country");
        
        if(firstName.getLength()>0)
            per.setFirstName(firstName.item(0).getTextContent());
        if(middleName.getLength()>0)
            per.setMiddleName(middleName.item(0).getTextContent());
        if(lastName.getLength()>0)
            per.setLastName(lastName.item(0).getTextContent());
        if(emails.getLength()>0)
            per.setEmail(xmlAllEmail((NodeList)emails));
        if(bdate.getLength()>0)
            per.setBirthDate(bdate.item(0).getTextContent());
        if(city.getLength()>0)
            per.setCity(city.item(0).getTextContent());
        if(state.getLength()>0)
            per.setState(state.item(0).getTextContent());
        if(country.getLength()>0)
            per.setCountry(country.item(0).getTextContent());
        System.out.println("xmlPerson");
        return per;
    }
    
    public static LinkedList<Person> xmlAllPerson(Element profiles)
    {
        LinkedList<Person>  listP;
        NodeList profList;
        listP = new LinkedList();
        
        profList = profiles.getElementsByTagName("Profile");
        for(int i=0; i<profList.getLength(); i++)
        {
            listP.add(xmlPerson((Element) profList.item(i)));
        }
        return listP;
    }
    
    public static LinkedList<Person> xmlDocument(String xmlFile)
    {
        LinkedList<Person> listP;
        listP = null;
        try
        {
            File fXmlFile = new File(xmlFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            
            
            listP = xmlAllPerson(doc.getDocumentElement());
            //System.out.println("----------------------------");

           
        }
        catch(Exception ex)
        {
            System.out.println("error:"+ex.toString());
        }
        
        return listP;
    }
    
    public static void main(String[]args)
    {
        LinkedList<Person> pl = xmlDocument("/home/sashi/NetBeansProjects/universalprofile/src/combiner/filename.xml");
        System.out.println(pl.get(0));
    
    }
    

}
