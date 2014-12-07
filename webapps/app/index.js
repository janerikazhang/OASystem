
$(document).ready(function(){
	checkLogin();
});

function check(){
	alert(jQuery.cookie("userInfo"));
}

function checkLogin(){
	var userInfoString = jQuery.cookie("userInfo");
	if (userInfoString && userInfoString != null && userInfoString != ""){
		var userInfo = jQuery.parseJSON(userInfoString);
		jQuery.ajax("http://localhost:8080/OASystem/api/validateUser",
				{
			async:true,
			processData: false,
			type: "post",
			data: userInfo.accessToken,
			dataType:"text",
			success:function oncallback(data,status,jqXHR){
				var data = jQuery.parseJSON(data);
				if (data.flag == "fail"){
					alert("您尚未登录！");
					window.location="login.html";
				}
			},
			error:function onerror(jqXHR,status,errorString){
				alert(errorString);
			}
		});
	}
	else{
		alert("您尚未登录！");
		window.location="login.html";
	}
}

function logout(){
	var userInfoString = jQuery.cookie("userInfo");
	if (userInfoString && userInfoString != null && userInfoString != ""){
		var userInfo = jQuery.parseJSON(userInfoString);
		jQuery.ajax("http://localhost:8080/OASystem/api/logout",
		{
			async:true,
			processData: false,
			type: "post",
			data: userInfo.accessToken,
			dataType:"text",
			success:function oncallback(data,status,jqXHR){
				jQuery.cookie("userInfo","");
				window.location = "login.html";
			},
			error:function onerror(jqXHR,status,errorString){
				alert(errorString);
			}
		});
	}
	else{
		jQuery.cookie("userInfo","");
		window.location = "login.html";
	}
}

function gotoAttendance() {
	$("#attendanceTable").attr("src", "table.html");
}

function gotoSysConfig() {
	$("#attendanceTable").attr("src", "config.html");
}

function gotoUserMngmt() {
//	$("#attendanceTable").attr("src", "table.html");
	alert("not implemented");
}
