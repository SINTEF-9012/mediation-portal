/**
 * This file contains the mediation portal API. It permits to access the various
 * services of the portal through simple function calls
 * 
 * Requires: + JQuery
 * 
 * Contributors: + Franck Chauvel - SINTEF ICT
 * 
 */

/*
 * BACKENDS MANAGEMENT --------------------------------------------------------
 */

var BACKEND_LIST = "SINTEF-MEDIATION-BACKENDS";

var defaultBackEnds = [ {
"isActive" : true,
"name" : "LOCALHOST",
"url" : "http://localhost:8080/sensapp"
}, {
"isActive" : false,
"name" : "SINTEF-DEMO",
"url" : "http://54.247.114.191/sensapp"
} ];

/**
 * @return the back ends stored in the local storage if any, or the default
 *         values otherwise
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
	for ( var i = 0; i < backEnds.length; i++) {
		if (backEnds[i].isActive) {
			selected = backEnds[i];
		}
	}
	return selected;
}

/**
 * Activate the backend whose name is given as parameter.
 * 
 * @param the name of the back end to activate
 */
function activateBackEnd(backEndId) {
	var backEnds = getAllBackEnds();
	var selected = null;
	for ( var i = 0; i < backEnds.length; i++) {
		if (backEnds[i].name == backEndId) {
			selected = backEnds[i];
			backEnds[i].isActive = true;
		} else {
			backEnds[i].isActive = false;
		}
	}
	localStorage[BACKEND_LIST] = JSON.stringify(backEnds);
	return selected;
}

/**
 * 
 */
function addBackEnd() {

}

/*
 * MODEL REPOSITORY SERVICE
 * --------------------------------------------------------
 */

var MODEL_REPOSITORY_URL = "/mediation/repositories/models";

/**
 * @desc Extract the ID/name of the model from its url
 */
function extractModelId(url) {
	var result = url.replace("/sensapp" + MODEL_REPOSITORY_URL + "/", "");
	return result;
}

/**
 * @desc Fetch information about all the models stored in the repository
 * 
 * @param onSuccess
 *            the function to call-back in case of successful retrieval
 * 
 * @return a list of info object describing each model
 */
function fetchAllModelInfo(onSuccess) {
	asyncRestCall(MODEL_REPOSITORY_URL + "?flatten=true", "get", onSuccess, "Unable to reach the model repository!");
}

/**
 * @desc Fetch a model in the model repository,
 * 
 * @param modelId
 *            the ID of the model to fetch
 * 
 * @param onSuccess
 */
function fetchModelById(modelId, onSuccess) {
	asyncRestCall(MODEL_REPOSITORY_URL + "/" + modelId, "get", onSuccess, "Unable to reach the model repository!");
}

/**
 * @desc Fetch the information about a specific model
 * 
 * @param modelId
 *            the ID of the model
 * 
 * @param onSuccess
 *            the function to callback in case of success
 */
function fetchModelInfoById(modelId, onSuccess) {
	$
			.ajax({
			url : getActiveBackEnd().url + MODEL_REPOSITORY_URL + "/" + modelId + "/info",
			type : "get",
			dataType : "json",
			success : onSuccess,
			error : function(err) {
				alert("Unable to retrieve info about model '" + modelId + "'!");
			}
			});
}

/**
 * @desc Store a new model in the repository
 * 
 * @param id
 *            the unique ID used to identify the model in the repository
 * 
 * @param description
 *            a short description of the model
 * 
 * @param content
 *            the content of the model as a String
 * 
 * @param onSuccess
 *            the function to call back in case of success
 */
function storeModel(id, description, content, onSuccess) {
	var request = {
	"id" : id,
	"description" : description,
	"content" : content
	}
	asyncRestRequest(MODEL_REPOSITORY_URL, "post", request, onSucess, "Unable to store the model in repository!");
}

/**
 * @desc Delete the model whose ID is given
 * 
 * @param modelId
 *            the ID of the model to delete
 * 
 * @param onSuccess
 *            the function to callback in case of success
 */
