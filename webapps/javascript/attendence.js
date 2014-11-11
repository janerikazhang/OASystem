jQuery(document).ready(function(){
	//var a = jQuery("#helloworld").html()
	//alert(a);
	callHelloWorld();
	test();
})

function importData() {
	if (jQuery('#divUploadFile').length == 0){
		createDiv();
	}
	jQuery("#divUploadFile").dialog({
		bgiframe : true,
		title : "导入excel",
		closeOnEscape : false,
		draggable : true,
		modal : true,
		resizable : true,
		width : 724,
		height : "auto",
		buttons : {
			"关闭" : function() {
				jQuery(this).dialog("close");
			}
		}
	});
}

function createDiv(){
	
	var uploadDiv = $("<div></div>");
	uploadDiv.attr("id", "divUploadFile");
	uploadDiv.css("display", "");
	uploadDiv.appendTo("body");

	var preFormDiv = $("<div></div>");
	preFormDiv.addClass('');
	preFormDiv.appendTo(uploadDiv);

	var preForm = $("<form></form>");
	preForm.attr({
		"id" : "form_portrait",
		"action" : "../OASystem/servlet/OriginalDataImporter",
		"method" : "POST",
		"enctype" : "multipart/form-data"
	});
	preForm.appendTo(preFormDiv);

	var uploadTab = $("<table></table>");
	uploadTab.css("width", "100%");
	uploadTab.appendTo(preForm);

	var tr = $("<tr></tr>");
	tr.appendTo(uploadTab);

	var th = $("<th width='160px'>原始考勤文档：</th>");
	th.appendTo(tr);

	var td = $("<td></td>");
	td.appendTo(tr);

	var fileInput = $("<input></input>");
	fileInput.attr({
		"type" : "file",
		"name" : "img",
		"id" : "f_portrait",
		"size" : "30"
	});
	fileInput.css({"margin-top":"5px"});
	fileInput.appendTo(td);

	var fileBtn = $("<input></input>");
	fileBtn.attr({
		"type" : "submit",
		"id" : "btn_preview",
		"value" : "上传"
	});
	fileBtn.css("padding", "0 20px");
	fileBtn.addClass("button");
	fileBtn.appendTo(td);

	$('#form_portrait').ajaxForm({
		dataType : 'json',
		success : function(json) {
			if (json.content) {
				alert(json.content);
				jQuery("#divUploadFile").dialog("close");
			}
		}
	});


}


function callHelloWorld(){
jQuery.ajax("../OASystem/api/helloworld",
		{
	async:true,
	dataType:"json",
	success:function oncallback(data,status,jqXHR){
		$("#helloworld").html(data.returnString);
	},
	error:function onerror(jqXHR,status,errorString){
		alert(errorString);
	}
		});
}

