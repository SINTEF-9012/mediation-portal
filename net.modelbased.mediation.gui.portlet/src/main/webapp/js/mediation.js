/*
 * Controls the execution of the mediation portlet, events, callbacks,
 * and other basic interaction with the GUI.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 */


var MediationEvents = {};


MediationEvents.listeners = new Array(); 

MediationEvents.addListener = function(listener) {
	MediationEvents.listeners.push(listener); 
};

MediationEvents.invokeListeners = function(message) {
	for (var i = 0; i < MediationEvents.listeners.length; i++) {
		var callback = MediationEvents.listeners[i]; 
		callback(message); 
	}
};


/**
 * Function trigger when the user press the Mapping Complete button. It 
 * returns the focus to the composition portal
 * 
 */
function doMappingComplete() {
	var uid = 	$("#mapping-id").value;
	var confirmed =	confirm("Are you sure that this mapping is complete'?");
	if (confirmed) {
		MediationEvents.invokeListeners(uid);
	}
}


/**
 * Event Handler - Trigger when the URL of the needed mapping is passed to the
 * portlet. The mapping is displayed as and the title page is updated so as to
 * show the ID of the mapping.
 * 
 * @param sender
 *            the string identifying the sender of the messages
 * 
 * @param mId
 *            the unique ID identifing the mapping within the mapping repository
 * 
 */
var loadMapping = function(mId) {
	alert("Loading mapping '" + mId + "'!");
	$("#mapping-id").text(mId); // Update the title of the page
	fetchMappingEntriesOf(mId, function(json) {
		var table = $("#results").dataTable();
		table.fnClearTable();
		table.fnAddData(json);
	});
};

// we have to wait until the DOM is loaded, otherwise MyEvents namespace is not
// ensured to exist
var localOnLoad = function() {
	CompositionEvents.addListener(loadMapping);
};


var currentOnload = window.onload;
if (currentOnload != undefined) {
	if(typeof(currentOnload) == "function"){
		window.onload = function() {
			currentOnload();
			localOnLoad();
		};
	} else {
		window.onload = localOnLoad;
	};
} else {
	window.onload = localOnLoad;
};


/*
 * MAIN PROGRAM Bind various functions to pages event
 */

$(document).ajaxStop(function() { 

});


$(document)
		.ready(function() {
			
			$('#results')
					.dataTable({
					"sPaginationType" : "bootstrap",
					"aaData" : [],
					"aoColumns" : [ {
					"sTitle" : "Source Element",
					"mDataProp" : "source"
					}, {
					"sTitle" : "Target Element",
					"mDataProp" : "target"
					}, {
					"sTitle" : "Degree",
					"mDataProp" : "degree",
					"sClass" : "center",
					"sWidth" : "100px",
					"fnRender" : function(object) {
						return (object.aData.degree * 100).toFixed(2);
					},
					"bUseRendered" : false
					}, {
					"sTitle" : "Origin",
					"mDataProp" : "origin"
					}, {
					"sTitle" : "Confirmation?",
					"mDataProp" : "isValidated",
					"fnRender" : function(object) {

						var status = {
						unknown : "",
						approved : "",
						disapproved : ""
						};
						if (object.aData["isValidated"] == null) {
							status["unknown"] = "active";
						} else if (object.aData["isValidated"] == false) {
							status["disapproved"] = "active";
						} else {
							status["approved"] = "active";
						}

						var callback = "function (data) { alert(\'Status update successful!\'); } ";
						var uid = $("#mapping-id")[0].innerHTML;
						var source = object.aData.source;
						var target = object.aData.target;

						var unknownButton = "<button class=\"btn btn-mini " + status["unknown"] + "\" onclick=\"setUnknown('" + uid + "', '" + source + "', '" + target + "', " + callback + ");\"><i class=\"icon-question-sign\"></i></button>";
						var approveButton = "<button class=\"btn btn-mini " + status["approved"] + "\" onclick=\"approve('" + uid + "', '" + source + "', '" + target + "', " + callback + ");\"><i class=\"icon-thumbs-up\"></i></button>";
						var disapproveButton = "<button class=\"btn btn-mini " + status["disapproved"] + "\" onclick=\"disapprove('" + uid + "', '" + source + "', '" + target + "', " + callback + ");\"><i class=\"icon-thumbs-down\"></i></button>";

						return "<div class=\"btn-group\" data-toggle=\"buttons-radio\">" + unknownButton + approveButton + disapproveButton + "</div>";
					},
					"bUseRendered" : false
					} ],
					"oLanguage" : {
						"sLengthMenu" : "_MENU_ records per page"
					}
					});
		});