function deleteModelById(modelId, onSuccess) {
	asyncRestCall(MODEL_REPOSITORY_URL + "/" + modelId, "delete", onSuccess, "Unable to remove the model (ID: '" + modelId + "') from the repository");
}

/*
 * MAPPING REPOSITORY SERVICE
 * --------------------------------------------------------
 */

var MAPPING_REPOSITORY_URL = "/mediation/repositories/mappings";

/**
 * Extract the ID of a mapping from the URL where it is available.
 * 
 * @param url
 *            the URL of the mapping whose ID is needed
 */
function extractMappingId(url) {
	var result = url.replace("/sensapp" + MAPPING_REPOSITORY_URL + "/", "");
	return result;
}

/**
 * @desc Fetch informations about all the mapppings stored in the repository
 * 
 * @param onSuccess
 *            the function callback in case of succesful retrieval
 */
function fetchAllMappingInfo(onSuccess) {
	asyncRestCall(MAPPING_REPOSITORY_URL + "?flatten=true", "get", onSuccess, "Unable to reach the mapping repository!");
}

/**
 * @desc Return all the entries of a given mapping
 * 
 * @param uid
 *            the unique ID of the mapping of interest
 * 
 * @param onSuccess
 *            the function to callback in of successful retrieval
 */
function fetchMappingEntriesOf(uid, onSuccess) {
	asyncRestCall(MAPPING_REPOSITORY_URL + "/" + uid + "/content/", "get", onSuccess, "Unable to reach the mapping repository");
}

/**
 * @desc Return all the entries of the mapping available at the given URL.
 * 
 * @param url
 *            the URL where the mapping of interest is available
 * 
 * @param onSuccess
 *            the function to callback in of successful retrieval
 */
function fetchMappingEntriesAt(url, onSuccess) {
	asyncRestCall(url + "/content/", "get", onSuccess, "Unable to reach the mapping repository");
}

/**
 * Delete a given mapping identified by its unique identifier
 * 
 * @param uid
 *            the unique ID of the mapping to delete
 * 
 */
function deleteMappingById(uid, onSuccess) {
	var url = getActiveBackEnd().url + MAPPING_REPOSITORY_URL + "/" + uid;
	$.ajax({
	url : url,
	type : "delete",
	dataType : "text",
	success : onSuccess,
	error : function(err) {
		alert("Unable to delete the mapping '" + uid + "'!");
	}
	});
}

/**
 * Disapprove a given mapping entry, identified by the mapping UID, and its
 * source and target element
 * 
 * @param source
 *            the source element of the entry of interest
 * 
 * @param target
 *            the target element of the entry of interest
 * 
 * @param onSuccess
 *            the function to callback in case of successful invocation
 */
function approve(uid, source, target, onSuccess) {
	var url = getActiveBackEnd().url + MAPPING_REPOSITORY_URL + "/" + uid + "/content/" + source + "/" + target + "/approve";
	$.ajax({
	url : url,
	type : "put",
	dataType : "text",
	success : onSuccess,
	error : function(err) {
		alert("Unable to reach the mapping repository (approval failed)!");
	}
	});
}

/**
 * Approve a given mapping entry, identified by the mapping UID, and its source
 * and target element
 * 
 * @param source
 *            the source element of the entry of interest
 * 
 * @param target
 *            the target element of the entry of interest
 * 
 * @param onSuccess
 *            the function to callback in case of successful invocation
 */
function disapprove(uid, source, target, onSuccess) {
	var url = getActiveBackEnd().url + MAPPING_REPOSITORY_URL + "/" + uid + "/content/" + source + "/" + target + "/disapprove";
	console.log(url);
	$.ajax({
	url : url,
	type : "put",
	dataType : "text",
	success : onSuccess,
	error : function(err) {
		alert("Unable to reach the mapping repository (approval failed)!");
	}
	});
}

/**
 * Set a given mapping entry as unknown. The given entry is identified by the
 * mapping UID, and its source and target element
 * 
 * @param source
 *            the source element of the entry of interest
 * 
 * @param target
 *            the target element of the entry of interest
 * 
 * @param onSuccess
 *            the function to callback in case of successful invocation
 */
