
package entity;

import java.util.Iterator;
import java.util.LinkedList;


/**
 * PersonMerger class implements EntityOperator
 *  Merges (or adds) two profiles together
 *  Combines using the field that is largest in the two Person
 * @author sashi
 */
public class PersonMerger implements EntityOperator 
{

    @Override
    public Entity operate(Entity o1, Entity o2) 
    {
        Person merged;
        Iterator<String> field1, field2;
        merged = new Person();
        field1 = o1.fieldValuePair.keySet().iterator();
        field2 = o2.fieldValuePair.keySet().iterator();

        while(field1.hasNext())
        {
            String f1,f2;
            Object v1,v2;
            f1 = field1.next();
            f2 = field2.next();
            
            v1 = o1.fieldValuePair.get(f1);
            v2 = o2.fieldValuePair.get(f2);
            
            if(f1.equals("Education"))
            {
                
                v1 = this.getFullEducation(((Person)o1).getEducation());
                v2 = this.getFullEducation(((Person)o2).getEducation());
            }
            else if(f1.equals("Email"))
            {
                //do something similar to above
                
            }
            
            
            if(((String)v1).length()>((String)v2).length())
            {
                merged.fieldValuePair.put(f1, v1);
            }
            else
            {
                merged.fieldValuePair.put(f2, v2);
            }
            
        }
        
        return merged;
    }
    
    public String getFullEducation(String[] allEd)
    {
        StringBuilder full;
        full = new StringBuilder("");
        
        for(int i=0;i<allEd.length; i++)
        {
            full.append(allEd[i]);
            full.append(" ");
        }
        return full.toString();
    }
    
}
