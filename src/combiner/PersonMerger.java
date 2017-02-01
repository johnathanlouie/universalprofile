
package combiner;

import java.util.Iterator;


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
            String f1,f2,v1,v2;
            f1 = field1.next();
            f2 = field2.next();

            v1 = o1.fieldValuePair.get(f1);
            v2 = o2.fieldValuePair.get(f2);
            
            if(v1.length()>v2.length())
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
    
}
