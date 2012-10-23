/*
 * Controls the execution of the mediation portlet, events, callbacks,
 * and other basic interaction with the GUI.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 */


/**
 * Function triggered when the user press the Mapping Complete button. It returns
 * the focus to the composition portal
 * 
 */
function doMappingComplete() {
	var uid = $("#mapping-id").value;
	var confirmed = confirm("Are you sure that this mapping is complete'?");
	if (confirmed) {
		var event = { uid: uid }; 
		Liferay.fire("mappingComplete", event);
	}
}


/**
 * Event Handler - Trigger when the URL of the needed mapping is passed to the
 * portlet. The mapping is displayed as and the title page is updated so as to
 * show the ID of the mapping.
 * 
 * @param event the event that triggered this callback 
 */
var doLoadMapping = function(event) {
	var uid = event.details[0].uid;
	alert("Loading mapping '" + uid + "'!");
	$("#mapping-id").text(uid); // Update the title of the page
	fetchMappingEntriesOf(uid, function(json) {
		var table = $("#results").dataTable();
		table.fnClearTable();
		table.fnAddData(json);
	});
};


/**
 * Bind the "loadMapping(uid) event to the Javascript function doLoadMapping
 */
Liferay.on("showMapping", doLoadMapping);



Liferay.Portlet.ready(
	    function(portletId, node) {
				activateBackEnd("SINTEF-DEMO");
				$('#results').dataTable(
					{
									"sPaginationType" : "bootstrap",
									"aaData" : [],
									"aoColumns" : [
											{
												"sTitle" : "Source Element",
												"mDataProp" : "source"
											},
											{
												"sTitle" : "Target Element",
												"mDataProp" : "target"
											},
											{
												"sTitle" : "Degree",
												"mDataProp" : "degree",
												"sClass" : "center",
												"sWidth" : "100px",
												"fnRender" : function(
														object) {
													return (object.aData.degree * 100)
															.toFixed(2);
												},
												"bUseRendered" : false
											},
											{
												"sTitle" : "Origin",
												"mDataProp" : "origin"
											},
											{
												"sTitle" : "Confirmation?",
												"mDataProp" : "isValidated",
												"fnRender" : function(
														object) {

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

													var unknownButton = "<button class=\"btn btn-mini "
															+ status["unknown"]
															+ "\" onclick=\"setUnknown('"
															+ uid
															+ "', '"
															+ source
															+ "', '"
															+ target
															+ "', "
															+ callback
															+ ");\"><i class=\"icon-question-sign\"></i></button>";
													var approveButton = "<button class=\"btn btn-mini "
															+ status["approved"]
															+ "\" onclick=\"approve('"
															+ uid
															+ "', '"
															+ source
															+ "', '"
															+ target
															+ "', "
															+ callback
															+ ");\"><i class=\"icon-thumbs-up\"></i></button>";
													var disapproveButton = "<button class=\"btn btn-mini "
															+ status["disapproved"]
															+ "\" onclick=\"disapprove('"
															+ uid
															+ "', '"
															+ source
															+ "', '"
															+ target
															+ "', "
															+ callback
															+ ");\"><i class=\"icon-thumbs-down\"></i></button>";

													return "<div class=\"btn-group\" data-toggle=\"buttons-radio\">"
															+ unknownButton
															+ approveButton
															+ disapproveButton
															+ "</div>";
												},
												"bUseRendered" : false
											} ],
									"oLanguage" : {
										"sLengthMenu" : "_MENU_ records per page"
									}
								});
	    });
