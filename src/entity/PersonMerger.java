
package entity;

import java.util.HashMap;
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
                
                v1 = ((Person)o1).getEducationList();
                v2 = ((Person)o2).getEducationList();
                merged.fieldValuePair.put(f1, 
                        this.unionList((LinkedList)v1, ((LinkedList)v2)));
            }
            else if(f1.equals("Email"))
            {
                //do something similar to above
                v1= ((Person)o1).getEmailList();
                v2= ((Person)o2).getEmailList();
                merged.fieldValuePair.put(f1, 
                        this.unionList((LinkedList)v1, ((LinkedList)v2)));
                
            }
            else
            {
            
                if(((String)v1).length()>((String)v2).length())
                {
                    merged.fieldValuePair.put(f1, v1);
                }
                else
                {
                    merged.fieldValuePair.put(f2, v2);
                }
            }
        }
        
        return merged;
    }
    
    public LinkedList unionList(LinkedList l1, LinkedList l2)
    {
        HashMap<Object, Boolean> map;
        LinkedList l3;
        map = new HashMap();
        l3 = new LinkedList();
        
        for(int i=0; i<l1.size(); i++)
        {
            Boolean val;
            val = map.get(l1.get(i));
            if(val==null)
            {
                map.put(l1.get(i), true);
            }
        }
        
        for(int i=0; i<l2.size(); i++)
        {
            Boolean val;
            val = map.get(l2.get(i));
            if(val==null)
            {
                map.put(l2.get(i), true);
            }
        }
         
        l3.addAll(map.keySet());
        return l3;
    }
}
