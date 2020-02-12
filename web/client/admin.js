function runCombiner()
{
	var col1 = $("#collection1").val();
	var col2 = $("#collection2").val();
	var col3 = $("#collection3").val();
	var data = [col1, col2, col3];
	data = JSON.stringify(data);
	var url = "/combiner/run";
	$.ajax({
		url: url,
		type: "post",
		success: handler,
		contentType: "application/json; charset=UTF-8",
		dataType: "json",
		data: data
	});
}

function handler(data, status)
{
	console.log(`connection ${status}`);
	console.log(data);
}