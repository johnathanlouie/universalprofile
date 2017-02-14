
package entity;

import java.util.Iterator;
import java.util.LinkedList;
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
    private final String fieldFirstName="FirstName";
    private final String fieldMiddleName="MiddleName";
    private final String fieldLastName="LastName";
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
    
    public void setFirstName(String newName)
    {
        this.fieldValuePair.put(this.fieldFirstName, newName);
    }
    
    public void setMiddleName(String newName)
    {
        this.fieldValuePair.put(this.fieldMiddleName, newName);
    }
    
    public void setLastName(String newName)
    {
        this.fieldValuePair.put(this.fieldLastName, newName);
    }
    
    /**
     * 
     * @param newEducation is array representing all the attributes of one education
     *  Contains all the education attribute in the following format
     *      newEducation[0] => school name
     *      newEducation[1] => gpa
     *      newEducation[3] => major1
     *      newEducation[4] => major2
     *      ...             => majorN
     */
    public void setEducation(String[] newEducation)
    {
        Education ed;
        ed = new Education();
        
        ed.school = newEducation[0];
        ed.gpa = Double.parseDouble(newEducation[1]);
        for(int i=2; i<newEducation.length; i++)
            ed.major.add(newEducation[i]);
        
        this.fieldValuePair.put(this.fieldEducation, newEducation);
        
        this.setEducation(ed);
    }
    
    public void setEducation(Education newEducation)
    {
        ((LinkedList<Education>)this.fieldValuePair.get(this.fieldEducation)).add(newEducation);
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
                
    public String getFullName()
    {
        StringBuilder fullName;
        fullName = new StringBuilder();
        fullName.append(this.fieldValuePair.get(this.fieldFirstName));
        fullName.append(" ");
        fullName.append(this.fieldValuePair.get(this.fieldMiddleName));
        fullName.append(" ");
        fullName.append(this.fieldValuePair.get(this.fieldLastName));
        return fullName.toString();
    }
    
    public String getFirstName()
    {
        return (String) this.fieldValuePair.get(this.fieldFirstName);
    }
    
    public String getMiddleName()
    {
        return (String) this.fieldValuePair.get(this.fieldMiddleName);
    }
     
    public String getLastName()
    {
        return (String) this.fieldValuePair.get(this.fieldLastName);
    }
    
    /**
     * 
     * @return string representation of all the education of this person object
     *      Example:
     *              {"UCBerkeley, 3.6, Mathematics, Computer Science","Stanford, 4.0, Computer Science"}
     */
    public String[] getEducation()
    {
        LinkedList<Education> edList;
        String [] ed;
        edList = (LinkedList<Education>) this.fieldValuePair.get(this.fieldEducation);
        ed = new String[edList.size()];
        
        for(int i=0; i<edList.size(); i++)
        {
            Education cur; 
            cur = edList.get(i);
            ed[i] = cur.toString();

        }
        return ed;
    }
    
    public LinkedList<Education> getEducationList()
    {
        return (LinkedList<Education>)this.fieldValuePair.get(this.fieldEducation);
    }
    
    public String getEmail()
    {
        return (String) this.fieldValuePair.get(this.fieldEmail);
    }
       
    public String getBirthDate()
    {
        return (String) this.fieldValuePair.get(this.fieldBirthDate);
    }
    
    public String getCity()
    {
        return (String) this.fieldValuePair.get(this.fieldCity);
    }
    
    public String getState()
    {
        return (String) this.fieldValuePair.get(this.fieldState);
    }
    
    public String getCountry()
    {
        return (String) this.fieldValuePair.get(this.fieldCountry);
    }
         
    
    public final void init()
    {
        this.fieldValuePair.put(this.fieldFirstName, "");
        this.fieldValuePair.put(this.fieldMiddleName, "");
        this.fieldValuePair.put(this.fieldLastName, "");
        this.fieldValuePair.put(this.fieldEmail, "");
        this.fieldValuePair.put(this.fieldEducation, new LinkedList<Education>());
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
