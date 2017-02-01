
package combiner;

/**
 * EntityOperator interface is implemented by classes that perform some operation
 * like adding, subtracting on two Entity objects
 * Contains one function:
 *                  Entity operate(Entity o1, Entity o2)
 * @author sashi
 */
public interface EntityOperator 
{
    /**
     * Performs the specific operation between the two entities depending
     *  on the type of EntityOperator
     * @param o1 object to be operated
     * @param o2 object to be operated
     * @return the resulting Entity after operation
     */
    public Entity operate(Entity o1, Entity o2);
}
