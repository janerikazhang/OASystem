
function login(){
	var username = jQuery("#username").val();
	var password = jQuery("#password").val();
	jQuery.ajax("http://localhost:8080/OASystem/api/login",
			{
		async:true,
		processData: false,
		type: "post",
		data: JSON.stringify({"username":username,'password':password}),
		dataType:"text",
		success:function oncallback(data,status,jqXHR){
			var data = jQuery.parseJSON(data);
			if (data.flag == "fail"){
				alert("用户名密码不正确，请核对用户名密码!");
			}
			else{
				loginSuccess(data);
			}
		},
		error:function onerror(jqXHR,status,errorString){
			alert(errorString);
		}
	});
}

function loginSuccess(data){
	jQuery.cookie("userInfo",data.value);
	window.location = "index.html"
}




