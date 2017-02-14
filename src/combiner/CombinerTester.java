
package combiner;

import entity.Person;
import java.io.File;

import java.util.LinkedList;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.NodeList;


/**
 * Tester for CombineEngine
 * Currently I am using XML files to get my sample profiles.
 * 
 * 
 * @author sashi
 */
public class CombinerTester
{
   
     
    public static Person xmlPerson(Element profile)
    {
        Person per;
        per = new Person();
        per.setFirstName(profile.getElementsByTagName("FirstName").item(0).getTextContent());
        per.setMiddleName(profile.getElementsByTagName("MiddleName").item(0).getTextContent());
        per.setLastName(profile.getElementsByTagName("LastName").item(0).getTextContent());
        per.setEmail(profile.getElementsByTagName("Email").item(0).getTextContent());
        per.setBirthDate(profile.getElementsByTagName("BirthDate").item(0).getTextContent());
        per.setCity(profile.getElementsByTagName("City").item(0).getTextContent());
        per.setState(profile.getElementsByTagName("State").item(0).getTextContent());
        per.setCountry(profile.getElementsByTagName("Country").item(0).getTextContent());
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
    
    public static LinkedList<Person> xmlDocument(String xml)
    {
        LinkedList<Person> listP;
        listP = null;
        try
        {
            File fXmlFile = new File(xml);
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
    
    public static void printPersonCollection(LinkedList<Person> col)
    {
        for(int i=0; i<col.size(); i++)
        {
            System.out.println(col.get(i));
            System.out.println("##################################");
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        CombinerEngine combiner;
        String f1,f2;
        combiner = new CombinerEngine(new SpecialPersonChecker());
        //Please change this path on your system as this is a specific path
        // Or else get ready to Crash .......... Burrrrrr 
        f1= "/home/sashi/NetBeansProjects/Combiner/src/test/file.xml";
        f2= "/home/sashi/NetBeansProjects/Combiner/src/test/file2.xml";
        LinkedList c1 = xmlDocument(f2);
        LinkedList c2 = xmlDocument(f1);
        
        //printPersonCollection(c1);
        combiner.addCollection(c1);
        combiner.addCollection(c2);
        LinkedList c3  = combiner.combineAll();
        System.out.println(c3.size());
        printPersonCollection(c3);
    }
    
    //this is just a tester method for creating unique string values
    public static int code(String str)
    {
        int value;
        value = 0;
        
        for(int i=0; i<str.length(); i++)
        {
            value += ((i+1)*(int)str.charAt(i));
        }
        
        return value;
    }
    
}
