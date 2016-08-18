var URL_FRONTEND = "http://tasks-frontendweb.us-west-2.elasticbeanstalk.com/";
//var URL_FRONTEND = "http://localhost:8080/";

jQuery(document).ready(function () {
	
	jQuery('#date-fr').bootstrapMaterialDatePicker
	({
		format: 'YYYY-MM-DD HH:mm:ss',
		lang: 'fr',
		weekStart: 1, 
		cancelText : 'Cancelar',
		nowButton : true,
		switchOnClick : true
	}).on('change', function(e, date)
			{
			jQuery("#dateSharedList").val(jQuery("#date-fr").val());
			insertTasks();
		});
	
	$("#date-fr").hide();
	
	jQuery(".alarm").popover();
	
	/*for(var i = 1; i <= jQuery(".taskListLeft").size(); i++){
		 var color = getRandomColor();
		 jQuery(".taskListLeft:nth-child("+i+") .panel").css("background-color",color);
	 }
	
	for(var i = 1; i <= jQuery(".taskListCenter").size(); i++){
		 var color = getRandomColor();
		 jQuery(".taskListCenter:nth-child("+i+") .panel").css("background-color",color);
	 }
	
	for(var i = 1; i <= jQuery(".taskListRight").size(); i++){
		 var color = getRandomColor();
		 jQuery(".taskListRight:nth-child("+i+") .panel").css("background-color",color);
	 }*/
	
	jQuery("a[data-title=deleteList]").click(function () {
		jQuery(".dialog-decision-header").html("<h2>Eliminar</h2>");
		jQuery(".dialog-decision-body").html("<h3><span>¿Estas Seguro(a) que desea eliminar esta Lista?</span></h3>");
        jQuery("#doDeleteItem").attr('onClick','doDeleteList();');
        jQuery("#idDeleteList").val(this.id);
});
	 
	jQuery("a[data-title=addTask]").click(function () {
	        var task = "<div class='col-lg-12 col-md-12 col-xs-12'>" +
							"<div class='col-lg-1 col-md-1 col-xs-1'>" +
							"<div class='checkbox text-left'>" +
								"<label>" +
									"<input type='checkbox'>" +
									"<span class='checkbox-material'>" +
										"<span class='check'></span>" +
									"</span>" +          				                  
								"</label>" +
							"</div>" +
				      	"</div>" +
						"<div class='col-lg-10 col-md-10 col-xs-10'>" +
				      		"<input type='text' name='task' class='form-control'  placeholder='Tarea'></input>" +
				      	"</div>" +
				      	"<div class='col-md-1 col-sm-1 col-xs-1 pull-right'>"+
						  	"<a href='javascript:void(0)'><i class='material-icons'>delete</i></a>"+
                        "</div> "+
				     "</div>";	
	        jQuery(".addTask").append(task);
	        
	});
	
	jQuery("a[data-title=addEmail]").click(function () {
		 var regex = /[\w-\.]{2,}@([\w-]{2,}\.)*([\w-]{2,}\.)[\w-]{2,4}/;

		    // Se utiliza la funcion test() nativa de JavaScript
		    if (regex.test($('#sharedEmail').val().trim())) {
			    	var task = "<div class='col-lg-12 col-md-12 col-xs-12'>" +				
				"<div class='col-lg-11 col-md-11 col-xs-11'>" +
		      		"<input type='text' name='email' class='form-control' readOnly='readOnly' value=\""+$('#sharedEmail').val().trim()+"\"></input>" +
		      	"</div>" +
		      	"<div class='col-md-1 col-sm-1 col-xs-1 pull-right'>"+
				  	"<a href='javascript:void(0)' data-title='deleteTaskOrEmail'><i class='material-icons'>delete</i></a>"+
	            "</div> "+
		     "</div>";	
			    jQuery(".addTask").append(task);
			    doDeleteItem();
		    } else {
		        alert('La direccón de correo no es valida');
		    }
});
	
	jQuery("a[data-title=openModel]").click(function (){
		jQuery("#titleTask").val("");
		jQuery(".addTask").empty();
		jQuery("#idListTask").val("");
	});
	
	jQuery("a[data-title=sharedList]").click(function (){
		jQuery("#titleTask").val("");
		jQuery(".addTask").empty();
		jQuery("#idListTask").val("");
		jQuery("#idSharedList").val(this.id);
	});
	
	jQuery("h4[data-title=taskListEdit]").click(function () {
		jQuery("#titleTask").val("");
		jQuery("#dateSharedList").val("");
		jQuery(".addTask").empty();
		jQuery("#idListTask").val("");
		jQuery.ajax({
	        type: "GET",
	        url: URL_FRONTEND + "getTaskList/" + this.id,
	        //data: {id:this.id},
	    })
	     .done(function( data, textStatus, jqXHR ) {
	    	var id = data[0].id;
	        var title = data[0].title;
	        var task = "";
	        for (var i = 0; i < data[0].tasks.length; i++) {
        		
	        	if(data[0].items[i].status == "completed")
				{
	        		task += "<div class='col-lg-12 col-md-12 col-xs-12'>" +
        			"<div class='col-lg-1 col-md-1 col-xs-1'>" +
        				"<div class='checkbox text-left'>" +
        					"<label>" +
        						"<input type='checkbox' onChange=\"changeStatus(\'"+id+"\',\'"+data[0].items[i].id+"\',\'completed\')\" checked><span class='checkbox-material'>" +
        						"<span class='check'></span></span>" +
        					"</label>" +
        				"</div>" +
                  	"</div>" +
        			"<div class='col-lg-10 col-md-10 col-xs-10'>" +
                  		"<input type='text' class='form-control completed' onInput=\"updateTask(\'"+id+"\',\'"+data[0].items[i].id+"\');\" id='"+data[0].items[i].id+"' value='"+data[0].items[i].title+"' ></input>" +
                  	"</div>" +
                  	"<div class='col-md-1 col-sm-1 col-xs-1 pull-right'>"+
					  	"<a href='javascript:void(0)' data-toggle='modal' data-target='#delete' onClick=\"deleteTask(\'"+id+"\',\'"+data[0].items[i].id+"\')\"><i class='material-icons'>delete</i></a>"+
	                "</div> "+
                 "</div>";
				}else{
					task += "<div class='col-lg-12 col-md-12 col-xs-12'>" +
        			"<div class='col-lg-1 col-md-1 col-xs-1'>" +
        				"<div class='checkbox text-left'>" +
        					"<label>" +
        						"<input type='checkbox' onChange=\"changeStatus(\'"+id+"\',\'"+data[0].items[i].id+"\',\'needsAction\')\" ><span class='checkbox-material'>" +
        						"<span class='check'></span></span>" +
        					"</label>" +
        				"</div>" +
                  	"</div>" +
        			"<div class='col-lg-10 col-md-10 col-xs-10'>" +
                  		"<input type='text' class='form-control' onInput=\"updateTask(\'"+id+"\',\'"+data[0].items[i].id+"\');\" id='"+data[0].items[i].id+"' value='"+data[0].items[i].title+"'></input>" +
                  	"</div>" +
                  	"<div class='col-md-1 col-sm-1 col-xs-1 pull-right'>"+
					  	"<a href='javascript:void(0)' data-toggle='modal' data-target='#delete' onClick=\"deleteTask(\'"+id+"\',\'"+data[0].items[i].id+"\')\"><i class='material-icons'>delete</i></a>"+
	                "</div> "+
                 "</div>";
				}
	        	
	        	
	                  	
	        /*task += "<div class='col-md-10'>" +
                		"<input type='text' name='task' class='form-control'  value='"+data[0].items[i].title+"'></input>" +
                		"</div>";*/
				
			}
	        jQuery("#idListTask").val(id);
	        jQuery("#titleTask").val(title);
	        jQuery("#titleTask").attr('onInput',"updateTaskList(\'"+id+"\')");
	        doDeleteItem();
	        jQuery(".addTask").html(task);
	        
	     })
	     .fail(function( jqXHR, textStatus, errorThrown ) {
	        alert(errorThrown);
	    });

       });
	
	

});	

