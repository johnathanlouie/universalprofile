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

function autoCreate()
{
	var profileNum = Number($("#profilenum").val());
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
		profiles.push(AutoProfile(male, female, last, street, city, state, school, subject, words));
	}
	cleanArray(profiles);
	return profiles;
}

function AutoProfile(maleNames, femaleNames, lastNames, streetNames, city, state, schoolNames, subject, words)
{
	var sex = genSex();
	var name = AutoName(sex, maleNames, femaleNames, lastNames);
	var birth = {};
	if (randomChance(chance.birth))
	{
		birth = AutoBirth();
	}
	var phone = [];
	for (let i = 0, max = 4; i < max; i++)
	{
		if (randomChance(avgChance(avgNum.phone, max)))
		{
			phone.push(genPhone());
		}
	}
	var email = [];
	for (let i = 0, max = 4; i < max; i++)
	{
		if (randomChance(avgChance(avgNum.email, max)))
		{
			if (randomChance(1 / 3))
			{
				email.push(genEmail1(name, birth));
			}
			else
			{
				email.push(genEmail2(words));
			}
		}
	}
	var address =
		[
			thisOrEmpty(AutoAddress(streetNames, city, state), 0.4)
		];
	var education = [];
	for (let i = 0, max = 4; i < max; i++)
	{
		if (randomChance(avgChance(avgNum.school, max)))
		{
			education.push(AutoSchool(schoolNames, subject));
		}
	}
	var profile = new Profile(name, sex, phone, email, address, birth, education);
	return profile;
}

function AutoAddress(street, city, state)
{
	return new Address(
		genStNum(),
		pickRandom(street),
		genAptNum(),
		pickRandom(city),
		pickRandom(state),
		genZip()
		);
}

function genAptNum()
{
	return thisOrEmpty(String(1 + Math.round(Math.random() * 9999)), 0.1);
}

function genStDir()
{
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
}

function AutoName(sex, male, female, last)
{
	var f = "";
	var m = "";
	var l = "";
	if (sex === "male")
	{
		f = pickRandom(male);
		m = thisOrEmpty(pickRandom(male), chance.middleName);
	}
	else
	{
		f = pickRandom(female);
		m = thisOrEmpty(pickRandom(female), chance.middleName);
	}
	l = pickRandom(last);
	return new Name(f, m, l);
}

function genSex()
{
	return randomChance(0.5) ? "male" : "female";
}

function AutoBirth()
{
	var year = thisOrEmpty(genByear(), chance.birthYear);
	var month = genBmonth();
	var day = genBday(month);
	return new Birth(year, month, day);
}

function AutoSchool(names, subjects)
{
	var gpa = genGpa();
	var name = pickRandom(names);
	var major = [];
	var minor = [];
	major.push(pickRandom(subjects));
	if (randomChance(0.05))
	{
		major.push(pickRandom(subjects));
	}
	if (randomChance(0.05))
	{
		minor.push(pickRandom(subjects));
	}
	if (randomChance(0.05))
	{
		minor.push(pickRandom(subjects));
	}
	return new School(name, gpa, major, minor);
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

function genStNum()
{
	return  String(1 + Math.round(Math.random() * 9999));
}

function genEmail1(name, birth)
{
	return `${name.first}.${name.last}@${pickRandom(providers)}.com`.toLowerCase();
}

function genEmail2(words)
{
	return `${pickRandom(words)}${pickRandom(words)}${Math.round(Math.random() * 99999)}@${pickRandom(providers)}.com`.toLowerCase();
}

function genZip()
{
	var str = "";
	for (let i = 0; i < 5; i++)
	{
		str += Math.round(Math.random() * 9);
	}
	return str;
}

function genBday(month)
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

function genBmonth()
{
	return String(1 + Math.round(Math.random() * 11));
}

function genByear()
{
	return String(2017 - 120 + Math.round(Math.random() * 120));
}

// returns chance 0 to 1
function avgChance(avg, tries)
{
	return avg / tries;
}

function genPhone()
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

function genGpa()
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

function thisOrEmpty(value, chance)
{
	if (randomChance(chance))
	{
		return value;
	}
	return "";
}