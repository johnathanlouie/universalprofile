var express = require("express");
var app = express();

var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({extended: false}));
//app.use(bodyParser.json());

app.use(express.static("client"));

app.get("/profiles/:collectionName", getAllHandler);
app.put("/profiles/:collectionName", insertHandler);
app.post("/profiles/:collectionName", queryHandler);

app.use(missing);
app.use(broke);

var dao = require("./dao.js");

function ensureArray(data)
{
	console.log("function ensureArray");
	if (!Array.isArray(data))
	{
		console.log(" - data converted to array");
		data = [data];
	} else
	{
		console.log(" - data already array");
	}
	return data;
}

function chBody(reqBody) {
	console.log("function chBody");
	var keys = Object.keys(reqBody);
	var obj = keys[0];
	obj = JSON.parse(obj);
	return obj;
}

function getAllHandler(req, res)
{
	console.log("function getAllHandler");
	var collectionName = req.params.collectionName;
	dao.getAll(collectionName)
			.then(function (data)
			{
				console.log(" - handler success response");
				res.json(data);
			})
			.catch(function ()
			{
				console.log(" - handler failure response");
				res.json({status: `failure: get all from ${collectionName}`});
			});
}

function insertHandler(req, res)
{
	console.log("function insertHandler");
	var collectionName = req.params.collectionName;
	var data = req.body;
	data = chBody(data);
	data = ensureArray(data);
	dao.insert(collectionName, data)
			.then(function ()
			{
				console.log(" - handler success response");
				res.json({status: `success: insert into ${collectionName}`});
			})
			.catch(function ()
			{
				console.log(" - handler failure response");
				res.json({status: `failure: insert into ${collectionName}`});
			});
}

function queryHandler(req, res)
{
	console.log("function queryHandler");
	var collectionName = req.params.collectionName;
	var data = req.body;
	data = chBody(data);
	dao.find(collectionName, data)
			.then(function (results)
			{
				console.log(" - handler success response");
				res.json(results);
			})
			.catch(function ()
			{
				console.log(" - handler failure response");
				res.json({status: `failure: query ${collectionName}`});
			});
}

function missing(req, res, next)
{
	res.status(404).json({status: "missing resource"});
}

function broke(err, req, res, next)
{
	console.error(err.stack);
	res.status(500).json({status: "something serverside broke!"});
}

function serverSuccess()
{
	console.log(`Server is listening on port ${cfg.server.port}!`);
}

var cfg = require("./config.js");
app.listen(cfg.server.port, serverSuccess);