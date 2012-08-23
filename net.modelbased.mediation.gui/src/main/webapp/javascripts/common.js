
var MODEL_REPOSITORY_URL = "/mediation/repositories/models";
var MEDIATOR_REPOSITORY_URL = "/mediator"

var BACKEND_LIST = "SINTEF-MEDIATION-BACKENDS";


var defaultBackEnds = [
    { 
	"isActive": true, 
	"name": "LOCALHOST", 
	"url": "http://localhost:8080/sensapp" 
    },
    { 
	"isActive": false, 
	"name": "SINTEF-DEMO", 
	"url": "http://54.247.114.191/sensapp" 
    }
];

/**
 * @return the back ends stored in the local storage if any, or the
 * default values otherwise
 */
function getAllBackEnds() {
    var result;
    if (localStorage) {
	if (localStorage[BACKEND_LIST]) {
	    result = JSON.parse(localStorage[BACKEND_LIST]);

	} else {
	    localStorage[BACKEND_LIST] = JSON.stringify(defaultBackEnds);
	    result = defaultBackEnds
	}
    }
    return result;
}




/**
 * @return the back end that is currently active 
 */
function getActiveBackEnd() {
    var backEnds = getAllBackEnds();
    var selected = null;
    for(var i = 0 ; i<backEnds.length ; i++) {
	if (backEnds[i].isActive) {
	    selected = backEnds[i];
	}
    }    
    return selected;
}

/**
 * @desc Fetch information regarding models stored in the model
 * repository and executes the onSuccess function on the retrieved result. 
 * It display an alert dialogue when it's not possible
 * to access the model repository 
 */
function fetchModels(onSuccess) {
    fetchStuff(MODEL_REPOSITORY_URL + "?flatten=true", "get", onSuccess,
	       "Unable to reach the model repository!");
}


function fetchAlgorithms(onSuccess) {
    fetchStuff(MEDIATOR_REPOSITORY_URL + "/algorithms", "get", 
	       onSuccess, "Unable to reach the mediator service!");
}


function fetchMappings(onSuccess) {
    fetchStuff(MAPPING_REPOSITORY_URL+"?flatten=true", "get", 
	       onSuccess, "Unable to reach the mapping repository!");
}


function fetchStuff(url,method, onSuccess, errorMsg) {
    $.ajax({
	url: getActiveBackEnd().url + url,
	type: method,
	dataType: "json",
	success: onSuccess,
	failure: function () { alert(errorMsg); }
    });
}


