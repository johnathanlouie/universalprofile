package entity;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Person class is a subclass of Entity
 *
 * @author sashi
 */
public class Person extends Entity {

	/**
	 * Following are the name of the fields of a Person object These fields are
	 * keys to store and retrieve values from the (HashMap) fieldValuePair of
	 * Person
	 */
	private final String fieldFirstName = "FirstName";
	private final String fieldMiddleName = "MiddleName";
	private final String fieldLastName = "LastName";
	private final String fieldEducation = "Education";
	private final String fieldEmail = "Email";
	private final String fieldBirthDate = "DOB";
	private final String fieldCity = "City";
	private final String fieldState = "State";
	private final String fieldCountry = "Country";

	private final PersonMerger merger;

	public Person() {
		this.merger = new PersonMerger();
		this.fieldValuePair.put(this.fieldFirstName, "");
		this.fieldValuePair.put(this.fieldMiddleName, "");
		this.fieldValuePair.put(this.fieldLastName, "");
		this.fieldValuePair.put(this.fieldEmail, new LinkedList<String>());
		this.fieldValuePair.put(this.fieldEducation, new LinkedList<Education>());
		this.fieldValuePair.put(this.fieldCity, "");
		this.fieldValuePair.put(this.fieldState, "");
		this.fieldValuePair.put(this.fieldCountry, "");
		this.fieldValuePair.put(this.fieldBirthDate, "");
	}

	public void setFirstName(String newName) {
		if (newName.length() > 0) {
			this.fieldValuePair.put(this.fieldFirstName, newName);
		}
	}

	public void setMiddleName(String newName) {
		if (newName.length() > 0) {
			this.fieldValuePair.put(this.fieldMiddleName, newName);
		}
	}

	public void setLastName(String newName) {
		if (newName.length() > 0) {
			this.fieldValuePair.put(this.fieldLastName, newName);
		}
	}

	/**
	 *
	 * @param newEducation is array representing all the attributes of one
	 * education Contains all the education attribute in the following format
	 * newEducation[0] => school name newEducation[1] => gpa newEducation[3] =>
	 * major1 newEducation[4] => major2 ... => majorN
	 */
	public void addEducation(String[] newEducation) {
		Education ed;
		ed = new Education();

		ed.setSchool(newEducation[0]);
		double gpa = Double.parseDouble(newEducation[1]);
		ed.setGpa(gpa);
		for (int i = 2; i < newEducation.length; i++) {
			ed.addMajor(newEducation[i]);
		}

		//this.fieldValuePair.put(this.fieldEducation, newEducation);
		this.addEducation(ed);
	}

	public void addEducation(Education newEducation) {
		if (newEducation != null) {
			((LinkedList<Education>) this.fieldValuePair.get(this.fieldEducation)).add(newEducation);
		}
	}

	public void setEducation(LinkedList<Education> newEducation) {
		if (newEducation != null) {
			this.fieldValuePair.put(this.fieldEducation, newEducation);
		}
	}

	public void addEmail(String newEmail) {
		if (newEmail.length() > 1) {
			((LinkedList<String>) this.fieldValuePair.get(this.fieldEmail)).add(newEmail);
		}
	}

	public void setEmail(LinkedList<String> newEmail) {
		if (newEmail != null) {
			this.fieldValuePair.put(fieldEmail, newEmail);
		}
	}

	public void setBirthDate(String newBirthD) {
		if (newBirthD.length() > 1) {
			this.fieldValuePair.put(this.fieldBirthDate, newBirthD);
		}
	}

	public void setCity(String newCity) {
		if (newCity.length() > 1) {
			this.fieldValuePair.put(this.fieldCity, newCity);
		}
	}

	public void setState(String newState) {
		if (newState.length() > 1) {
			this.fieldValuePair.put(this.fieldState, newState);
		}
	}

	public void setCountry(String newCountry) {
		if (newCountry.length() > 1) {
			this.fieldValuePair.put(this.fieldCountry, newCountry);
		}
	}

