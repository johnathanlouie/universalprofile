/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Objects;
import org.json.JSONObject;

/**
 *
 * @author jlouie
 */
public class Date implements Json {

    private String year;
    private String month;
    private String day;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.year);
        hash = 71 * hash + Objects.hashCode(this.month);
        hash = 71 * hash + Objects.hashCode(this.day);
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
        final Date other = (Date) obj;
        if (!Objects.equals(this.year, other.year)) {
            return false;
        }
        if (!Objects.equals(this.month, other.month)) {
            return false;
        }
        if (!Objects.equals(this.day, other.day)) {
            return false;
        }
        return true;
    }

    public Date() {
        year = "";
        month = "";
        day = "";
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void set(String date) {
        String[] dateArray = date.split("-");
        if (dateArray.length != 3) {
            throw new RuntimeException("set date wrong format");
        }
        year = dateArray[0];
        month = dateArray[1];
        day = dateArray[2];
    }

    public String get() {
        StringBuilder str = new StringBuilder();
        str.append(year);
        str.append('-');
        str.append(month);
        str.append('-');
        str.append(day);
        return str.toString();
    }

    @Override
    public String toJson() {
        StringBuilder s = new StringBuilder();
        s.append('{');
        s.append("\"year\":");
        s.append('"');
        s.append(year);
        s.append('"');
        s.append(',');
        s.append("\"month\":");
        s.append('"');
        s.append(month);
        s.append('"');
        s.append(',');
        s.append("\"day\":");
        s.append('"');
        s.append(day);
        s.append('"');
        s.append('}');
        return s.toString();
    }

    public static Date fromJson(JSONObject json) {
        Date x = new Date();
        for (String property : json.keySet()) {
            switch (property) {
                case "year":
                    x.year = json.getString(property);
                    break;
                case "month":
                    x.month = json.getString(property);
                    break;
                case "day":
                    x.day = json.getString(property);
                    break;
                default:
                    System.out.println("Date fromJson unknown property " + property);
            }
        }
        return x;
    }

    public static Date union(Date x, Date y) {
        Date z = new Date();
        z.year = x.year.isEmpty() ? y.year : x.year;
        z.month = x.month.isEmpty() ? y.month : x.month;
        z.day = x.day.isEmpty() ? y.day : x.day;
        return z;
    }

    @Override
    public String toString() {
        return "Date{" + "year=" + year + ", month=" + month + ", day=" + day + '}';
    }

}
