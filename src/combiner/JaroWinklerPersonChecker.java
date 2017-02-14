
package combiner;

import entity.Person;
import info.debatty.java.stringsimilarity.JaroWinkler;

/**
 * * JaroWinklerPersonChecker class extends EntityChecker
 *  Checks similarity between two Person objects using Jaro-Winkler Distance between the attribute strings.
 *      
 *      Gives weight to fields like email, name, and city
 *
 *      Fields      Weight
 *      Email       5
 *      Name        2
 *      City        0.0
 *      ...         0.0
 * 
 *      Default threshold = 7.0 
 *      
 *
 * @author sashi
 */
public class JaroWinklerPersonChecker extends EntityChecker
{

    public JaroWinklerPersonChecker()
    {
        this.threshold=7.0;
    }
    
    @Override
    public boolean isSimilar(Object o1, Object o2) 
    {
        //System.out.println(this.similar(o1, o2));
        return this.similar(o1, o2)>this.threshold;
    }

    @Override
    public double similar(Object o1, Object o2) 
    {
        double sim, cosVal;
        Person p1,p2;
        JaroWinkler jaro ;
        sim = 1.0; cosVal = 0.0;
        p1 = (Person)o1;
        p2 = (Person)o2;
        jaro = new JaroWinkler();
        
        cosVal = jaro.similarity(p1.getEmail(), p2.getEmail());
        //System.out.println("cosVal email="+cosVal);
        if(cosVal>0.9) sim+=5+cosVal;
        
        cosVal = jaro.similarity(p1.getFullName(), p2.getFullName());
        if(cosVal>0.9) sim+=2+cosVal;
        
        cosVal = jaro.similarity(p1.getCity(), p2.getCity());
        if(cosVal>0.9) sim+=cosVal;
        
        cosVal = jaro.similarity(p1.getCountry(), p2.getCountry());
        if(cosVal>0.9) sim+=cosVal;
        
        cosVal = jaro.similarity(p1.getState(), p2.getState());
        if(cosVal>0.6) sim+=cosVal;
        
        String[]ed1,ed2;
        ed1 = p1.getEducation();
        ed2 = p2.getEducation();
        for(int i=0; i<ed1.length && i<ed2.length; i++)
        {
            cosVal = jaro.similarity(ed1[i],ed2[i]);
            if(cosVal>0.9) sim+=cosVal;
        }
       
        
        cosVal = jaro.similarity(p1.getBirthDate(), p2.getBirthDate());
        if(cosVal>0.9) sim+=cosVal;
        
        //System.out.println(p1.getName()+" ?= "+p2.getName());
        
        return sim;
    }
    
}
