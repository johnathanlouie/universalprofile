package combiner;

/**
 * SimilarityChecker is an interface implemented by classes that are responsible
 * for checking similarity between two or more objects.
 *
 * @author sashi
 */
public interface SimilarityChecker {

    /**
     * Calculates similarity between object o1 and o2.
     *
     * @param o1 object to be compared
     * @param o2 object to be compared
     * @return true if o1 is similar to o2
     *         false if else
     */
    public boolean isSimilar(Object o1, Object o2);

    /**
     * Calculates similarity between object o1 and o2.
     *
     * @param o1 object to be compared
     * @param o2 object to be compared
     * @return the similarity value between o1 and o2
     */
    public double similar(Object o1, Object o2);
}