function testDialog(){
	$( "#dialog" ).dialog({
		autoOpen: false,
		width: 400,
		buttons: [
			{
				text: "Ok",
				click: function() {
					$( this ).dialog( "close" );
				}
			},
			{
				text: "Cancel",
				click: function() {
					$( this ).dialog( "close" );
				}
			}
		]
	});
	$("#dialog").dialog("open");
}
function test(){
	jQuery("#flexme4").flexigrid({
	    url : '../OASystem/api/testGrid',
	    dataType : 'json',
	    colModel : [ {
	        display : 'EmployeeID',
	        name : 'employeeID',
	        width : 90,
	        sortable : false,
	        align : 'center'
	        }, {
	            display : 'Name',
	            name : 'name',
	            width : 120,
	            sortable : false,
	            align : 'left'
	        }, {
	            display : 'Primary Language',
	            name : 'primary_language',
	            width : 120,
	            sortable : false,
	            align : 'left'
	        }, {
	            display : 'Favorite Color',
	            name : 'favorite_color',
	            width : 80,
	            sortable : false,
	            align : 'left',
	            hide : false
	        }, {
	            display : 'Favorite Animal',
	            name : 'favorite_pet',
	            width : 80,
	            sortable : false,
	            align : 'right'
	    } ],
	    buttons : [ {
	        name : 'Add',
	        bclass : 'add',
	        onpress : Example4
	        }
	        ,
	        {
	            name : 'Edit',
	            bclass : 'edit',
	            onpress : Example4
	        }
	        ,
	        {
	            name : 'Delete',
	            bclass : 'delete',
	            onpress : Example4
	        }
	        ,
	        {
	            separator : true
	        } 
	    ],
	    searchitems : [ {
	        display : 'EmployeeID',
	        name : 'employeeID'
	        }, {
	            display : 'Name',
	            name : 'name',
	            isdefault : true
	    } ],
	    sortname : "iso",
	    sortorder : "asc",
	    usepager : true,
	    title : 'Employees',
	    useRp : true,
	    rp : 1,
	    showTableToggleBtn : true,
	    width : 750,
	    height : 200,
	    trclick : function () {
			$('#flexme4 tr').on('click', function (e) {
				var a = $(e.currentTarget);
				alert(a.attr("id"));
			}).on('mousedown', function (e) {
				
			}).on('mouseup', function (e) {
				
			}).on('dblclick', function () {
				
			}).hover(function (e) {
				
			}, function () {});
		}
	});
	
}

function Example4(com, grid) {
    if (com == 'Delete') {
        var conf = confirm('Delete ' + $('.trSelected', grid).length + ' items?')
        if(conf){
            $.each($('.trSelected', grid),
                function(key, value){
                    $.get('example4.php', { Delete: value.firstChild.innerText}
                        , function(){
                            // when ajax returns (callback), update the grid to refresh the data
                            $("#flexme4").flexReload();
                    });
            });    
        }
    }
    else if (com == 'Edit') {
        var conf = confirm('Edit ' + $('.trSelected', grid).length + ' items?')
        if(conf){
            $.each($('.trSelected', grid),
                function(key, value){
                    // collect the data
                    var OrgEmpID = value.children[0].innerText; // in case we're changing the key
                    var EmpID = prompt("Please enter the New Employee ID",value.children[0].innerText);
                    var Name = prompt("Please enter the Employee Name",value.children[1].innerText);
                    var PrimaryLanguage = prompt("Please enter the Employee's Primary Language",value.children[2].innerText);
                    var FavoriteColor = prompt("Please enter the Employee's Favorite Color",value.children[3].innerText);
                    var FavoriteAnimal = prompt("Please enter the Employee's Favorite Animal",value.children[4].innerText);

                    // call the ajax to save the data to the session
                    $.get('example4.php', 
                        { Edit: true
                            , OrgEmpID: OrgEmpID
                            , EmpID: EmpID
                            , Name: Name
                            , PrimaryLanguage: PrimaryLanguage
                            , FavoriteColor: FavoriteColor
                            , FavoritePet: FavoriteAnimal  }
                        , function(){
                            // when ajax returns (callback), update the grid to refresh the data
                            $("#flexme4").flexReload();
                    });
            });    
        }
    }
    else if (com == 'Add') {
        // collect the data
        var EmpID = prompt("Please enter the Employee ID","5");
        var Name = prompt("Please enter the Employee Name","Mark");
        var PrimaryLanguage = prompt("Please enter the Employee's Primary Language","php");
        var FavoriteColor = prompt("Please enter the Employee's Favorite Color","Tan");
        var FavoriteAnimal = prompt("Please enter the Employee's Favorite Animal","Dog");

        // call the ajax to save the data to the session
        $.get('example4.php', { Add: true, EmpID: EmpID, Name: Name, PrimaryLanguage: PrimaryLanguage, FavoriteColor: FavoriteColor, FavoritePet: FavoriteAnimal  }
            , function(){
                // when ajax returns (callback), update the grid to refresh the data
                $(".flexme4").flexReload();
        });
    }
}