function setAsUnknown(uid, source, target, onSuccess) {
	var url = getActiveBackEnd().url + MAPPING_REPOSITORY_URL + "/" + uid + "/content/" + source + "/" + target + "/unknown";
	$.ajax({
	url : url,
	type : "put",
	dataType : "text",
	success : onSuccess,
	error : function(err) {
		alert("Unable to reach the mapping repository (set as 'unknown' failed)!");
	}
	});
	return false;
}

/*
 * COMPARIONS REPOSITORY SERVICE
 * --------------------------------------------------------
 */

var COMPARISON_REPOSITORY_URL = "/mediation/repositories/comparisons";

/**
 * Extract the oracle ID from the URL of its comparisons
 */
function extractOracleId(url) {
	var result = url.replace(COMPARISON_REPOSITORY_URL + "/", "");
	return result;
}

/**
 * Retrieve a specific comparison between a specific oracle and a given mapping.
 * The complete comparison is returned.
 * 
 * @param oracleId
 *            the unique ID of the oracle in the mapping repository
 * 
 * @param mappingId
 *            the unique ID of the mapping in the mapping repository
 * 
 * @param onSuccess
 *            the function that should be call back in case of success
 */
function fetchComparisonById(oracleId, mappingId, onSuccess) {
	$
			.ajax({
			url : getActiveBackEnd().url + COMPARISON_REPOSITORY_URL + "/" + oracleId + "/" + mappingId,
			type : "get",
			dataType : "json",
			success : onSuccess,
			error : function(err) {
				alert("Unable to reach the comparison repository!");
			}
			});
	return false;
}

/**
 * Retrieve teh statistics of a given comparison between a specific oracle and a
 * given mapping. The complete comparison is returned.
 * 
 * @param oracleId
 *            the unique ID of the oracle in the mapping repository
 * 
 * @param mappingId
 *            the unique ID of the mapping in the mapping repository
 * 
 * @param onSuccess
 *            the function that should be call back in case of success
 */
function fetchComparisonAsStatsById(oracleId, mappingId, onSuccess) {
	$
			.ajax({
			url : getActiveBackEnd().url + COMPARISON_REPOSITORY_URL + "/" + oracleId + "/" + mappingId + "/stats",
			type : "get",
			dataType : "json",
			success : onSuccess,
			error : function(err) {
				alert("Unable to reach the comparison repository!");
			}
			});
	return false;
}

/**
 * Retrieve all the comparisons with a given oracle that exist in the
 * repository. The complete comparisons are returned
 * 
 * @param oracleId
 *            the unique ID of the oracle in the mapping repository
 * 
 * @param onSuccess
 *            the function that should be call back in case of success
 */
function fetchComparisonByOracle(oracleId, onSuccess) {
	$.ajax({
	url : getActiveBackEnd().url + COMPARISON_REPOSITORY_URL + "/" + oracleId,
	type : "get",
	dataType : "json",
	success : onSuccess,
	error : function(err) {
		alert("Unable to reach the comparison repository!");
	}
	});
	return false;
}

/**
 * Retrieve all the comparisons with a given oracle that exist in the
 * repository. The complete comparisons are returned
 * 
 * @param oracleId
 *            the unique ID of the oracle in the mapping repository
 * 
 * @param onSuccess
 *            the function that should be call back in case of success
 */
function fetchComparisonAsStatsByOracle(oracleId, onSuccess) {
	$
			.ajax({
			url : getActiveBackEnd().url + COMPARISON_REPOSITORY_URL + "/" + oracleId + "/stats",
			type : "get",
			dataType : "json",
			success : onSuccess,
			error : function(err) {
				alert("Unable to reach the comparison repository!");
			}
			});
	return false;
}

/**
 * Fetch all the comparison available at the given URL.
 * 
 * @param url
 *            the URL where the comparisons are expected to be
 * 
 * @param onSuccess
 *            the function to call back in case of success
 */