	public String getFullName() {
		StringBuilder fullName;
		fullName = new StringBuilder();
		fullName.append(this.fieldValuePair.get(this.fieldFirstName));
		fullName.append(" ");
		fullName.append(this.fieldValuePair.get(this.fieldMiddleName));
		fullName.append(" ");
		fullName.append(this.fieldValuePair.get(this.fieldLastName));
		return fullName.toString();
	}

	public String getFirstName() {
		return (String) this.fieldValuePair.get(this.fieldFirstName);
	}

	public String getMiddleName() {
		return (String) this.fieldValuePair.get(this.fieldMiddleName);
	}

	public String getLastName() {
		return (String) this.fieldValuePair.get(this.fieldLastName);
	}

	/**
	 *
	 * @return string representation of all the education of this person object
	 * Example: {"UCBerkeley, 3.6, Mathematics, Computer Science","Stanford,
	 * 4.0, Computer Science"}
	 */
	public String[] getAllEducation() {
		LinkedList<Education> edList;
		String[] ed;
		edList = (LinkedList<Education>) this.fieldValuePair.get(this.fieldEducation);
		ed = new String[edList.size()];

		for (int i = 0; i < edList.size(); i++) {
			Education cur;
			cur = edList.get(i);
			ed[i] = cur.toString();

		}
		return ed;
	}

	public LinkedList<Education> getEducationList() {
		return (LinkedList<Education>) this.fieldValuePair.get(this.fieldEducation);
	}

	public String getEducation() {
		StringBuilder allEd;
		String[] ed;
		allEd = new StringBuilder("");
		ed = this.getAllEducation();
		for (int i = 0; i < ed.length; i++) {
			allEd.append(ed[i]);
			allEd.append(" ");
		}

		return allEd.toString();
	}

	public String getEmail() {
		StringBuilder em;
		em = new StringBuilder("");
		for (String email : getEmailList()) {
			em.append(email);
			em.append(" ");
		}
		return em.toString();
	}

	public LinkedList<String> getEmailList() {
		return (LinkedList<String>) this.fieldValuePair.get(this.fieldEmail);
	}

	public String getBirthDate() {
		return (String) this.fieldValuePair.get(this.fieldBirthDate);
	}

	public String getCity() {
		return (String) this.fieldValuePair.get(this.fieldCity);
	}

	public String getState() {
		return (String) this.fieldValuePair.get(this.fieldState);
	}

	public String getCountry() {
		return (String) this.fieldValuePair.get(this.fieldCountry);
	}

	public String toJSON() {
		StringBuilder json = new StringBuilder();
		json.append("{");
		if (getFullName().length() > 1) {
			json.append("\"name\":{");
			json.append("\"first\":");
			json.append("\"");
			json.append(getFirstName());
			json.append("\"");
			json.append(",");
			json.append("\"last\":");
			json.append("\"");
			json.append(getLastName());
			json.append("\"");
		}
		json.append("}");
		json.append(",");
		json.append("\"address\":");
		json.append("[");
		json.append("{");
		json.append("\"city\":");
		json.append("\"");
		json.append(getCity());
		json.append("\"");
		json.append(",");
		json.append("\"state\":");
		json.append("\"");
		json.append(getState());
		json.append("\"");
		json.append("}");
		json.append("]");
		List<String> emailList = getEmailList();
		if (emailList.size() > 0) {
			json.append(",\"email\":");
			json.append("[");
			for (String email : emailList) {
				json.append("\"");
				json.append(email);
				json.append("\"");
				json.append(",");
			}
			json.deleteCharAt(json.length() - 1);
			json.append("]");
		}
		json.append("}");
		return json.toString();
	}

	@Override
	public Entity union(Entity e1, Entity e2) {
		return (Person) this.merger.operate(e1, e2);
	}

	@Override
	public String toString() {
		StringBuilder str;
		str = new StringBuilder();
		Iterator<String> fields = this.fieldValuePair.keySet().iterator();
		while (fields.hasNext()) {
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
