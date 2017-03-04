function runCombiner()
{
	var col1 = $("#collection1").val();
	var col2 = $("#collection2").val();
	var data = [col1, col2];
	data = JSON.stringify(data);
	$.post("/combiner/run", data, handler, "json");
}

function handler(data, status)
{
	console.log(`connection ${status}`);
	console.log(data);
}