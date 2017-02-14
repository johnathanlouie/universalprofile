var express = require("express");
var app = express();
var cfg = require("./config.js");
var dao = require("./dao.js");

var bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

app.use(express.static("client"));

app.get("/", function(req, res)
{
	res.sendFile("profilegenerator.html", {root: "client"});
});

app.get("/profiles/facebook", getFbHandler);
app.get("/profiles/linkedin", getLiHandler);
app.get("/profiles/google", getGpHandler);
app.get("/profiles/combined", getCombinedHandler);

app.post("/profiles/facebook", postFbHandler);
app.post("/profiles/linkedin", postLiHandler);
app.post("/profiles/google", postGpHandler);
app.post("/profiles/combined", postCombinedHandler);

app.use(missing);
app.use(broke);

function ensureArray(req)
{
	var data = req.body;
	if (!Array.isArray(data))
	{
		data = [data];
	}
	return data;
}

function getFbHandler(req, res)
{
	dao.findFb()
	.then(function(data)
	{
		res.json(data);
	})
	.catch(function()
	{
		res.json({error: "get facebook failed"});
	});
}

function postFbHandler(req, res)
{
	var data = ensureArray(req);
	dao.insertFb(data)
	.then(function()
	{
		res.json({status: "success"});
	})
	.catch(function()
	{
		res.json({error: "insert facebook failed"});
	});
}

function getLiHandler(req, res)
{
	dao.findLi()
	.then(function(data)
	{
		res.json(data);
	})
	.catch(function()
	{
		res.json({error: "get linkedin failed"});
	});
}

function postLiHandler(req, res)
{
	var data = ensureArray(req);
	dao.insertLi(data)
	.then(function()
	{
		res.json({status: "success"});
	})
	.catch(function()
	{
		res.json({error: "insert linkedin failed"});
	});
}

function getGpHandler(req, res)
{
	dao.findGp()
	.then(function(data)
	{
		res.json(data);
	})
	.catch(function()
	{
		res.json({error: "get google+ failed"});
	});
}

function postGpHandler(req, res)
{
	var data = ensureArray(req);
	dao.insertGp(data)
	.then(function()
	{
		res.json({status: "success"});
	})
	.catch(function()
	{
		res.json({error: "insert google+ failed"});
	});
}

function getCombinedHandler(req, res)
{
	dao.findFb()
	.then(function(data)
	{
		res.json(data);
	})
	.catch(function()
	{
		res.json({error: "get combined failed"});
	});
}

function postCombinedHandler(req, res)
{
	var data = ensureArray(req);
	dao.insertFb(data)
	.then(function()
	{
		res.json({status: "success"});
	})
	.catch(function()
	{
		res.json({error: "insert combined failed"});
	});
}

function missing(req, res, next)
{
	res.status(404).json({error: "missing resource"});
}

function broke(err, req, res, next)
{
	console.error(err.stack);
	res.status(500).send("Something broke!");
}

function serverSuccess()
{
	console.log(`Server is listening on port ${cfg.server.port}!`);
}

app.listen(cfg.server.port, serverSuccess);