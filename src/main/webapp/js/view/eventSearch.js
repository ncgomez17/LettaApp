var EventSearchView = (function() {
	var dao;
	
	// Referencia a this que permite acceder a las funciones públicas desde las funciones de jQuery.
	var self;
	
	var listId = 'event-list';
	var listQuery = '#' + listId;
	var listContainerId;
	var page;
	var title;
	function EventSearchView(eventDao, listContainerId) {
		dao = eventDao;
		listContainerId = listContainerId;
		self = this;
		
		this.init = function(search, numberOfPage) {
			page = numberOfPage;
			title = search;
			document.getElementById(listContainerId).innerHTML='<p id="no-events-found">No se han encontrado eventos recientes</p>';
			insertEventList($('#' + listContainerId));
			dao.listEventFind(title, page, function(events,textStatus, request) {
				$.each(events, function(key, event) {
					appendToCard(event);
				});
				createPagination(page,$('#' + listContainerId),request.getResponseHeader("Event-Total-Pages"));
			},
			function() {
			    	alert('No ha sido posible acceder al listado de eventos');
					document.getElementById(listContainerId).innerHTML="";
			});
			
		};
		
	};
		
	var insertEventList = function(parent) {
		parent.append(
			'<div id="' + listId + '">\
				<div class="row">\
				</div>\
			</div>'
		);
	};
	
	var createEventRow = function(event) {
		let desc = "";
		if(event.description.length < 50) {
			desc = event.description;
		} else {
			desc = event.description.substring(0,50) + "...";
		}
		return '<div class="col-sm-4 p-3">\
				<div class="card p-3" onClick="showEvent(' + event.id + ', ' + page + ', \'' + title + '\')">\
					<img class="card-img-top img-fluid" src="data:' + event.extension + ';base64, ' + event.image + '" alt="Card image cap"/>\
					<div class="card-block">\
						<br/><h4 class="card-title">' + event.title + '</h4>\
						<p class="card-category">Categoría: ' + event.category + '</p>\
						<p class="card-date">Fecha: ' + event.date.substring(8,10) + "/" + event.date.substring(5,7) + "/" + event.date.substring(0,4) + " - " + event.date.substring(11,16) + '</p>\
						<p class="card-place">Lugar: ' + event.place + '</p>\
						<p class="card-capacity">Aforo: ' + event.capacity + '</p>\
						<br><p class="card-text">' + desc + '</p>\
					</div>\
				</div>\
			</div>';
	};
		
	var showErrorMessage = function(jqxhr, textStatus, error) {
		alert(textStatus + ": " + error);
	};

	var appendToCard = function(event) {
		$( "#no-events-found" ).remove();
		$( ".row" ).append( createEventRow(event));
	};
	
	var createPagination = function(page,parent,totalPages){
		parent.append(
		'<nav aria-label="Page navigation example" id="pagination_scroll">\
		<ul class="pagination">\
	    	<li class="page-item"><a id="first_page_link" class="page-link" href="#">\<\<</a></li>\
			'+(parseInt(page) - 1 >= 0? '<li id="previous_page_link" class="page-item"><a class="page-link" href="#">\<</a></li>':'')+'\
			'+(parseInt(page) - 2 >= 0? '<li id="'+ (parseInt(page) - 2) +'_page_link" class="page-item"><a class="page-link" href="#">'+ (parseInt(page) - 2) +'</a></li>':'')+'\
	    	'+(parseInt(page) - 1 >= 0? '<li id="'+ (parseInt(page) - 1) +'_page_link" class="page-item"><a class="page-link" href="#">'+ (parseInt(page) - 1) +'</a></li>':'')+'\
	    	<li id="current_page_link" class="page-item"><a class="page-link" href="#">'+ (parseInt(page)) +'</a></li>\
			'+ (parseInt(page) + 1 < parseInt(totalPages)? '<li id="'+ (parseInt(page) + 1) +'_page_link" class="page-item"><a class="page-link" href="#">'+ (parseInt(page) + 1) +'</a></li>':'')+'\
			'+(parseInt(page) + 2 < parseInt(totalPages)? '<li id="'+ (parseInt(page) + 2) +'_page_link" class="page-item"><a class="page-link" href="#">'+ (parseInt(page) + 2) +'</a></li>':'')+'\
			'+(parseInt(page) + 1 < parseInt(totalPages)? '<li id="next_page_link" class="page-item"><a class="page-link" href="#">\></a></li>':'')+'\
	    	<li class="page-item"><a id="last_page_link" class="page-link" href="#">\>\></a></li>\
	  	</ul>\
		</nav>'
		);
		$('#first_page_link' ).click(function(){
			self.init(0);
		});
		$('#previous_page_link' ).click(function(){
			self.init(title,parseInt(page) - 1);
		});
		$('#next_page_link' ).click(function(){
			self.init(title,parseInt(page) + 1);
		});
		$('\#'+ (parseInt(page) - 1) +'_page_link' ).click(function(){
			self.init(title,parseInt(page) - 1);
		});
		$('\#'+ (parseInt(page) - 2) +'_page_link' ).click(function(){
			self.init(title,parseInt(page) - 2);
		});
		$('\#'+ (parseInt(page) + 1 )+'_page_link' ).click(function(){
			self.init(title,parseInt(page) + 1);
		});
		$('\#'+ (parseInt(page) + 2 )+'_page_link' ).click(function(){
			self.init(title,parseInt(page) + 2);
		});
		$('#last_page_link').click(function(){
			self.init(title,parseInt(totalPages)-1);
		});
	}
	
	
	return EventSearchView;
})();

