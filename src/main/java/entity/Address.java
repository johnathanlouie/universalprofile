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
public class Address implements Json {

	private String number;
	private String street;
	private String apartment;
	private String city;
	private String state;
	private String country;

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 89 * hash + Objects.hashCode(this.number);
		hash = 89 * hash + Objects.hashCode(this.street);
		hash = 89 * hash + Objects.hashCode(this.apartment);
		hash = 89 * hash + Objects.hashCode(this.city);
		hash = 89 * hash + Objects.hashCode(this.state);
		hash = 89 * hash + Objects.hashCode(this.country);
		hash = 89 * hash + Objects.hashCode(this.zipCode);
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
		final Address other = (Address) obj;
		if (!Objects.equals(this.number, other.number)) {
			return false;
		}
		if (!Objects.equals(this.street, other.street)) {
			return false;
		}
		if (!Objects.equals(this.apartment, other.apartment)) {
			return false;
		}
		if (!Objects.equals(this.city, other.city)) {
			return false;
		}
		if (!Objects.equals(this.state, other.state)) {
			return false;
		}
		if (!Objects.equals(this.country, other.country)) {
			return false;
		}
		if (!Objects.equals(this.zipCode, other.zipCode)) {
			return false;
		}
		return true;
	}
	private String zipCode;

	public Address() {
		number = "";
		street = "";
		apartment = "";
		city = "";
		state = "";
		country = "";
		zipCode = "";
	}

	@Override
	public String toJson() {
		StringBuilder s = new StringBuilder();
		s.append('{');
		s.append("\"number\":");
		s.append('"');
		s.append(number);
		s.append('"');
		s.append(',');
		s.append("\"street\":");
		s.append('"');
		s.append(street);
		s.append('"');
		s.append(',');
		s.append("\"apartment\":");
		s.append('"');
		s.append(apartment);
		s.append('"');
		s.append(',');
		s.append("\"city\":");
		s.append('"');
		s.append(city);
		s.append('"');
		s.append(',');
		s.append("\"state\":");
		s.append('"');
		s.append(state);
		s.append('"');
		s.append(',');
		s.append("\"country\":");
		s.append('"');
		s.append(country);
		s.append('"');
		s.append(',');
		s.append("\"zipCode\":");
		s.append('"');
		s.append(zipCode);
		s.append('"');
		s.append('}');
		return s.toString();
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getApartment() {
		return apartment;
	}

	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public static Address fromJson(JSONObject json) {
		Address addr = new Address();
		for (String property : json.keySet()) {
			switch (property) {
				case "number":
					addr.setNumber(json.getString(property));
					break;
				case "street":
					addr.setStreet(json.getString(property));
					break;
				case "apartment":
					addr.setApartment(json.getString(property));
					break;
				case "city":
					addr.setCity(json.getString(property));
					break;
				case "state":
					addr.setState(json.getString(property));
					break;
				case "country":
					addr.setCountry(json.getString(property));
					break;
				case "zipCode":
					addr.setZipCode(json.getString(property));
					break;
				default:
					System.out.println("Address fromJson unknown property " + property);
			}
		}
		return addr;
	}

	@Override
	public String toString() {
		return "Address{" + "number=" + number + ", street=" + street + ", apartment=" + apartment + ", city=" + city + ", state=" + state + ", country=" + country + ", zipCode=" + zipCode + '}';
	}
}
