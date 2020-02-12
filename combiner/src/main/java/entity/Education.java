package entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.json.JSONObject;

/**
 * Class Education represents education of a Person.
 *
 * @author sashi
 */
public class Education implements Json {

	private String gpa;
	private String name;
	private List<String> major;
	private List<String> minor;
	private String level;

	public Education() {
		major = new LinkedList();
		minor = new LinkedList();
	}

	public void setGpa(double gpa) {
		this.gpa = String.valueOf(gpa);
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void addMajor(String major) {
		this.major.add(major);
	}

	@Override
	public String toJson() {
		StringBuilder str = new StringBuilder();
		str.append('{');
		str.append("\"name\":");
		str.append('"');
		str.append(name);
		str.append('"');
		str.append(',');
		str.append("\"gpa\":");
		str.append('"');
		str.append(gpa);
		str.append('"');
		str.append(',');
		str.append("\"major\":");
		str.append('[');
		for (String major : major) {
			str.append('"');
			str.append(major);
			str.append('"');
			str.append(',');
		}
		if (!major.isEmpty()) {
			str.deleteCharAt(str.length() - 1);
		}
		str.append(']');
		str.append(',');
		str.append("\"minor\":");
		str.append('[');
		for (String minor : minor) {
			str.append('"');
			str.append(minor);
			str.append('"');
			str.append(',');
		}
		if (!minor.isEmpty()) {
			str.deleteCharAt(str.length() - 1);
		}
		str.append(']');
		str.append(',');
		str.append("\"level\":");
		str.append('"');
		str.append(level);
		str.append('"');
		str.append('}');
		return str.toString();
	}

	public void setName(String name) {
		this.name = name;
	}

	public static Education fromJson(JSONObject json) {
		Education name = new Education();
		for (String property : json.keySet()) {
			switch (property) {
				case "gpa":
					name.gpa = json.getString(property);
					break;
				case "level":
					name.level = json.getString(property);
					break;
				case "major":
					for (Object email : json.getJSONArray(property)) {
						String x = (String) email;
						if (!x.isEmpty()) {
							name.major.add(x);
						}
					}
					break;
				case "minor":
					for (Object email : json.getJSONArray(property)) {
						String x = (String) email;
						if (!x.isEmpty()) {
							name.minor.add(x);
						}
					}
					break;
				case "name":
					name.name = json.getString(property);
					break;
				default:
					System.out.println("Education fromJson unknown property " + property);
			}
		}
		return name;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 23 * hash + Objects.hashCode(this.gpa);
		hash = 23 * hash + Objects.hashCode(this.name);
		hash = 23 * hash + Objects.hashCode(this.major);
		hash = 23 * hash + Objects.hashCode(this.minor);
		hash = 23 * hash + Objects.hashCode(this.level);
		return hash;
	}

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
		final Education other = (Education) obj;
		if (!Objects.equals(this.gpa, other.gpa)) {
			return false;
		}
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		if (!Objects.equals(this.level, other.level)) {
			return false;
		}
		if (!Objects.equals(this.major, other.major)) {
			return false;
		}
		if (!Objects.equals(this.minor, other.minor)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Education{" + "gpa=" + gpa + ", name=" + name + ", major=" + major + ", minor=" + minor + ", level=" + level + '}';
	}

}
