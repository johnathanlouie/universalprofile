<?php
$mysqli = new mysqli("localhost", "root", "", "uniprofile");
if ($mysqli->connect_errno) {echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;}

/* Prepared statement, stage 1: prepare */
if (!($stmt = $mysqli->prepare(
"INSERT INTO profiles ( fname, mname, lname, phone1, phone2, phone3, email1, email2, email3, gender, byr, bmnth, bday, addrnum1, dir1, stname1, sttype1, aptnum1, city1, state1, zip1, addrnum2, dir2, stname2, sttype2, aptnum2, city2, state2, zip2, school1, gpa1, mjr11, mjr12, min11, min12, school2, gpa2, mjr21, mjr22, min21, min22, school3, gpa3, mjr31, mjr32, min31, min32 ) VALUES ( ? );"
))) {
     echo "Prepare failed: (" . $mysqli->errno . ") " . $mysqli->error;
}

/* Prepared statement, stage 2: bind and execute */
if (!$stmt->bind_param("ssssssssssiiiisssissiisssissisdsssssdsssssdssss", $_POST["fname"],
$_POST["mname"],
$_POST["lname"],
$_POST["phone1"],
$_POST["phone2"],
$_POST["phone3"],
$_POST["email1"],
$_POST["email2"],
$_POST["email3"],
$_POST["gender"],
$_POST["byr"],
$_POST["bmnth"],
$_POST["bday"],
$_POST["addrnum1"],
$_POST["dir1"],
$_POST["stname1"],
$_POST["sttype1"],
$_POST["aptnum1"],
$_POST["city1"],
$_POST["state1"],
$_POST["zip1"],
$_POST["addrnum2"],
$_POST["dir2"],
$_POST["stname2"],
$_POST["sttype2"],
$_POST["aptnum2"],
$_POST["city2"],
$_POST["state2"],
$_POST["zip2"],
$_POST["school1"],
$_POST["gpa1"],
$_POST["mjr11"],
$_POST["mjr12"],
$_POST["min11"],
$_POST["min12"],
$_POST["school2"],
$_POST["gpa2"],
$_POST["mjr21"],
$_POST["mjr22"],
$_POST["min21"],
$_POST["min22"],
$_POST["school3"],
$_POST["gpa3"],
$_POST["mjr31"],
$_POST["mjr32"],
$_POST["min31"],
$_POST["min32"])) {
    echo "Binding parameters failed: (" . $stmt->errno . ") " . $stmt->error;
}

if (!$stmt->execute()) {
    echo "Execute failed: (" . $stmt->errno . ") " . $stmt->error;
}

/* explicit close recommended */
$stmt->close();
?>