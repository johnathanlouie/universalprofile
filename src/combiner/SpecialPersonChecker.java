
package combiner;

import entity.Person;

/**
 *
 * SpecialPersonChecker class extends EntityChecker
 *  Checks similarity between two Person objects using weighted boolean
 *      String comparison
 * 
 *      Fields      Weight
 *      Email       10.0
 *      Name        6.0
 *      City        1.0
 *      ...         1.0
 * 
 *      Default threshold = 6.0   
 * @author sashi
 */
public class SpecialPersonChecker extends EntityChecker 
{
    
    public SpecialPersonChecker()
    {
        this.threshold = 6.0;
    }
    
    @Override
    public boolean isSimilar(Object o1, Object o2) 
    {
        return(this.similar(o1, o2)>this.threshold);
    }

    @Override
    public double similar(Object o1, Object o2) 
    {
        double sim;
        Person p1,p2;
        sim = 0.0;
        p1 = (Person)o1;
        p2 = (Person)o2;
        
        if(p1.getEmail().equals(p2.getEmail()))
            sim+= 10.0;
        if(p1.getFullName().toLowerCase().equals(p2.getFullName().toLowerCase()))
            sim+= 6.0;
        if(p1.getCity().toLowerCase().equals(p2.getCity().toLowerCase()))
            sim+= 1.0;
        if(p1.getState().toLowerCase().equals(p2.getState().toLowerCase()))
            sim+= 1.0;
        if(p1.getCountry().toLowerCase().equals(p2.getCountry().toLowerCase()))
            sim+= 1.0;
        if(p1.getBirthDate().toLowerCase().equals(p2.getBirthDate().toLowerCase()))
            sim+= 1.0;
        if(p1.getEducation().toLowerCase().equals(p2.getEducation().toLowerCase()))
            sim+= 1.0;
        
        return sim;
    }
    
}
