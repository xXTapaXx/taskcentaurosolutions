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
						"<div class='col-lg-11 col-md-11 col-xs-11'>" +
				      		"<input type='text' name='task' class='form-control'  placeholder='Tarea'></input>" +
				      	"</div>" +
				     "</div>";
	        jQuery("#btnTask").attr('onClick','insertTasks()');	
	        jQuery(".addTask").append(task);
	        
	});
	
	jQuery("a[data-title=taskListEdit]").click(function () {
		jQuery.ajax({
	        type: "GET",
	        url: "http://localhost:8081/getTaskList/" + this.id,
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
        						"<input type='checkbox' checked><span class='checkbox-material'>" +
        						"<span class='check'></span></span>" +
        					"</label>" +
        				"</div>" +
                  	"</div>" +
        			"<div class='col-lg-11 col-md-11 col-xs-11'>" +
                  		"<input type='text' name='task' class='form-control'  value='"+data[0].items[i].title+"' style=-text-decoration:line-through;-></input>" +
                  	"</div>" +
                 "</div>";
				}else{
					task += "<div class='col-lg-12 col-md-12 col-xs-12'>" +
        			"<div class='col-lg-1 col-md-1 col-xs-1'>" +
        				"<div class='checkbox text-left'>" +
        					"<label>" +
        						"<input type='checkbox' ><span class='checkbox-material'>" +
        						"<span class='check'></span></span>" +
        					"</label>" +
        				"</div>" +
                  	"</div>" +
        			"<div class='col-lg-11 col-md-11 col-xs-11'>" +
                  		"<input type='text' name='task' class='form-control'  value='"+data[0].items[i].title+"'></input>" +
                  	"</div>" +
                 "</div>";
				}
	        	
	        	
	                  	
	        /*task += "<div class='col-md-10'>" +
                		"<input type='text' name='task' class='form-control'  value='"+data[0].items[i].title+"'></input>" +
                		"</div>";*/
				
			}
	        
	        jQuery("#btnTask").attr('onClick','editTask()');
	        jQuery("#titleTask").val(title);
	        jQuery(".addTask").html(task);
	        
	     })
	     .fail(function( jqXHR, textStatus, errorThrown ) {
	        alert(errorThrown);
	    });

       });
	
	

});	


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