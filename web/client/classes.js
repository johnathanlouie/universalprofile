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

function CompatibleProfile(profile)
{
	this.name = new CompatibleName(profile.name);
	this.email = profile.email;
	this.address = new Array();
	for (let addr of profile.address)
	{
		this.address.push(new CompatibleAddress(addr));
	}
}

function CompatibleName(name)
{
	this.first = name.first;
	this.last = name.last;
}

function CompatibleAddress(address)
{
	this.city = address.city;
	this.state = address.state;
}

function CompatibleBirth(birth)
{}

function CompatibleSchool(school)
{}