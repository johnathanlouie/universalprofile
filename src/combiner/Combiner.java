
package combiner;

import entity.Person;
import java.io.IOException;
import static java.lang.Compiler.command;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import combiner.CombinerEngine;
/**
 *
 * @author sashi
 */
public class Combiner 
{
    public CombinerEngine ce;
    
    public Combiner()
    {
        this.ce = new CombinerEngine(new SpecialPersonChecker());
    }
    
    public LinkedList<Person> getFacebookProfiles(String fullName)
    {
        LinkedList<Person> fbProfiles = null;
        try {
            //start process
            Process myProcess = Runtime.getRuntime().exec(
                    "python /home/sashi/NetBeansProjects/universalprofile/src/socialmedia/facebook/fb.py "+fullName);
            System.out.println(myProcess.waitFor());
            
            //xml profiles should be written if profiles found on facebook
            // filename
            fbProfiles = PersonReader.xmlDocument("profiles.xml");
        } catch (IOException ex) {
            Logger.getLogger(Combiner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Combiner.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return fbProfiles;
    }
    
    public LinkedList<Person> getGooglePlusProfiles(String fullName)
    {
        
        return null;
    }
    
    public void retrieveProfiles(LinkedList<LinkedList<Person>> collection, String query)
    {
    
    
    }
    
    public LinkedList<Person> combine(LinkedList<LinkedList<Person>> collection)
    {
        this.ce.setCollection(collection);
        return this.ce.combineAll();
        
    }
    
    public void start(String query)
    {
        LinkedList<LinkedList<Person>> collec;
        LinkedList<Person> combined;
        collec= new LinkedList();
        this.retrieveProfiles(collec, query);
        combined = this.combine(collec);
        //store in the database 
        // 
    }
    public static void main(String[]args)
    {
        Combiner combiner = new Combiner();
        if(args.length>3)
        {
        
        }
        LinkedList<Person> fp = combiner.getFacebookProfiles("Sashi Thapaliya");
        if(fp!=null)
        {
            for(int i=0; i<fp.size(); i++)
            {
                System.out.println(fp.get(i));
            }
        }
    }
}
