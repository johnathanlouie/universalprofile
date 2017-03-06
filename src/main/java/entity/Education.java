package entity;

import java.util.LinkedList;

/**
 * Class Education represents education of a Person.
 *
 * @author sashi
 */
public class Education {

	private double gpa;
	private String school;
	private LinkedList<String> majors;
	private String level;

	public Education() {
		this.gpa = 0.0;
		this.majors = new LinkedList();
	}

	@Override
	public String toString() {
		StringBuilder str;
		str = new StringBuilder();
		str.append(this.school);
		str.append(" ");
		str.append(Double.toString(this.gpa));
		str.append(" ");
		str.append(this.level);
		for (int i = 0; i < this.majors.size(); i++) {
			str.append(" ");
			str.append(this.majors.get(i));
		}
		return str.toString();
	}

	public boolean equals(Education ed) {
		boolean isEqual;
		isEqual = true;

		if (ed.gpa != this.gpa || !ed.level.equals(this.level) || !ed.school.equals(this.school) || !ed.majors.equals(this.majors)) {
			isEqual = false;
		}
		return isEqual;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public void setGpa(double gpa) {
		this.gpa = gpa;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void addMajor(String major) {
		majors.add(major);
	}
}