function changeStatus(listIdParam,idParam,statusParam){
	jQuery.ajax({
        type: "POST",
        dataType: "json",
        url: URL_FRONTEND + "updateTaskStatus",
        data: {id: idParam, listId: listIdParam, status: statusParam},
    })
     .done(function( data, textStatus, jqXHR ) {
    	 
     })
     .fail(function( jqXHR, textStatus, errorThrown ) {
        //alert(textStatus);
    });
	
	if(data.status == "completed"){
		jQuery("input#"+idParam).addClass("completed");
		jQuery("span."+idParam).addClass("completed");
		jQuery('input:checkbox' + "."+idParam).attr("checked",true);
	}else{
		jQuery("input#"+idParam).removeClass("completed");
		jQuery("span."+idParam).removeClass("completed");
		jQuery('input:checkbox' + "."+idParam).attr("checked",false);
	}
	
}

function updateTask(listIdParam,taskIdParam){
	var titleParam = jQuery("#"+taskIdParam).val();
	jQuery.ajax({
        type: "POST",
        dataType: "json",
        url: URL_FRONTEND + "updateTask",
        data: {id: taskIdParam, listId: listIdParam, title: titleParam},
    })
     .done(function( data, textStatus, jqXHR ) {
    	 jQuery("span."+taskIdParam).text(data.title);
     })
     .fail(function( jqXHR, textStatus, errorThrown ) {
        //alert(textStatus);
    });
	
	
}

