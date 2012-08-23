
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
 * Store the given back-ends in the local storage if supported
 */
function storeBackEnds(backEnds) {
    if (localStorage) {
	localStorage[BACKEND_LIST] = JSON.stringify(backEnds);
    
    } else {
	$("#no-local-storage").show();
	setTimeout(
	    function() {
		$("#no-local-storage").hide();
	    }, 
	    3000
	);
    }
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
 * @return the URL of the model repository that is active
 */
function getModelRepositoryUrl() {
    return getActiveBackEnd().url + "/mediation/repositories/models" 
}


/**
 * @desc Update the 'backEnds' variable so as the given back-end is
 * activated. All other back ends will be desactivated;
 *
 * @param name the name of the back-end to activate.
 */
function selectBackEnd(radio) {
    var backEnds = getAllBackEnds();
    var selected = null;
    for(var i = 0 ; i<backEnds.length ; i++) {
	if (backEnds[i].name == radio.value) {
	    selected = backEnds[i];
	    backEnds[i].isActive = true;
	} else {
	    backEnds[i].isActive = false;
	}
    }
    $("#current-backend").innerHtml = selected.name;
    $("#update-local-storage").show();
    setTimeout(
	function() {
	    $("#update-local-storage").hide();
	}, 
	3000
    );
    alert(JSON.stringify(backEnds));
    storeBackEnds(backEnds);   
    $("#back-ends").dataTable().fnClearTable();
    $("#back-ends").dataTable().fnAddData(backEnds);
}

/**
 * @desc Retrieve the back-ends from the local storage, or display an
 * alert is local storage is not supported by the browser.
 *
 */
function fetchBackEnds() {
    if (localStorage) {
	var backEnds = getAllBackEnds();
	$("#no-local-storage").hide();
	$("#back-ends").dataTable( {
      	    "sPaginationType": "bootstrap",
	    "aaData": backEnds,
	    "aoColumns": [
		{ 		
		    "sTitle": "Active?",
		    "mDataProp": "isActive",
		    "fnRender": function ( object ) {
			if (object.aData.isActive) {
			    return "<input type='radio' name='isActive' checked='true' value='" + object.aData.name + "' onclick='selectBackEnd(this)'></input>";
			} else {
			    return "<input type='radio' name='isActive' value='" + object.aData.name + "' onclick='selectBackEnd(this)'></input>";
			}
		    },  
		},
		{ 
		    "sTitle": "Name",
		    "mDataProp": "name" 
		},
		{ 
		    "sTitle": "Back-end URL",
		    "mDataProp": "url" 
		},
	    ],
	    "oLanguage": {
		"sLengthMenu": "_MENU_ records per page"
	    },
	} );	
    } else {
	$("#no-local-storage").show();
    }
}


/**
 * Main Program
 */


// Hide the no local storage message

$("#no-local-storage").hide();

$("#update-local-storage").hide();

fetchBackEnds();