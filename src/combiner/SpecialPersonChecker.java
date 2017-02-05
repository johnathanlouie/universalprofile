
package combiner;

import entity.Person;

/**
 *
 * SpecialPersonChecker class implements interface SimilarityChecker
 *  And checks similarity between two Person objects
 *  
 * @author sashi
 */
public class SpecialPersonChecker implements SimilarityChecker 
{
    private double threshold;
    
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
            sim+= 10;
        if(p1.getName().equals(p2.getName()))
            sim+= 6;
        if(p1.getCity().equals(p2.getCity()))
            sim+= 1;
        if(p1.getState().equals(p2.getState()))
            sim+= 1;
        if(p1.getCountry().equals(p2.getCountry()))
            sim+= 1;
        if(p1.getBirthDate().equals(p2.getBirthDate()))
            sim+= 1;
        if(p1.getEducation().equals(p2.getEducation()))
            sim+= 1;
        
        return sim;
    }
    
}
