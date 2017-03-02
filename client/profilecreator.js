function manualCreate()
{
	var name = new Name($("#fname").val(), $("#mname").val(), $("#lname").val());
	var phone = [$("#phone1").val(), $("#phone2").val(), $("#phone3").val()];
	var email = [$("#email1").val(), $("#email2").val(), $("#email3").val()];
	var sex = $("#gender").val();
	var birth = new Birth($("#byr").val(), $("#bmnth").val(), $("#bday").val());
	var addr1 = new Address(
			$("#addrnum1").val(),
			$("#stname1").val(),
			$("#aptnum1").val(),
			$("#city1").val(),
			$("#state1").val(),
			$("#zip1").val()
			);
	var addr2 = new Address(
			$("#addrnum2").val(),
			$("#stname2").val(),
			$("#aptnum2").val(),
			$("#city2").val(),
			$("#state2").val(),
			$("#zip2").val()
			);
	var address = [addr1, addr2];
	var school1 = new School(
			$("#school1").val(),
			$("#gpa1").val(),
			[$("#mjr11").val(), $("#mjr12").val()],
			[$("#min11").val(), $("#min12").val()]
			);
	var school2 = new School(
			$("#school2").val(),
			$("#gpa2").val(),
			[$("#mjr21").val(), $("#mjr21").val()],
			[$("#min22").val(), $("#min22").val()]
			);
	var school3 = new School(
			$("#school3").val(),
			$("#gpa3").val(),
			[$("#mjr31").val(), $("#mjr31").val()],
			[$("#min32").val(), $("#min32").val()]
			);
	var education = [school1, school2, school3];
	var profile = new Profile(name, sex, phone, email, address, birth, education);
	cleanObject(profile);
	$("#test").text(JSON.stringify(profile));
}

function insertDb()
{
	var data = $("#test").text();
//	data = JSON.parse(data);
	var collectionName = $("#src").val();
	var url = `/profiles/${collectionName}`;
	$.ajax({
		url: url,
		type: "put",
		success: responseHandler,
		dataType: "json",
		data: data
	});
//	$.post(url, data, responseHandler, "json");
}

function responseHandler(res, status) {
	console.log(`server connection: ${status}`);
	console.log(`request status: ${res.status}`);
	console.log("response data", res);
	if (Array.isArray(res)) {
		for (let i of res) {
			try {
				var lastName = i.name.last;
				lastName = lastName || "";
			} catch (e) {
				lastName = "";
			}
			try {
				var firstName = i.name.first;
				firstName = firstName || "";
			} catch (e) {
				firstName = "";
			}
			try {
				var middleName = i.name.middle;
				middleName = middleName || "";
			} catch (e) {
				middleName = "";
			}
			try {
				var phone = i.phone[0];
			} catch (e) {
				phone = "";
			}
			try {
				var email = i.email[0];
			} catch (e) {
				email = "";
			}
			$("#results").append(`<tr><td>${firstName}</td><td>${middleName}</td><td>${lastName}</td><td>${phone}</td><td>${email}</td></tr>`);
		}
	} else {
		console.log("response not array");
	}
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
		} else if (Array.isArray(obj[p]))
		{
			if (!cleanArray(obj[p]))
			{
				delete obj[p];
			}
		} else
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
		} else if (Array.isArray(array[i]))
		{
			if (!cleanArray(array[i]))
			{
				array.splice(i, 1);
			}
		} else
		{
			if (!cleanObject(array[i]))
			{
				array.splice(i, 1);
			}
		}
	}
	return array.length > 0;
}

function queryDb()
{
	var data = $("#test").text();
	var collectionName = $("#src").val();
	$.post(`/profiles/${collectionName}`, data, responseHandler, "json");
}