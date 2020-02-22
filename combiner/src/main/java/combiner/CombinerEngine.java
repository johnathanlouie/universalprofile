package combiner;

import entity.Entity;
import entity.Person;
import java.util.LinkedList;

/**
 * CombinerEngine class has the functionality of combining collections of
 * similar Entity Objects to one collection.
 *
 * Input-> C1, C2 Entity1 Entity1 Entity2 Entity2 Entity3
 *
 * Output-> FinalC Entity1 = C1[Entity1]+C2[Entity2] Entity2 =
 * C1[Entity2]+C2[Entity1] Entity3 Here Entity1 and Entity2 from C1 and C2 are
 * similar and are combined Similarly, Entity2 and Entity1 from C1 and C2 are
 * similar and are combined to one Entity.
 *
 * SimilarityChecker object is used to check the similarity between every two
 * Entity Objects And SimilarityChecker needs to be given by the user before
 * combining. And the specific Entity object's union(Entity e1, Entity e2); is
 * used to combine similar objects.
 *
 * IMPORTANT: There are mainly two things CombinerEngine depends on to work
 * properly. 1)Collections of proper type need to set 2)SimilarityChecker needs
 * to be set.
 *
 * @author sashi
 */
public class CombinerEngine {

    public SimilarityChecker checker;
    public LinkedList<LinkedList<Entity>> collection;

    public CombinerEngine(SimilarityChecker newChecker) {
        this(newChecker, new LinkedList());
    }

    public CombinerEngine(SimilarityChecker newChecker,
            LinkedList<LinkedList<Entity>> newCollec) {
        this.checker = newChecker;
        this.collection = newCollec;
    }

    public void setSimilarityChecker(SimilarityChecker newChecker) {
        this.checker = newChecker;
    }

    public void setCollection(LinkedList<LinkedList<Entity>> newCollec) {
        if (newCollec != null) {
            this.collection = newCollec;
        }
    }

    public void addCollection(LinkedList<Entity> newCollec) {
        if (newCollec != null) {
            this.collection.add(newCollec);
        }
    }

    public LinkedList combineAll() {
        return this.combineAll(this.collection);
    }

    public LinkedList combineAll(LinkedList<LinkedList<Entity>> newCollec) {
        LinkedList<Entity> curCol;
        curCol = null;
        try {
            curCol = newCollec.get(0);

            // combine entities in different collections
            for (int i = 1; i < newCollec.size(); i++) {
                curCol = this.combine(curCol, newCollec.get(i));
            }
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Collection to be combined: " + newCollec.size());
            System.out.println("No collection to combine");
        }
        return curCol;
    }

    public LinkedList combine(LinkedList<Entity> c1, LinkedList<Entity> c2) {
        LinkedList<Entity> comb, c1Clone, c2Clone;
        comb = new LinkedList();
        c1Clone = (LinkedList<Entity>) c1.clone();
        c2Clone = (LinkedList<Entity>) c2.clone();

        for (int i = 0; i < c1Clone.size(); i++) {
            Person curP = (Person) c1Clone.get(i);
            for (int j = 0; j < c2Clone.size(); j++) {
                if (this.checker.isSimilar(curP, c2Clone.get(j))) {
                    comb.add(curP.union(curP, c2Clone.get(j)));
                    c1Clone.remove(i);
                    c2Clone.remove(j);
                    i--;
                    break;
                }
            }
        }
        for (; !c1Clone.isEmpty();) {
            comb.add(c1Clone.removeFirst());
        }
        for (; !c2Clone.isEmpty();) {
            comb.add(c2Clone.removeFirst());
        }

        return comb;
    }

}
