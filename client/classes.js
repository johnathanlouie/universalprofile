function Profile(name, sex, phone, email, address, birth, education)
{
	this.name = name;
	this.sex = sex;
	this.phone = phone;
	this.email = email;
	this.address = address;
	this.birth = birth;
	this.education = education;
}

function Name(first, middle, last)
{
	this.first = first;
	this.middle = middle;
	this.last = last;
}

function Birth(year, month, day)
{
	this.year = year;
	this.month = month;
	this.day = day;
}

function Address(number, street, apartment, city, state, zip)
{
	this.number = number;
	this.street = street;
	this.apartment = apartment;
	this.city = city;
	this.state = state;
	this.zipCode = zip;
}

function School(name, gpa, major, minor)
{
	this.name = name;
	this.gpa = gpa;
	this.major = major;
	this.minor = minor;
}