function fetchComparisonsAt(url, onSuccess) {
	$.ajax({
	url : getActiveBackEnd().url + url,
	type : "get",
	dataType : "json",
	success : onSuccess,
	error : function(err) {
		alert("Unable to reach the comparison repository!");
	}
	});
	return false;
}

/**
 * Fetch all the comparisons that exist in the repository. The complete
 * comparisons are returned.
 * 
 * @param onSuccess
 *            the function that should be called in case of success
 */
function fetchAllComparisons(onSuccess) {
	$.ajax({
	url : getActiveBackEnd().url + COMPARISON_REPOSITORY_URL + "?flatten=true",
	type : "get",
	dataType : "json",
	success : onSuccess,
	error : function(err) {
		alert("Unable to reach the comparison repository!");
	}
	});
	return false;
}

/**
 * Delete a comparison identified by the IDs of its oracle and of its mapping.
 * 
 * @param oracleId
 *            the ID that identifies the oracle in the mapping repository.
 * 
 * @param mappingId
 *            the ID that identifies the mapping in the mapping repository
 */
function deleteComparisonById(oracleId, mappingId, onSuccess) {
	$
			.ajax({
			url : getActiveBackEnd().url + COMPARISON_REPOSITORY_URL + "/" + oracleId + "/" + mappingId,
			type : "delete",
			success : onSuccess,
			error : function(err) {
				alert("Unable to reach the comparison repository!");
			}
			});
	return false;
}

/*
 * IMPORTER SERVICE --------------------------------------------------------
 */

var IMPORTER_URL = "/importer"

/**
 * Import a new model in the repository, and take cares of potentiel conversion
 * on the fly.
 * 
 * @param id
 *            the unique ID that one shall use to retrieve the model from the
 *            repository
 * 
 * @param format
 *            the format of the data that are send. Cnn be "ECORE", "TEXT", or
 *            "XSD".
 * 
 * @param description
 *            a short the description explaining what is the model about
 * 
 * @param the
 *            content of model as a string
 * 
 * @param onSuccess
 *            the function to call back in case of success
 * 
 */
function importModel(id, description, format, content, onSuccess, onComplete) {
	var request = {
	"modelId" : id,
	"description" : description,
	"format" : format,
	"content" : content,
	};

	$.ajax({
	url : getActiveBackEnd().url + IMPORTER_URL,
	type : "post",
	contentType : "application/json",
	data : JSON.stringify(request),
	dataType : "text",
	async : true,
	cache : false,
	success : onSuccess,
	error : function(err) {
		alert("Unable to reach the mediation 'Importer' service' \n" + JSON
				.stringify(err));
	},
	complete : onComplete
	});

	return false;
}

/**
 * @desc Retrieve the format that are supported by the Importer Services
 * 
 * @param onSuccess
 *            the function to call back in case of success
 * 
 */
function fetchSupportedFormats(onSuccess) {
	asyncRestCall(IMPORTER_URL + "/formats", "get", onSuccess, "Unable to retrieve the list of supported formats from the Importer service (" + IMPORTER_URL + ")");
}

/*
 * AGGREGATOR SERVICE --------------------------------------------------------
 */

var AGGREGATOR_URL = "/aggregator";

/**
 * Invoke the aggregator service
 * 
 * @param resultId
 *            the unique ID that should be used to store the resulting
 *            aggregation
 * 
 * @param parts
 *            an array of element of type (modelId, packageName)
 */
function aggregate(resultId, parts, onSuccess) {
	var request = {
	"resultId" : resultId,
	"parts" : parts
	};

	$.ajax({
	url : getActiveBackEnd().url + AGGREGATOR_URL,
	type : "post",
	contentType : "application/json",
	data : JSON.stringify(request),
	cache : false,
	success : onSuccess,
	error : function(err) {
		alert("Unable to reach the mediation 'aggregator' service' \n" + JSON
				.stringify(err));
	}
	});

	return false;
}

/*
 * MEDIATOR SERVICE --------------------------------------------------------
 */

