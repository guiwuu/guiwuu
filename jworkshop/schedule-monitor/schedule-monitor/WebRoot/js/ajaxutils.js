function ajaxUtil(type,url,dataType,success,error)
{
	jQuery.ajax({
			type:type,
			 url: url,
		dataType:dataType,
	    success:success,
		error:error 
	});
}
