
package combiner;
import entity.Person;
import info.debatty.java.stringsimilarity.*;

/** 
 * VectorPersonChecker class extends EntityChecker
 *  Checks similarity between two Person objects using Vector Space model.
 *      Uses cosine similarity between the two Person Vectors to check for similarity 
 *      Gives weight to fields like email, name, and city
 *
 *      Fields      Weight
 *      Email       20.0
 *      Name        10.0
 *      City        5.0
 *      ...         0.0
 * 
 *      Default threshold = 15.0 
 *      
 *
 * @author sashi
 */
public class VectorPersonChecker extends EntityChecker
{
    
    public VectorPersonChecker()
    {
        this.threshold = 15.0;
    }
    
    @Override
    public boolean isSimilar(Object o1, Object o2) 
    {
        //System.out.println(this.similar(o1, o2));
        return (this.similar(o1, o2)>this.threshold);
    }

    @Override
    public double similar(Object o1, Object o2) 
    {
       
        double sim, cosVal;
        Person p1,p2;
        Cosine cos ;
        sim = 1.0; cosVal = 0.0;
        p1 = (Person)o1;
        p2 = (Person)o2;
        cos = new Cosine(11);
        
        cosVal = cos.similarity(p1.getEmail(), p2.getEmail());
        if(cosVal!=0.0) sim+=20.0+Math.log(cosVal);
        
        cosVal = cos.similarity(p1.getFullName(), p2.getFullName());
        if(cosVal!=0.0) sim+=10.0+Math.log(cosVal);
        
        cosVal = cos.similarity(p1.getCity(), p2.getCity());
        if(cosVal!=0.0) sim+=5.0+Math.log(cosVal);
        
        cosVal = cos.similarity(p1.getCountry(), p2.getCountry());
        if(cosVal!=0.0) sim+=Math.log(cosVal);
        
        cosVal = cos.similarity(p1.getState(), p2.getState());
        if(cosVal!=0.0) sim+=Math.log(cosVal);
        
        
        cosVal = cos.similarity(p1.getEducation(), p2.getEducation());
        if(cosVal!=0.0) sim+=Math.log(cosVal);
        
        
        cosVal = cos.similarity(p1.getBirthDate(), p2.getBirthDate());
        if(cosVal!=0.0) sim+=Math.log(cosVal);
        
        //System.out.println(p1.getName()+" ?= "+p2.getName());
        
        return sim;
    }
    
}
