/* global Promise */

module.exports =
{
	insertFb: function(data)
	{
		return operation("facebook", data);
	},
	findFb: function()
	{
		return operation("facebook");
	}, insertGp: function(data)
	{
		return operation("google", data);
	},
	findGp: function()
	{
		return operation("google");
	}, insertLi: function(data)
	{
		return operation("linkedin", data);
	},
	findLi: function()
	{
		return operation("linkedin");
	}
};
var MongoClient = require("mongodb").MongoClient;
var assert = require("assert");
var cfg = require("./config.js");
function operation(collectionName, data)
{
	var op = data === undefined ? "find" : "insert";
	return MongoClient.connect(cfg.mongodb.url)
	.then(function(db)
	{
		console.log(`connected to mongodb ${collectionName}`);
		return (data === undefined ? db.collection(collectionName).find().toArray() : db.collection(collectionName).insertMany(data))
		.then(function(result)
		{
			console.log(`${op} complete`);
			return result;
		})
		.catch(function(err)
		{
			var msg = `${op} failed`;
			console.log(msg);
			return Promise.reject(msg);
		})
		.then(function(result)
		{
			console.log("mongodb closed");
			db.close();
			return result;
		});
	})
	.catch(function(err)
	{
		var msg = "mongodb connection failed";
		console.log(msg);
		return Promise.reject(msg);
	});
}