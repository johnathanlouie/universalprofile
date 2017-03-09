package entity;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.json.JSONObject;

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
	private PersonName name;
	private String sex;
	private Date birth;
	private List<String> phone;
	private List<String> email;
	private List<Address> address;
	private List<Education> education;

	private final PersonMerger merger;

	public Person() {
		merger = new PersonMerger();
		name = new PersonName();
		sex = "";
		birth = new Date();
		phone = new LinkedList<>();
		email = new LinkedList<>();
		address = new LinkedList<>();
		address.add(new Address());
		education = new LinkedList<>();
		fieldValuePair.put(fieldFirstName, "");
		fieldValuePair.put(fieldMiddleName, "");
		fieldValuePair.put(fieldLastName, "");
		fieldValuePair.put(fieldEmail, email);
		fieldValuePair.put(fieldEducation, education);
		fieldValuePair.put(fieldCity, "");
		fieldValuePair.put(fieldState, "");
		fieldValuePair.put(fieldCountry, "");
		fieldValuePair.put(fieldBirthDate, "");
	}

	public void setFirstName(String newName) {
		fieldValuePair.put(fieldFirstName, newName);
		name.setFirst(newName);
	}

	public void setMiddleName(String newName) {
		fieldValuePair.put(fieldMiddleName, newName);
		name.setMiddle(newName);
	}

	public void setLastName(String newName) {
		fieldValuePair.put(fieldLastName, newName);
		name.setLast(newName);
	}

	public void addEducation(Education newEducation) {
		if (newEducation != null) {
			education.add(newEducation);
		}
	}

	public void setEducation(LinkedList<Education> newEducation) {
		if (newEducation != null) {
			if (newEducation.contains(null)) {
				throw new RuntimeException("null in list");
			}
			education = newEducation;
			fieldValuePair.put(fieldEducation, newEducation);
		}
	}

	public void addEmail(String newEmail) {
		if (!newEmail.isEmpty()) {
			email.add(newEmail);
		}
	}

	public void setEmail(LinkedList<String> newEmail) {
		if (newEmail != null) {
			email = newEmail;
			fieldValuePair.put(fieldEmail, newEmail);
		}
	}

	public void setBirthDate(String newBirthD) {
		birth.set(newBirthD);
		fieldValuePair.put(fieldBirthDate, newBirthD);
	}

	public void setCity(String newCity) {
		fieldValuePair.put(fieldCity, newCity);
		address.get(0).setCity(newCity);
	}

	public void setState(String newState) {
		fieldValuePair.put(fieldState, newState);
		address.get(0).setState(newState);
	}

	public void setCountry(String newCountry) {
		fieldValuePair.put(fieldCountry, newCountry);
		address.get(0).setCountry(newCountry);
	}

	public String getFullName() {
		return name.getFull();
	}

	public String getFirstName() {
		return name.getFirst();
	}

	public String getMiddleName() {
		return name.getMiddle();
	}

	public String getLastName() {
		return name.getLast();
	}

	public List<Education> getEducationList() {
		return new LinkedList<>(education);
	}

	public String getEducation() {
		StringBuilder str = new StringBuilder();
		for (Education edu : education) {
			str.append(edu.toString());
			str.append(" ");
		}
		return str.toString();
	}

	public String getEmail() {
		StringBuilder str = new StringBuilder("");
		for (String email : getEmailList()) {
			str.append(email);
			str.append(" ");
		}
		return str.toString();
	}

	public List<String> getEmailList() {
		return email;
	}

	public String getBirthDate() {
		return birth.get();
	}

	public String getCity() {
		return address.get(0).getCity();
	}

	public String getState() {
		return address.get(0).getState();
	}

	public String getCountry() {
		return address.get(0).getCountry();
	}

	public String toJson() {
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"name\":");
		json.append(name.toJson());
		json.append(",");
		json.append("\"sex\":");
		json.append('"');
		json.append(sex);
		json.append('"');
		json.append(',');

		json.append("\"phone\":");
		json.append('[');
		for (String phone : phone) {
			json.append('"');
			json.append(phone);
			json.append('"');
			json.append(',');
		}
		if (!phone.isEmpty()) {
			json.deleteCharAt(json.length() - 1);
		}
		json.append(']');
		json.append(',');

		json.append("\"email\":");
		json.append('[');
		for (String email : email) {
			json.append('"');
			json.append(email);
			json.append('"');
			json.append(',');
		}
		if (!email.isEmpty()) {
			json.deleteCharAt(json.length() - 1);
		}
		json.append(']');
		json.append(',');

		json.append("\"address\":");
		json.append("[");
		for (Address addr : address) {
			json.append(addr.toJson());
			json.append(',');
		}
		if (!address.isEmpty()) {
			json.deleteCharAt(json.length() - 1);
		}
		json.append("]");
		json.append(',');

		json.append("\"birth\":");
		json.append(birth.toJson());
		json.append(',');

		json.append("\"education\":");
		json.append("[");
		for (Education edu : education) {
			json.append(edu.toJson());
			json.append(',');
		}
		if (!education.isEmpty()) {
			json.deleteCharAt(json.length() - 1);
		}
		json.append("]");

		json.append('}');
		return json.toString();
	}

	@Override
	public Entity union(Entity e1, Entity e2) {
		Person p1 = (Person) e1;
		Person p2 = (Person) e2;
		Person p3 = new Person();
		p3.name = PersonName.union(p1.name, p2.name);
		p3.birth = Date.union(p1.birth, p2.birth);
		p3.sex = p1.sex.isEmpty() ? p2.sex : p1.sex;
		p3.address = Person.unionList(p1.address, p2.address);
		p3.email = Person.unionList(p1.email, p2.email);
		p3.phone = Person.unionList(p1.phone, p2.phone);
		p3.education = Person.unionList(p1.education, p2.education);
		return p3;
//		return (Person) merger.operate(e1, e2);
	}

	private static <T> List<T> unionList(List<T> l1, List<T> l2) {
		Set<T> set = new HashSet<>();
		set.addAll(l1);
		set.addAll(l2);
		return new LinkedList<>(set);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 79 * hash + Objects.hashCode(this.name);
		hash = 79 * hash + Objects.hashCode(this.sex);
		hash = 79 * hash + Objects.hashCode(this.birth);
		hash = 79 * hash + Objects.hashCode(this.phone);
		hash = 79 * hash + Objects.hashCode(this.email);
		hash = 79 * hash + Objects.hashCode(this.address);
		hash = 79 * hash + Objects.hashCode(this.education);
		hash = 79 * hash + Objects.hashCode(this.merger);
		return hash;
	}

