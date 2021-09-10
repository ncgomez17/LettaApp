var EventView = (function() {
	var dao;

	// Referencia a this que permite acceder a las funciones públicas desde las funciones de jQuery.
	var self;
	var listContainerId;
	var listEventView;
	var returnPage;
	var title;
	
	function EventView(eventDao, listContainerId) {
		dao = eventDao;
		listContainerId = listContainerId;
		self = this;

		this.init = function(eventId, page, view, search) {
			listEventView = view;
			returnPage = page;
			// title is Null if you are not coming from searchView
			title = search;
			$("#" + listContainerId).empty();
			dao.getEvent(eventId, function(key) {
				dao.getNameCreator(key.managerId, function(name) {
					insertEvent($("#" + listContainerId), key, name);
					returnListEvent();
					joinEventListener(eventId);

				});
			});

		};
		var insertEvent = function(parent, event, managerId) {
			parent.append(
				'<div class ="event-view" style="margin-top:50px" >\
					<div id="event-container">\
					<div class="row">\
						<div class = "col-sm-5">\
						<img class="card-img-top img-fluid" src="data:' + event.extension + ';base64, ' + event.image + '" alt="Card image cap"/>\
							<h4>Ubicación:' + event.place + '</h4>\
						</div>\
								<div class = "col-sm-5">\
									<h1>' + event.title + '</h3>\
									<h5>Aforo:' + event.capacity + '</h3>\
									<h5>Categoría: ' + event.category + '</h3>\
									<h5>Fecha: ' + event.date.substring(8, 10) + "/" + event.date.substring(5, 7) + "/" + event.date.substring(0, 4) + " - " + event.date.substring(11, 16) + '</h3>\
									<h5>Creador:' + managerId + '</h3>\
									<button id="joinEvent" class="btn btn-dark" value="Apuntarse"  type="button">Apuntarse</button>\
								</div>\
					</div>\
					<div  class="row" style="margin-left:0px">\
					<p>' + event.description + '</p>\
					</div>\
				</div>\
				<button id="returnListEvents"class="btn btn-secondary" value="Volver"  type="button">Volver</button>\
				</div> '
			);
		};

	};

	var returnListEvent = function() {
		$('#returnListEvents').click(function() {
			$("#" + "event-list").empty();
			if (title == null) {
				// listEventView
				listEventView.init(returnPage);
			} else {
				// searchEventView
				listEventView.init(title, returnPage);
			}
		});
	};

	var joinEventListener = function(eventid) {
		$('#joinEvent').click(function() {
			dao.registerToEvent(1, eventid, function(name) {
				alert("Se ha apuntado al evento correctamente");
			}, function() {
				alert('No se pudo apuntar a evento');
			});
		});
		};


		return EventView;
	})();