function updateTaskList(listIdParam){
	var titleParam = jQuery("#titleTask").val();
	jQuery.ajax({
        type: "POST",
        dataType: "json",
        url: URL_FRONTEND + "updateTasksList",
        data: {id: listIdParam, title: titleParam},
    })
     .done(function( data, textStatus, jqXHR ) {
    	 jQuery("h4."+listIdParam).text(data.title);
     })
     .fail(function( jqXHR, textStatus, errorThrown ) {
        //alert(textStatus);
    });
	
	
}


function getRandomColor() {

	 return randomColor({
	  luminosity : 'light',
	  count : 1
	 });
}



function insertTasks(){
	jQuery.ajax({
        type: "POST",
        dataType: "json",
        url: URL_FRONTEND + "insertTasks",
        data: jQuery("#formInsertTasks").serialize(),
    })
     .done(function( data, textStatus, jqXHR ) {
        alert(data);
     })
     .fail(function( jqXHR, textStatus, errorThrown ) {
        //alert(textStatus);
    });
	
}

function doDeleteList(){
	
	jQuery.ajax({
        type: "POST",
        dataType: "json",
        url: URL_FRONTEND + "deleteList",
        data: jQuery("#formDelete").serialize(),
    })
     .done(function( data, textStatus, jqXHR ) {
        alert(data);
     })
     .fail(function( jqXHR, textStatus, errorThrown ) {
        //alert(textStatus);
    });
	
}

function doDeleteTask(){
	
	jQuery.ajax({
        type: "POST",
        dataType: "json",
        url: URL_FRONTEND + "deleteTask",
        data: jQuery("#formDelete").serialize(),
    })
     .done(function( data, textStatus, jqXHR ) {
        alert(data);
     })
     .fail(function( jqXHR, textStatus, errorThrown ) {
        //alert(textStatus);
    });
	
	jQuery("#"+jQuery("#idDeleteTask").val()).parent().parent().remove();
	
}

function doInsertShared(){
	
	jQuery.ajax({
        type: "POST",
        dataType: "json",
        url: URL_FRONTEND + "sharedList",
        data: jQuery("#formInsertShared").serialize(),
    })
     .done(function( data, textStatus, jqXHR ) {
        alert(data);
     })
     .fail(function( jqXHR, textStatus, errorThrown ) {
        //alert(textStatus);
    });
	
	
}

function doDate(){
	
	jQuery(".dialog-decision-header").html("<h2>Fecha</h2>");
	jQuery(".dialog-decision-body").html("<h3><span>¿Desea Seleccionar una Fecha de Caducidad?</span></h3>");   
    jQuery("#doDeleteItem").attr('onClick','doSelectDate();');
    jQuery("#doDeleteItem").addClass("date-fr");
    jQuery("#doDeleteItem").attr('data-target','#date');
    jQuery("#btnDontDeleteItem").attr('onClick','insertTasks();');
       
}

function doSelectDate(){
	
		jQuery("#date-fr").show().focus().hide();
		
}

function deleteTask(idList,idTask){
		
		jQuery(".dialog-decision-header").html("<h2>Eliminar</h2>");
		jQuery(".dialog-decision-body").html("<h3><span>¿Estas Seguro(a) que desea eliminar esta Tarea?</span></h3>");   
        jQuery("#doDeleteItem").attr('onClick','doDeleteTask();');
        jQuery("#idDeleteTask").val(idTask);
        jQuery("#idDeleteList").val(idList);

}

function doDeleteItem(){
	jQuery("a[data-title=deleteTaskOrEmail]").click(function () {
		 jQuery(this).parent().parent().remove();
		
});
	
}