//Imports

var express = require("express");
var app = express();
var mysql = require("mysql");
var connection = mysql.createConnection({
	host: "sql9.freesqldatabase.com",
	user: "sql9139285",
	password: "YzhFsyyuyC",
	database: "sql9139285"
});

var server = app.listen(80, function() {
	connection.connect();

	var host = server.address().address;
	var port = server.address().port;

	console.log(host + ":" + port);
});

var spawn = require("threads").spawn;

//spawn a thread that uses input (json) for
//your data. done is a callback function that
//you can execute to return data from the
//thread
var monitorTable = spawn(function(input, done) {
	done({});
});

monitorTable.send({}).on('message', function(response) {
	monitorTable.kill();
}); //run the thread

//Routing

app.get('/', function(req, res) {
	res.send("you're on the default page, nothing is configured here");
});

app.get('/p/get_profile_id', function(req, res){
	var json = req.query;
	if(json.hasOwnProperty('username') && json.hasOwnProperty('password'))
	{
		var username = json.username;
		var password = json.password;
		connection.query(getProfileID(username, password), function(err, rows, fields) {
			if(err)
			{
				console.log("error was captured");
			}
			else
			{
				res.json(rows[0]);
			}
		});
	}
});

app.get('/p/get_fares', function(req, res) {
	var json  = req.query;
	if(json.hasOwnProperty('profile_id')) {
		var profile_id = json.profile_id;
		connection.query("SELECT * FROM  `Fares` WHERE  `PROFILE_ID` =  '" + profile_id + "'", function(err, rows, fields){
			if(err)
			{
				res.json({"error":"no fares"});
			}
			else {
				res.json(rows);
			}
		});
	}
});

app.get('/p/purchase_fare', function(req, res) {
	var json = req.query;
	if(json.hasOwnProperty('profile_id') &&
	json.hasOwnProperty("fare_type") &&
	json.hasOwnProperty("active_start") &&
	json.hasOwnProperty("active_end"))
	{
		var profile_id = json.profile_id;
		var active_start = json.active_start;
		var fare_type = json.fare_type;
		var ticket_id = makeid(16);
		var price = "$0";
		var active_end = json.active_end;

		switch (fare_type) {
			case "1-Day Pass":
				price = "$7";
				break;

			case "3-Day Pass":
				price = "$20";
				break;

			case "Week Pass":
				price = "$40";
				break;

			case "Month Pass":
				price = "$70";
				break;

			case "Annual Pass":
				price = "$400,000";
				break;

			default:
				price = "$30";
				break;
		}

		connection.query(
			insertData('Fares', {
				"PROFILE_ID"		: profile_id,
				"FARE_TYPE"			: fare_type,
				"TICKET_ID"			: ticket_id,
				"ACTIVE_START"	: active_start,
				"ACTIVE_END"		: active_end,
				"PRICE"					: price
			}) , function(err, rows, fields) {
			if(err) {
				console.log(err);
			}
			//console.log(res);
			//console.log("'twas able to complete your request'");
			res.json({
				"success": "request was success"
			});
		});
	}
});


//Functions

function makeid(length)
{
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    for( var i=0; i < length; i++ )
        text += possible.charAt(Math.floor(Math.random() * possible.length));

    return text;
}

function lengthOfJson(input)
{
	var count = 0;
	for(var kreme in input) {
  	count++;
	}
	return count;
}

function insertData(tableName, keypair)
{
	var returnString = "INSERT INTO `sql9139285`.`" + tableName + "` (";
	var values = " VALUES (";
	var maxLength = 0;

	var count = lengthOfJson(keypair);

	for(var key in keypair)
	{
		if(!keypair.hasOwnProperty(key)) continue;

		var evalulated = keypair[key];

		if(maxLength+1 == count)
		{
			returnString += "`";
			returnString += key += "`)";

			values += "'";
			values += evalulated += "');";
		}
		else {
			returnString += "`";
			returnString += key += "`,";

			values += "'";
			values += evalulated += "',";
		}
		maxLength++;
	}
	returnString += values;
	console.log(returnString);

	return returnString;
}

function getProfileID(username, password)
{
	return "SELECT * FROM  `Profiles` WHERE  `USERNAME` = '" + username + "' AND  `PASSWORD` = '" + password + "'";
}
