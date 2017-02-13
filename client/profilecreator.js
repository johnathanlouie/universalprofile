function manualCreate()
{
	var profile = {};
	profile.name = {};
	profile.name.first = $("#fname").val();
	profile.name.middle = $("#mname").val();
	profile.name.last = $("#lname").val();
	profile.phone = [];
	profile.phone.push($("#phone1").val());
	profile.phone.push($("#phone2").val());
	profile.phone.push($("#phone3").val());
	profile.email = [];
	profile.email.push($("#email1").val());
	profile.email.push($("#email2").val());
	profile.email.push($("#email3").val());
	profile.gender = $("#gender").val();
	profile.birth = {};
	profile.birth.year = $("#byr").val();
	profile.birth.month = $("#bmnth").val();
	profile.birth.day = $("#bday").val();
	var addr1 = {};
	addr1.street = {};
	addr1.street.number = $("#addrnum1").val();
	addr1.street.name = $("#stname1").val();
	addr1.street.type = $("#sttype1").val();
	addr1.street.direction = $("#dir1").val();
	addr1.street.apartmentNumber = $("#aptnum1").val();
	addr1.city = $("#city1").val();
	addr1.state = $("#state1").val();
	addr1.zipCode = $("#zip1").val();
	var addr2 = {};
	addr2.street = {};
	addr2.street.number = $("#addrnum2").val();
	addr2.street.name = $("#stname2").val();
	addr2.street.type = $("#sttype2").val();
	addr2.street.direction = $("#dir2").val();
	addr2.street.apartmentNumber = $("#aptnum2").val();
	addr2.city = $("#city2").val();
	addr2.state = $("#state2").val();
	addr2.zipCode = $("#zip2").val();
	profile.address = [addr1, addr2];
	var school1 = {};
	school1.name = $("#school1").val();
	school1.gpa = $("#gpa1").val();
	school1.major = [$("#mjr11").val(), $("#mjr12").val()];
	school1.minor = [$("#min11").val(), $("#min12").val()];
	var school2 = {};
	school2.name = $("#school2").val();
	school2.gpa = $("#gpa2").val();
	school2.major = [$("#mjr21").val(), $("#mjr22").val()];
	school2.minor = [$("#min21").val(), $("#min22").val()];
	var school3 = {};
	school3.name = $("#school3").val();
	school3.gpa = $("#gpa3").val();
	school3.major = [$("#mjr31").val(), $("#mjr32").val()];
	school3.minor = [$("#min31").val(), $("#min32").val()];
	profile.education = [school1, school2, school3];
	send(profile, $("#src").val());
}

// chance a user fills out a field
var chance =
{
	firstName: 1,
	middleName: 0.1,
	lastName: 1,
	birth: 0.7,
	birthYear: 0.4,
	address: 0.05
};
// average number of phones, emails, schools, etc attended for users
// from 0 to any positive real number
var avgNum =
{
	email: 2,
	phone: 1.2,
	school: 1.3
};
const profileNum = 1000;
function autoCreate()
{
	var male = textareaList("#male");
	var female = textareaList("#female");
	var last = textareaList("#last");
	var words = textareaList("#words");
	var street = textareaList("#street");
	var city = textareaList("#city");
	var state = textareaList("#state");
	var school = textareaList("#school");
	var subject = textareaList("#subject");
	var profiles = [];
	for (let x = 0; x < profileNum; x++)
	{
		var profile = {};
		profile.gender = genderGen();
		profile.name = {};
		if (profile.gender === "male")
		{
			profile.name.first = pickRandom(male);
			profile.name.middle = thisOrEmpty(pickRandom(male), chance.middleName);
		}
		else
		{
			profile.name.first = pickRandom(female);
			profile.name.middle = thisOrEmpty(pickRandom(female), chance.middleName);
		}
		profile.name.last = pickRandom(last);
		profile.birth = {};
		if (randomChance(chance.birth))
		{
			profile.birth.year = thisOrEmpty(byearGen(), chance.birthYear);
			profile.birth.month = bmonthGen();
			profile.birth.day = bdayGen(profile.birth.month);
		}
		else
		{
			profile.birth.year = "";
			profile.birth.month = "";
			profile.birth.year = "";
		}
		profile.phone = [];
		for (let i = 0, max = 4; i < max; i++)
		{
			if (randomChance(avgChance(avgNum.phone, max)))
			{
				profile.phone.push(phoneGen());
			}
		}
		profile.email = [];
		for (let i = 0, max = 4; i < max; i++)
		{
			if (randomChance(avgChance(avgNum.email, max)))
			{
				profile.email.push(emailGen(profile.name.first, profile.name.middle, profile.name.last, profile.birth.year, profile.birth.month, profile.birth.day, words));
			}
		}
		profile.address =
		[
			thisOrEmpty({
				city: pickRandom(city),
				state: pickRandom(state),
				zipCode: zipGen(),
				street:
				{
					apartmentNumber: thisOrEmpty(1 + Math.round(Math.random() * 9999), 0.1),
					name: pickRandom(street),
					number: stNumGen(),
					type: pickRandom(stTypes),
					direction: (() => {
						let rand = Math.random();
						if (rand < 0.125)
						{
							return "N";
						}
						else if (rand < 0.25)
						{
							return "S";
						}
						else if (rand < 0.375)
						{
							return "E";
						}
						else if (rand < 0.5)
						{
							return "W";
						}
						else
						{
							return "";
						}
					})()
				}
			}, 0.4)
		];
		profile.education = [];
		for (let i = 0, max = 4; i < max; i++)
		{
			if (randomChance(avgChance(avgNum.school, max)))
			{
				profile.education.push(schoolGen(school, subject));
			}
		}
		profiles.push(profile);
	}
	cleanObject(profiles);
	$("#test").text(JSON.stringify(profiles));
}

