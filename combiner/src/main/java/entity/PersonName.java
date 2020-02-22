/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Objects;
import org.json.JSONObject;

/**
 * @author jlouie
 */
public class PersonName implements Json {

    private String first;
    private String middle;
    private String last;

    public PersonName() {
        first = "";
        middle = "";
        last = "";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.first);
        hash = 37 * hash + Objects.hashCode(this.middle);
        hash = 37 * hash + Objects.hashCode(this.last);
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
        final PersonName other = (PersonName) obj;
        if (!Objects.equals(this.first, other.first)) {
            return false;
        }
        if (!Objects.equals(this.middle, other.middle)) {
            return false;
        }
        if (!Objects.equals(this.last, other.last)) {
            return false;
        }
        return true;
    }

    @Override
    public String toJson() {
        StringBuilder str = new StringBuilder();
        str.append('{');
        str.append("\"first\":");
        str.append('"');
        str.append(first);
        str.append('"');
        str.append(',');
        str.append("\"middle\":");
        str.append('"');
        str.append(middle);
        str.append('"');
        str.append(',');
        str.append("\"last\":");
        str.append('"');
        str.append(last);
        str.append('"');
        str.append('}');
        return str.toString();
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getFull() {
        StringBuilder str = new StringBuilder();
        if (!first.isEmpty()) {
            str.append(first);
            str.append(' ');
        }
        if (!middle.isEmpty()) {
            str.append(middle);
            str.append(' ');
        }
        str.append(last);
        return str.toString();
    }

    public static PersonName fromJson(JSONObject json) {
        PersonName name = new PersonName();
        for (String property : json.keySet()) {
            switch (property) {
                case "first":
                    name.setFirst(json.getString(property));
                    break;
                case "middle":
                    name.setMiddle(json.getString(property));
                    break;
                case "last":
                    name.setLast(json.getString(property));
                    break;
                default:
                    System.out.println("PersonName fromJson unknown property " + property);
            }
        }
        return name;
    }

    public static PersonName union(PersonName x, PersonName y) {
        PersonName z = new PersonName();
        z.first = x.first.isEmpty() ? y.first : x.first;
        z.middle = x.middle.isEmpty() ? y.middle : x.middle;
        z.last = x.last.isEmpty() ? y.last : x.last;
        return z;
    }

    @Override
    public String toString() {
        return "PersonName{" + "first=" + first + ", middle=" + middle + ", last=" + last + '}';
    }

}
