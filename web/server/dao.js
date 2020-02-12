/* global Promise */

const INSERT = "insert";
const FIND = "find";
const GETALL = "get all";
module.exports =
		{
			insert: function (collectionName, data)
			{
				return act(collectionName, data, INSERT);
			},
			find: function (collectionName, data)
			{
				return act(collectionName, data, FIND).then(function (data) {
					return data;
				});
			},
			getAll: function (collectionName)
			{
				return act(collectionName, null, GETALL).then(function (data) {
					return data;
				});
			}
		};
var MongoClient = require("mongodb").MongoClient;
var cfg = require("./config.js");

function connFailed(err)
{
	var msg = "mongodb connection failed";
	console.log(msg);
	console.log(err.message);
	return Promise.reject(msg);
}

function authFailed(err) {
	var msg = "mongodb auth failed";
	console.log(msg);
	console.log(err.message);
	return Promise.reject(msg);
}

function act(collectionName, data, operation)
{
	function opFail(err)
	{
		var msg = `${operation} failed`;
		console.log(msg);
		console.log(err.message);
		return Promise.reject(msg);
	}
	function opSuccess(result)
	{
		console.log(`${operation} complete`);
		return result;
	}
	function connSuccess(db)
	{
		function closeDb(result)
		{
			console.log("mongodb closed");
			db.close();
			return result;
		}
		function insert()
		{
			console.log("function insert");
			return db.collection(collectionName).insertMany(data);
		}
		function find()
		{
			console.log("function find");
			console.log(" - json query", data);
			data = dotNoteObject(data);
			console.log(" - mongodb query", data);
			return db.collection(collectionName).find(data).toArray();
		}
		function getAll()
		{
			console.log("function getall");
			return db.collection(collectionName).find().toArray();
		}
		function authSuccess() {
			console.log("mongodb auth successful");
			var op = null;
			switch (operation)
			{
				case INSERT:
					op = insert;
					break;
				case FIND:
					op = find;
					break;
				case GETALL:
					op = getAll;
					break;
				default:
					console.log("unknown database operation");
					return Promise.reject("unknown database operation");
			}
			return op().then(opSuccess).catch(opFail).then(closeDb);
		}
		console.log(`mongodb connection successful`);
		console.log(`collection ${collectionName}`);
		if (typeof cfg.mongodb.username === "string") {
			return db.authenticate(cfg.mongodb.username, cfg.mongodb.password).then(authSuccess).catch(authFailed);
		} else {
			return authSuccess();
		}
	}
	console.log("function act");
	console.log(` - collectionName ${collectionName}`);
	console.log(` - data ${data}`);
	console.log(` - operation ${operation}`);
	return MongoClient.connect(cfg.mongodb.url).then(connSuccess).catch(connFailed);
}

function dotNoteObject(obj) {
	console.log("function dotNoteObject");
	var query = {};
	for (let key of Object.keys(obj)) {
		if (Array.isArray(obj[key])) {
			query[key] = dotNoteArray(obj[key]);
		} else if (typeof obj[key] === "object") {
			var subQuery2 = dotNoteObject(obj[key]);
			for (let subKey of Object.keys(subQuery2)) {
				query[`${key}.${subKey}`] = subQuery2[subKey];
			}
		} else {
			query[key] = obj[key];
		}
	}
	return query;
}

function dotNoteArray(array) {
	console.log("function dotNoteArray");
	var query = {};
	query.$all = [];
	for (let element of array) {
		if (typeof element === "object") {
			var elementQuery = {};
			elementQuery.$elemMatch = dotNoteObject(element);
			query.$all.push(elementQuery);
		} else {
			query.$all.push(element);
		}
	}
	return query;
}