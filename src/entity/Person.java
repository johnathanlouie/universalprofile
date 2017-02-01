
package entity;

import java.util.Iterator;
import java.util.Set;


/**
 *  Person class is a subclass of Entity 
 *  
 * @author sashi
 */
public class Person extends Entity
{
    /**
     * Following are the name of the fields of a Person object
     * These fields are keys to store and retrieve values from the (HashMap) fieldValuePair
     * of Person
     */
    private final String fieldName="Name";
    private final String fieldEducation="Education";
    private final String fieldEmail="Email";
    private final String fieldBirthDate = "DOB";
    private final String fieldCity = "City";
    private final String fieldState = "State";
    private final String fieldCountry = "Country";
    
    private final PersonMerger merger;
    
    
    public Person()
    {
        this.merger = new PersonMerger();
        this.init();
    }
    
    public void setName(String newName)
    {
        this.fieldValuePair.put(this.fieldName, newName);
    }
    
    public void setEducation(String newEducation)
    {
        this.fieldValuePair.put(this.fieldEducation, newEducation);
    }
    
    public void setEmail(String newEmail)
    {
        this.fieldValuePair.put(this.fieldEmail, newEmail);
    }
    
    public void setBirthDate(String newBirthD)
    {
        this.fieldValuePair.put(this.fieldBirthDate, newBirthD);
    }
    
    public void setCity(String newCity)
    {
        this.fieldValuePair.put(this.fieldCity, newCity);
    }
    
    public void setState(String newState)
    {
        this.fieldValuePair.put(this.fieldState, newState);
    }
    
    public void setCountry(String newCountry)
    {
        this.fieldValuePair.put(this.fieldCountry, newCountry);
    }
                
    public String getName()
    {
        return this.fieldValuePair.get(this.fieldName);
    }
    
    public String getEducation()
    {
        return this.fieldValuePair.get(this.fieldEducation);
    }
    
    public String getEmail()
    {
        return this.fieldValuePair.get(this.fieldEmail);
    }
       
    public String getBirthDate()
    {
        return this.fieldValuePair.get(this.fieldBirthDate);
    }
    
    public String getCity()
    {
        return this.fieldValuePair.get(this.fieldCity);
    }
    
    public String getState()
    {
        return this.fieldValuePair.get(this.fieldState);
    }
    
    public String getCountry()
    {
        return this.fieldValuePair.get(this.fieldCountry);
    }
         
    
    public final void init()
    {
        this.fieldValuePair.put(this.fieldName, "");
        this.fieldValuePair.put(this.fieldEmail, "");
        this.fieldValuePair.put(this.fieldEducation, "");
        this.fieldValuePair.put(this.fieldCity, "");
        this.fieldValuePair.put(this.fieldState, "");
        this.fieldValuePair.put(this.fieldCountry, "");
        this.fieldValuePair.put(this.fieldBirthDate, "");
    }

    @Override
    public Entity union(Entity e1, Entity e2) 
    {
        return (Person)this.merger.operate(e1, e2);
    }
    
    @Override
    public String toString()
    {
        StringBuilder str;
        str = new StringBuilder();
        Iterator<String> fields = this.fieldValuePair.keySet().iterator();
        while(fields.hasNext())
        {
            String field;
            field = fields.next();
            str.append(field);
            str.append(" = ");
            str.append(this.fieldValuePair.get(field));
            str.append("\n");
        }
        
        return str.toString();
    }
}
