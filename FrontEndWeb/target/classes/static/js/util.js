jQuery(document).ready(function () {
	 
	for(var i = 1; i <= jQuery(".taskListLeft").size(); i++){
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
	 }
	 
	 
	 
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
	
	jQuery("a[data-title=openModel]").click(function (){
		jQuery("#titleTask").val("");
		jQuery(".addTask").empty();
		jQuery("#idListTask").val("");
	});
	
	jQuery("h4[data-title=taskListEdit]").click(function () {
		jQuery("#titleTask").val("");
		jQuery(".addTask").empty();
		jQuery("#idListTask").val("");
		jQuery.ajax({
	        type: "GET",
	        url: "http://localhost:8080/getTaskList/" + this.id,
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
					  	"<a href='javascript:void(0)'><i class='material-icons'>delete</i></a>"+
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
					  	"<a href='javascript:void(0)'><i class='material-icons'>delete</i></a>"+
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
        url: "http://localhost:8080/updateTaskStatus",
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
        url: "http://localhost:8080/updateTask",
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
        url: "http://localhost:8080/updateTasksList",
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
        url: "/insertTasks",
        data: jQuery("#formInsertTasks").serialize(),
    })
     .done(function( data, textStatus, jqXHR ) {
        alert(data);
     })
     .fail(function( jqXHR, textStatus, errorThrown ) {
        //alert(textStatus);
    });
}