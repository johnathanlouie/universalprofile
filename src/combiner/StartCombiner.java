
package combiner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Person;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import rest.Rest;

/**
 *
 * @author sashi
 */
public class StartCombiner
{
    public CombinerEngine ce;
    
    public StartCombiner()
    {
        this.ce = new CombinerEngine(new SpecialPersonChecker());
    }
    
    public LinkedList<Person> combine(LinkedList<LinkedList> collection)
    {
        for(int i=0; i<collection.size(); i++)
        {
            this.ce.addCollection(collection.get(i));
        }
        return this.ce.combineAll();
    }
    
    public LinkedList start(String coll1, String coll2)
    {
        LinkedList<LinkedList> collec;
        LinkedList<Person> combined, c1,c2;
        collec= new LinkedList();
        c1  = this.retrieveProfiles(coll1);
        c2  = this.retrieveProfiles(coll2);
        if(c1!=null && c1.size()>0)
            collec.add(c1);
        if(c2!=null && c2.size()>0)
            collec.add(c2);
        combined = this.combine(collec);
        return combined;
    }
    
    public LinkedList retrieveProfiles(String nameCollec)
    {
        LinkedList prof=null;
        
        try {
            String json = Rest.getAll(nameCollec);
            //System.out.println(Rest.getAll(nameCollec));
            System.out.println(json);
            prof = getAllPerson(json);
        } catch (Exception ex) {
            System.out.println(ex.toString());
            Logger.getLogger(StartCombiner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prof;
    }
 
    public static Person getPerson(JSONObject obj)
    {
        Person p = new Person();
        JSONObject jO = null;
        String first,last, city, state,phone, email;
        first="";last=""; city=""; state=""; phone=""; email="";
        Iterator it = obj.keys();
        while(it.hasNext())
        {
            String curK = (String) it.next();
            if(curK.equals("name"))
            {
                
                JSONObject temp = (JSONObject) obj.get("name");
                Iterator itTmp = temp.keys();
                while(itTmp.hasNext())
                {
                    String temp2 = (String) itTmp.next();
                    if(temp2.equals("first"))
                    {
                        first= (String)temp.get("first");
                    }
                    if(temp2.equals("last"))
                    {
                        last= (String)temp.get("last");
                    }
                
                }
            }
            else if(curK.equals("address"))
            {
                System.out.println(curK);
                System.out.println();
                JSONArray temp = (JSONArray)obj.get("address");
                for(int i=0; i<temp.length(); i++)
                {
                    JSONObject addJ = temp.getJSONObject(i);
                    Iterator itTmp = addJ.keys();
                    while(itTmp.hasNext())
                    {

                        String temp2 = (String) itTmp.next();
                        if(temp2.equals("city"))
                        {
                            city= (String)addJ.get("city");
                        }
                        if(temp2.equals("state"))
                        {
                            state= (String)addJ.get("state");
                        }

                    }
                }
                
            }
            else if(curK.equals("email"))
            {
                JSONArray temp = (JSONArray)obj.get("email");
                for(int i=0; i<temp.length(); i++)
                {
                    JSONObject em = temp.getJSONObject(i);
                    
                    /*em.
                    Iterator itTmp = addJ.keys();
                    while(itTmp.hasNext())
                    {
                        

                    }*/
                }
            }
        }
        
        p.setFirstName(first);
        p.setLastName(last);
        p.addEmail(email);
        p.setCity(city);
        return p;
    }
    
    public static LinkedList<Person> getAllPerson(String json)
    {
        LinkedList<Person> listP= new LinkedList();
        JSONArray jar = new JSONArray(json);
        for(int i=0; i<jar.length(); i++)
        {
           Person curP = getPerson(jar.getJSONObject(i));
           if(curP!=null)
               listP.add(curP);
        }
        
        
        return listP;
    }
    
    public static String getJSON(LinkedList<Person> list)
    {
        StringBuilder json= new StringBuilder();
        json.append("[");
        for(int i=0; i<list.size(); i++)
        {
            Person p = list.get(i);
            json.append("{");
            if(p.getFullName().length()>1)
            {
                json.append("\"name\":{");
                json.append("\"first\":");
                json.append("\"");
                json.append(p.getFirstName());
                json.append("\"");
                json.append(",");
                json.append("\"last\":");
                json.append("\"");
                json.append(p.getLastName());
                json.append("\"");
            }
            json.append("}");
            json.append(",");
            json.append("\"address\":");
            json.append("[");
            json.append("{");
            json.append("\"city\":");
            json.append("\"");
            json.append(p.getCity());
            json.append("\"");
            json.append(",");
            json.append("\"state\":");
            json.append("\"");
            json.append(p.getState());
            json.append("\"");
            json.append("}");
            json.append("]");
            json.append("},");
        }
        json.deleteCharAt(json.length()-1);
        json.append("]");
        return json.toString();
    }
    
    public static void main(String[]args)
    {
        if(args.length>3)
        { 
            StartCombiner sC = new StartCombiner();
            //LinkedList com = sC.start(args[2], args[3]);
            
            //make db entry
            
        }
              
        StartCombiner sC = new StartCombiner();
        LinkedList com = sC.start("facebook", "googleplus");
        //LinkedList<Person> pL =  sC.retrieveProfiles("facebook");
        System.out.println(com.size());
        for(int i=0; i<com.size(); i++)
        {
            System.out.println(com.get(i));
        }
        System.out.println(getJSON(com));
    }
}
