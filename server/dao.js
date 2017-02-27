/* global Promise */

const INSERT = "insert";
const FIND = "find";
const GETALL = "get all";
module.exports =
		{
			insert: function (collectionName, data)
			{
//				data = noReservedChar(data);
				return act(collectionName, data, INSERT);
			},
			find: function (collectionName, data)
			{
//				data = noReservedChar(data);
				return act(collectionName, data, FIND).then(function (data) {
//					data = yesReservedChar(data);
					return data;
				});
			},
			getAll: function (collectionName)
			{
				return act(collectionName, null, GETALL).then(function (data) {
//					data = yesReservedChar(data);
					return data;
				});
			}
		};
var MongoClient = require("mongodb").MongoClient;
var cfg = require("./config.js");
//function noReservedChar(data) {
//	var str = JSON.stringify(data);
//	str = noDot(str);
//	str = noDollar(str);
//	data = JSON.parse(str);
//	return data;
//}
//
//function yesReservedChar(data) {
//	var str = JSON.stringify(data);
//	str = yesDot(str);
//	str = yesDollar(str);
//	data = JSON.parse(str);
//	return data;
//}
//
//function noDot(data) {
//	return data.replace(/\./g, "#dot#");
//}
//
//function yesDot(data) {
//	return data.replace(/#dot#/g, ".");
//}
//
//function noDollar(data) {
//	return data.replace(/\$/g, "#dollar#");
//}
//
//function yesDollar(data) {
//	return data.replace(/#dollar#/g, "$");
//}

function act(collectionName, data, operation)
{
	console.log("function act");
	console.log(` - collectionName ${collectionName}`);
	console.log(` - data ${data}`);
	console.log(` - operation ${operation}`);
	return MongoClient.connect(cfg.mongodb.url)
			.then(function (db)
			{
				console.log(`connected to mongodb ${collectionName}`);
				var op = null;
				switch (operation)
				{
					case INSERT:
						op = function ()
						{
							console.log(" - function insert");
							return db.collection(collectionName).insertMany(data);
						};
						break;
					case FIND:
						op = function ()
						{
							console.log(" - function find");
							console.log(data);
							data = dotNoteObject(data);
//							console.log(typeof data);
							console.log(data);
							return db.collection(collectionName).find(data).toArray();
						};
						break;
					case GETALL:
						op = function ()
						{
							console.log(" - function getall");
							return db.collection(collectionName).find().toArray();
						};
						break;
					default:
						console.log(" - unknown database operation");
				}
				return op().then(function (result)
				{
					console.log(`  - ${operation} complete`);
					return result;
				})
						.catch(function (err)
						{
							var msg = `  - ${operation} failed`;
							console.log(msg);
							console.log(err.message);
							return Promise.reject(msg);
						})
						.then(function (result)
						{
							console.log(" - mongodb closed");
							db.close();
							return result;
						});
			})
			.catch(function (err)
			{
				var msg = "mongodb connection failed";
				console.log(msg);
				console.log(err.message);
				return Promise.reject(msg);
			});
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