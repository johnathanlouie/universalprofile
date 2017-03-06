
package combiner;

/**
 * Abstract class EntityChecker implements SimilarityChecker
 *  Can be extended by Specific Entity Comparison objects 
 *  
 *  Current subclasses: SpecialPersonChecker, VectorPersonChecker
 * 
 * @author sashi
 */
public abstract class EntityChecker implements SimilarityChecker
{
    double threshold = 0.0;
    
    public void setThreshold(double newThreshold)
    {
        this.threshold = newThreshold;
    }
    
    public double getThreshold()
    {
        return this.threshold;
    }
}