//	@Override
//	public String toString() {
//		StringBuilder str;
//		str = new StringBuilder();
//		Iterator<String> fields = fieldValuePair.keySet().iterator();
//		while (fields.hasNext()) {
//			String field;
//			field = fields.next();
//			str.append(field);
//			str.append(" = ");
//			str.append(fieldValuePair.get(field));
//			str.append("\n");
//		}
//
//		return str.toString();
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Person other = (Person) obj;
		if (!Objects.equals(this.sex, other.sex)) {
			return false;
		}
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		if (!Objects.equals(this.birth, other.birth)) {
			return false;
		}
		if (!Objects.equals(this.phone, other.phone)) {
			return false;
		}
		if (!Objects.equals(this.email, other.email)) {
			return false;
		}
		if (!Objects.equals(this.address, other.address)) {
			return false;
		}
		if (!Objects.equals(this.education, other.education)) {
			return false;
		}
		if (!Objects.equals(this.merger, other.merger)) {
			return false;
		}
		return true;
	}

//	}
	public static Person fromJson(JSONObject json) {
		Person p = new Person();
		for (String property : json.keySet()) {
			switch (property) {
				case "name":
					p.name = PersonName.fromJson(json.getJSONObject(property));
					break;
				case "sex":
					p.sex = json.getString(property);
					break;
				case "phone":
					for (Object phone : json.getJSONArray(property)) {
						String x = (String) phone;
						if (!x.isEmpty()) {
							p.phone.add(x);
						}
					}
					break;
				case "email":
					for (Object email : json.getJSONArray(property)) {
						String x = (String) email;
						if (!x.isEmpty()) {
							p.addEmail(x);
						}
					}
					break;
				case "address":
					for (Object addr : json.getJSONArray(property)) {
						p.address.add(Address.fromJson(new JSONObject(addr)));
					}
					break;
				case "birth":
					p.birth = Date.fromJson(json.getJSONObject(property));
					break;
				case "education":
					for (Object edu : json.getJSONArray(property)) {
						JSONObject e = (JSONObject) edu;
						p.education.add(Education.fromJson(e));
					}
					break;
				case "_id":
					break;
				default:
					System.out.println("Person fromJson unknown property " + property);
			}
		}
		return p;
	}

	@Override
	public String toString() {
		return "Person{" + "name=" + name + ", sex=" + sex + ", birth=" + birth + ", phone=" + phone + ", email=" + email + ", address=" + address + ", education=" + education + '}';
	}

}
