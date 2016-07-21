function initLogin(){
		showLoading();
		var userLogin = storage.get('isUserLogin');
		if(userLogin){
			$(location).attr('href', "layout.html");
			hideLoading();
		}
		hideLoading();
	}

	// Initiate the library
	hello.init({
				facebook : '957381987687235',//'318169131727309',//
				twitter : 'oZgC8kYRfoJCyAthqEsr1yfLs',
				google : '132172060654-gpfi552uk35hb29gqrni2da2lfaosv6l.apps.googleusercontent.com'//'132172060654-e7j771gufmkq5cmupc523qeb9jlij394.apps.googleusercontent.com'
			}, {
				scope : 'publish,email',
					//redirect_uri:'http://localhost/assets/www/default/login.html'
					//redirect_uri:'http://localhost:10080/ProyectoFutbalon/apps/services/preview/Futbalon_2/android/1.0/default/login.html'
			});
	var data = null;
	function login(network) {
		ga('send', 'event', 'button', 'click', 'Presionó el boton de '+ network);
		hello(network).login().then(
						function() {
							data = hello(network).getAuthResponse();
							console.log(JSON.stringify(data));
							var token = '';
							var token_aux = '';
							//alert(JSON.stringify(hello(network).getAuthResponse()));
							token = hello(network).getAuthResponse().access_token;
							if (network == 'twitter') {
								token = hello(network).getAuthResponse().oauth_token;
								token_aux = hello(network).getAuthResponse().oauth_token_secret;
							}
							loginSocial(token, network, token_aux);
						}, function(e) {
							//alert('Signin error: ' + e.error.message);
						});

	}

	function loginSocial(passwordSocial, network, token_aux) {
		$.ajax({
					type : "POST",
					dataType : "json",
					data : {
						password : passwordSocial,
						social : network,
						password_aux : token_aux,
					},
					url : REST_HOST + "aggregators/users/loginSocialMobile2",

					error : function(xhr, ajaxOptions, thrownError) {
						hideLoading();
						$("#communicationError").data("kendoMobileModalView").open();
						//$('input:submit').attr("disabled", false);
					},
					success : function(data) {
// 						hideLoading();
						if (data.authenticated) {
						
							//console.log(JSON.stringify(data));
						    //storage.set('social', passwordSocial);// Set storage.foo to "value"
							storage.set('user', data.id);// Set storage.foo to "value"
							storage.set('isUserLogin', true);// Set storage.foo to "value"
							$(location).attr('href', "layout.html");
							hideLoading();
							// el usuario no dio permisos del correo
						} else if (data.error == -100) {
							$("#tituloFaltaPermisoUsuario").html(data.tituloError);
							$("#descFaltaPermisoUsuario").html(data.descError);
								$("#popupFaltaPermisoEmail").popup("open");
							
						} else {
							hideLoading();
							var msg = "<span class='help-block form-error' style='margin-left: 5px;'><spring:message code='mobile.login.loginErrorMessage' javaScriptEscape='true'/></span>";
							$("#error").html(msg);
						}
					},
					beforeSend : function(msg) {
						showLoading();
					}
				});
	}