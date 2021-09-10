var LoginView = (function() {
	var dao;

	// Referencia a this que permite acceder a las funciones públicas desde las funciones de jQuery.
	var self;
	var listContainerId;
	var listEventView;
	function LoginView(eventDao, listContainerId) {
		dao = eventDao;
		listContainerId = listContainerId;
		self = this;


		this.init = function() {

			$("#" + listContainerId).empty();
			insertLogin($("#" + listContainerId));
		};
		var insertLogin = function(parent) {
			parent.append(
					'<form id="form-singin" class="text-center col-lg-4" style="margin:auto">\
			<h1 class="h1 mb-3 font-weight-normal">LETTA</h1>\
			<label for="login" class="sr-only">Usuario</label> <input id="login"\
				name="login" type="text" class="form-control mb-3" placeholder="Usuario"\
				required autofocus /> <label for="password" class="sr-only">Contraseña</label>\
			<input id="password" name="password" type="password"\
				class="form-control" placeholder="Contraseña" required />\
				<button type="submit" class="btn btn-lg btn-dark btn-block mt-3">Entrar</button>\
		</form>'
			);
		};

	};



	return LoginView;
})();
