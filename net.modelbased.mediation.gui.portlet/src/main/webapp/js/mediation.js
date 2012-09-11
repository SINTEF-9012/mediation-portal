/*
 * Controls the execution of the mediation portlet, events, callbacks,
 * and other basic interaction with the GUI.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 */

// Global variable shared among all portlets
var Receiver = {};

/**
 * Notify the composition portlet that the mapping is now complete and can be
 * retrieved from the repository.
 */
function notifyMappingComplete() {
	var mappingId = $("#mapping-id").innerHTML;
	alert(mappingId);
	Receiver.mappingComplete(mappingId);
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
Receiver.loadMapping = function(sender, mId) {
	$("#mapping-id").text(mId); // Update the title of the page
	fetchMappingEntriesOf(mId, function(json) {
		var table = $("#results").dataTable();
		table.fnClearTable();
		table.fnAddData(json);
	});
};

/*
 * MAIN PROGRAM Bind various functions to pages event
 */

$(document)
		.ready(function() {

			$('#results').dataTable({
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
