
package entity;

import java.util.LinkedList;

/**
 *  Class Education represents education of a Person.
 * 
 * @author sashi
 */
public class Education
{
    double gpa;
    String school;
    LinkedList<String> major;
    String level;
    
    public Education()
    {
        this.gpa = 0.0;
        this.major = new LinkedList();
    }
    
    
    @Override
    public String toString()
    {
        StringBuilder str;
        str = new StringBuilder();
        str.append(this.school);
        str.append(" ");
        str.append(Double.toString(this.gpa));
        str.append(" ");
        str.append(this.level);
        for(int i=0; i<this.major.size(); i++)
        {
            str.append(" ");
            str.append(this.major.get(i));
        }
        return str.toString();
    }
}
