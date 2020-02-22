package combiner;

import entity.Person;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author sashi
 */
public class Combiner {

    public CombinerEngine ce;

    public Combiner() {
        this.ce = new CombinerEngine(new SpecialPersonChecker());
    }

    public LinkedList getFacebookProfiles(String fullName) {
        LinkedList fbProfiles = null;
        try {
            // start process
            Process myProcess = Runtime.getRuntime().exec(
                    "python /home/sashi/NetBeansProjects/universalprofile/src/socialmedia/facebook/fb.py " + fullName);
            System.out.println(myProcess.waitFor());

            // xml profiles should be written if profiles found on facebook
            // filename
            fbProfiles = PersonReader.xmlDocument("profiles.xml");
        } catch (IOException ex) {
            Logger.getLogger(Combiner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Combiner.class.getName()).log(Level.SEVERE, null, ex);
        }

        return fbProfiles;
    }

    public LinkedList getGooglePlusProfiles(String fullName) {
        LinkedList gProfiles = null;
        gProfiles = PersonReader.xmlDocument("/home/sashi/NetBeansProjects/universalprofile/src/test/file.xml");
        return gProfiles;
    }

    public void retrieveProfiles(LinkedList<LinkedList> collection, String query) {
        LinkedList fb, gP;
        fb = this.getFacebookProfiles(query);
        gP = this.getGooglePlusProfiles(query);
        if (fb != null && fb.size() > 0) {
            collection.add(fb);
        }
        if (gP != null && gP.size() > 0) {
            collection.add(gP);
        }

    }

    public LinkedList<Person> combine(LinkedList<LinkedList> collection) {
        for (int i = 0; i < collection.size(); i++) {
            this.ce.addCollection(collection.get(i));
        }
        return this.ce.combineAll();
    }

    public LinkedList start(String query) {
        LinkedList<LinkedList> collec;
        LinkedList<Person> combined;
        collec = new LinkedList();
        this.retrieveProfiles(collec, query);
        combined = this.combine(collec);
        // store in the database 

        return combined;
    }

    public static void main(String[] args) {
        Combiner combiner = new Combiner();
        LinkedList cmb = combiner.start("Sashi Thapaliya");
        for (int i = 0; i < cmb.size(); i++) {
            System.out.println((Person) cmb.get(i));
        }
        System.exit(1);
        if (args.length > 3) {

        }
        LinkedList<Person> fp = combiner.getFacebookProfiles("Sashi Thapaliya");
        if (fp != null) {
            for (int i = 0; i < fp.size(); i++) {
                System.out.println(fp.get(i));
            }
        }
    }
}
