var EventDAO = (function() {
	var resourcePath = "rest/event/";
	var requestByAjax = function(data, done, fail, always) {
		done = typeof done !== 'undefined' ? done : function() { };
		fail = typeof fail !== 'undefined' ? fail : function() { };
		always = typeof always !== 'undefined' ? always : function() { };


		$.ajax(data).done(done).fail(fail).always(always);
	};

	function EventDAO() {
		this.listEvent = function(page, done, fail, always) {
			requestByAjax({
				url: resourcePath + "?page=" + page,
				type: 'GET'
			}, done, fail, always);
		};
		
		this.listEventFind = function(title, page, done, fail, always) {
			requestByAjax({
				url: resourcePath + "?title=" + title + "&page=" + page,
				type: 'GET'
			}, done, fail, always);
		};

		this.getEvent = function(eventId, done, fail, always) {
			requestByAjax({
				url: resourcePath + eventId,
				type: 'GET'
			}, done, fail, always);
		};

		this.getNameCreator = function(managerId, done, fail, always) {
			requestByAjax({
				url: resourcePath + "creator/" + managerId,
				type: 'GET'
			}, done, fail, always);
		};
		
		this.registerToEvent = function(userId, eventId , done, fail, always) {
			requestByAjax({
				url: resourcePath + eventId +"/users/" + userId,
				type: 'POST'
			}, done, fail, always);
		};

	}

	return EventDAO;
})();