function genderGen()
{
	return randomChance(0.5) ? "male" : "female";
}

function schoolGen(schoolNames, subjects)
{
	var school = {};
	school.gpa = gpaGen();
	school.name = pickRandom(schoolNames);
	school.major = [];
	school.minor = [];
	school.major.push(pickRandom(subjects));
	if (randomChance(0.05))
	{
		school.major.push(pickRandom(subjects));
	}
	if (randomChance(0.05))
	{
		school.minor.push(pickRandom(subjects));
	}
	if (randomChance(0.05))
	{
		school.minor.push(pickRandom(subjects));
	}
	return school;
}

var providers =
[
	"hotmail",
	"live",
	"gmail",
	"yahoo",
	"outlook",
	"icloud"
];

var stTypes =
[
	"st",
	"blvd",
	"ave",
	"rd",
	"ln",
	"ct",
	"dr"
];

function stNumGen()
{
	return 1 + Math.round(Math.random() * 9999);
}

function emailGen(first, middle, last, byear, bmonth, bday, words)
{
	if (randomChance(1 / 3))
	{
		return `${first}.${last}@${pickRandom(providers)}.com`.toLowerCase();
	}
	return `${pickRandom(words)}${pickRandom(words)}${Math.round(Math.random() * 99999)}@${pickRandom(providers)}.com`.toLowerCase();
}

function zipGen()
{
	var str = "";
	for (let i = 0; i < 5; i++)
	{
		str += Math.round(Math.random() * 9);
	}
	return str;
}

function bdayGen(month)
{
	var days;
	switch (Number(month))
	{
		case 2:
			days = 28;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			days = 30;
			break;
		default:
			days = 31;
	}
	return String(1 + Math.round(Math.random() * (days - 1)));
}

function bmonthGen()
{
	return String(1 + Math.round(Math.random() * 11));
}

function byearGen()
{
	return String(2017 - 120 + Math.round(Math.random() * 120));
}

// returns chance 0 to 1
function avgChance(avg, tries)
{
	return avg / tries;
}

function thisOrEmpty(value, chance)
{
	if (randomChance(chance))
	{
		return value;
	}
	return "";
}

function phoneGen()
{
	var str = "";
	for (let i = 0; i < 10; i++)
	{
		str += Math.round(Math.random() * 9);
	}
	return str;
}

function pickRandom(array)
{
	return array[Math.round(Math.random() * (array.length - 1))];
}

// chance to be true
// from 0 to 1
function randomChance(chance)
{
	return chance >= 1 || Math.random() < chance;
}

function gpaGen()
{
	return String(Math.round(Math.random() * 500) / 100);
}

function textareaList(id)
{
	var str = $(id).text();
	str = str.replace(/\n\r/g, "\n").replace(/\r/g, "\n");
	var a = str.split("\n");
	for (let i = a.length - 1; i >= 0; i--)
	{
		a[i] = a[i].trim();
		if (a[i].length === 0)
		{
			a.splice(i, 1);
		}
	}
	return a;
}

// returns true if object is not empty after cleaning
function cleanObject(obj)
{
	var props = Object.keys(obj);
	for (let p of props)
	{
		if (typeof obj[p] === "string")
		{
			obj[p] = obj[p].trim();
			if (obj[p].length === 0)
			{
				delete obj[p];
			}
		}
		else if (Array.isArray(obj[p]))
		{
			if (!cleanArray(obj[p]))
			{
				delete obj[p];
			}
		}
		else
		{
			if (!cleanObject(obj[p]))
			{
				delete obj[p];
			}
		}
	}
	return Object.keys(obj).length > 0;
}

// returns true if array is not empty after cleaning
function cleanArray(array)
{
	for (let i = array.length - 1; i >= 0; i--)
	{
		if (typeof array[i] === "string")
		{
			array[i] = array[i].trim();
			if (array[i].length === 0)
			{
				array.splice(i, 1);
			}
		}
		else if (Array.isArray(array[i]))
		{
			if (!cleanArray(array[i]))
			{
				array.splice(i, 1);
			}
		}
		else
		{
			if (!cleanObject(array[i]))
			{
				array.splice(i, 1);
			}
		}
	}
	return array.length > 0;
}

function send(data, db)
{
	if (cleanObject(data))
	{
		$.post(`/profiles/${db}`, data, (res, status) => {
			alert(`response: ${res}\nstatus: ${status}`);
		}, "json");
	}
}