
var MODEL_REPOSITORY_URL = "http://localhost:8080/sensapp/mediation/repositories/models";
var MEDIATOR_REPOSITORY_URL = "http://localhost:8080/sensapp/mediator"


/**
 * @desc Fetch information regarding models stored in the model
 * repository and executes the onSuccess function on the retrieved result. 
 * It display an alert dialogue when it's not possible
 * to access the model repository 
 */
function fetchModels(onSuccess) {
    $.ajax({
	url: MODEL_REPOSITORY_URL + "?flatten=true",
	type: "get",
	dataType: "json",
	success: onSuccess,
	failure: function () {
	    alert("Unable to reach the model repository!");
	}
    });
}


function fetchAlgorithms(onSuccess) {
    $.ajax({
	url: MEDIATOR_REPOSITORY_URL + "/algorithms",
	type: "get",
	dataType: "json",
	success: onSuccess,
	failure: function () {
	    alert("Unable to reach the model repository!");
	}
    });
}