var MEDIATOR_URL = "/mediator";

/**
 * Invoke the mediator service and triggers a mediation between two models
 * 
 * @param sourceId
 *            the ID of the source model
 * 
 * @param targetId
 *            the ID of the target model
 * 
 * @param algorithm
 *            the algorithm to use for a given mapping
 * 
 * @param onSuccess
 *            the function to callback if this call is successful
 */
function mediate(sourceId, targetId, algorithm, onSuccess) {
	var request = {
	"source" : sourceId,
	"target" : targetId,
	"algorithm" : algorithm
	};
	var url = getActiveBackEnd().url + MEDIATOR_URL;
	$.ajax({
	url : url,
	type : "post",
	contentType : "application/json",
	data : JSON.stringify(request),
	success : onSuccess,
	cache : false,
	error : function(err) {
		alert("Unable to reach the mediator!\n" + JSON.stringify(err));
	}
	});
}

/**
 * @desc Fetch the list of available matching algorithms
 * 
 * @param onSuccess
 *            the function to callback in case of successful retrieval
 */
function fetchAlgorithms(onSuccess) {
	asyncRestCall(MEDIATOR_URL + "/algorithms", "get", onSuccess, "Unable to reach the mediator service!");
}

/*
 * COMPARATOR SERVICE --------------------------------------------------------
 */

var COMPARATOR_URL = "/comparator";

/**
 * Invoke the comparator service and return the resulting list of comparison in
 * the comparison.
 * 
 * @param oracleId
 *            the unique ID of the oracle mapping in the mapping repository
 * 
 * @param mappingsId
 *            the IDs of the mappings which need to be compared with the oracle
 * 
 * @param onSuccess
 *            the function to callback in case of successfull comparison
 * 
 * @param onComplete
 *            the function callback once the comparisons is over, regardless of
 *            whether it failed or it succeeded.
 */
function compare(oracleId, mappingsId, onSuccess, onComplete) {
	var request = {
	"oracle" : oracleId,
	"toCompare" : mappingsId,
	"note" : "Requested by the mediation portal"
	};
	$.ajax({
	url : getActiveBackEnd().url + COMPARATOR_URL,
	type : "post",
	contentType : "application/json",
	data : JSON.stringify(request),
	dataType : "text",
	cache : false,
	success : onSuccess,
	error : function(err) {
		alert(JSON.stringify(err));
	},
	complete : onComplete
	});
	return false;
}

/*
 * Helpers functions --------------------------------------------------------
 */

/**
 * @desc Invoke the REST service available at the given URL, using the given
 *       parameters
 * 
 * @param url
 *            the URL of the REST service to invoke
 * 
 * @param method
 *            the method to use (get, post, delete, etc.)
 * 
 * @param data
 *            the JSON object describing the service request to send
 * 
 * @param onSuccess
 *            the function to callback in case of successful invocation
 * 
 * @param errorMsg
 *            the error message to display in case of failure
 */
function asyncRestCall(url, method, onSuccess, errorMsg) {
	$.ajax({
	url : getActiveBackEnd().url + url,
	type : method,
	dataType : "json",
	success : onSuccess,
	error : function(data) {
		alert(errorMsg);
	}
	});
}

/**
 * @desc Invoke the REST service available at the given URL, using the given
 *       parameters
 * 
 * @param url
 *            the URL of the REST service to invoke
 * 
 * @param method
 *            the method to use (get, post, delete, etc.)
 * 
 * @param data
 *            the JSON object describing the service request to send
 * 
 * @param onSuccess
 *            the function to callback in case of successful invocation
 * 
 * @param errorMsg
 *            the error message to display in case of failure
 */
function asyncRestRequest(url, method, data, onSuccess, errorMsg) {
	$.ajax({
	url : getActiveBackEnd().url + url,
	type : method,
	contentType : "application/json; charset=utf-8",
	data : data,
	dataType : "text",
	cache : false,
	success : onSuccess,
	error : function(err) {
		alert(JSON.stringify(err));
	}
	});
}