/* global __dirname */

var express = require("express");
var app = express();

var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({extended: false}));
//app.use(bodyParser.json());

app.use(express.static("client"));

app.get("/profiles/:collectionName", getAllHandler);
app.put("/profiles/:collectionName", insertHandler);
app.post("/profiles/:collectionName", queryHandler);
app.post("/combiner/run", runCombinerHandler);

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
	}
	else
	{
		console.log(" - data already array");
	}
	return data;
}

function chBody(reqBody)
{
	console.log("function chBody");
	var keys = Object.keys(reqBody);
	var obj = keys[0];
	obj = JSON.parse(obj);
	return obj;
}

function getAllHandler(req, res)
{
	function handlerSuccess(data)
	{
		console.log("handler success");
		res.json(data);
	}
	function handlerFail()
	{
		console.log("handler failure");
		res.json({status: `failure: get all from ${collectionName}`});
	}
	console.log("function getAllHandler");
	var collectionName = req.params.collectionName;
	dao.getAll(collectionName).then(handlerSuccess).catch(handlerFail);
}

function insertHandler(req, res)
{
	function handlerSuccess()
	{
		console.log("handler success");
		res.json({status: `success: insert into ${collectionName}`});
	}
	function handlerFail()
	{
		console.log("handler failure");
		res.json({status: `failure: insert into ${collectionName}`});
	}
	console.log("function insertHandler");
	var collectionName = req.params.collectionName;
	var data = req.body;
	data = chBody(data);
	data = ensureArray(data);
	dao.insert(collectionName, data).then(handlerSuccess).catch(handlerFail);
}

function queryHandler(req, res)
{
	function handlerSuccess(results)
	{
		console.log("handler success");
		res.json(results);
	}
	function handlerFail()
	{
		console.log("handler failure");
		res.json({status: `failure: query ${collectionName}`});
	}
	console.log("function queryHandler");
	var collectionName = req.params.collectionName;
	var data = req.body;
	data = chBody(data);
	dao.find(collectionName, data).then(handlerSuccess).catch(handlerFail);
}
const exec = require('child_process').spawn;
const path = require('path');
function runCombinerHandler(req, res)
{
	function execResults(error, stdout, stderr)
	{
		if (error)
		{
			console.error(`exec error: ${error}`);
			return;
		}
		console.log(`stdout: ${stdout}`);
		console.log(`stderr: ${stderr}`);
		console.log("exec finished");
		res.json({status: "success: finished combining"});
	}
	console.log("function runCombinerHandler");
	var data = req.body;
	data = chBody(data);
	data = ensureArray(data);
	if (data.length === 2)
	{
		var col1 = data[0];
		var col2 = data[1];
		var cwd = path.normalize(`${__dirname}/../build`);
		var options = {};
		options.cwd = cwd;
		console.log(`cwd ${cwd}`);
		var child = exec('java', ['combiner.StartCombiner', col1, col2], options);
		var cmd = `java combiner.StartCombiner ${col1} ${col2}`;
//		var cmd = "notepad";
		console.log(`cmd ${cmd}`);
		function ran(exitCode)
		{
			if (exitCode !== 0)
			{
				console.log('combiner nonzero exit code');
				res.json({status: "failure: combiner nonzero exit code"});
			}
			res.json({status: "success: combiner zero exit code"});
		}
		child.on('close', ran);
//		exec(cmd, options, execResults);
	}
	else
	{
		res.json({status: "failure: wrong number of collections"});
	}
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
