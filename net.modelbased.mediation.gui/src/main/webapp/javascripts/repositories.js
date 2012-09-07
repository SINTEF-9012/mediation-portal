/*
 * Provides helpers function to fetch data from the various
 * reposiories of the mediation portal.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 */




/**
 * @desc Prefetch the model that is selected by the user. 
 * 
 * @param file the file that is selected by the user
 */
function prefetch(file) {
    if (window.File && window.FileReader && window.FileList && window.Blob) {
	console.log(file);
	var reader = new FileReader();
	reader.onload = function (evt) {
	    var content = evt.target.result;
	    $("#model-content")[0].value = content;
	    $("#import-button")[0].disabled = false;
	};

	reader.onerror = function(evt) {
	    var message = file.name + " could not be read! (ERROR CODE: " + event.target.error.code + ")";
	    console.error(message);
	    alert(message);
	};

	reader.readAsText(file);

    } else {
	alert("The File APIs are not fully supported in this browser.");
    }
    return false;
}


/**
 * @desc Import a new model in the model repository.
 */
function doImportModel() {
    if (window.File && window.FileReader && window.FileList && window.Blob) {	
	var name = $("#model-name").val();
	var description = $("#model-description").val();
	var type = $("input[id=model-type]:checked").val();
	var content = $("#model-content")[0].value;

	importModel(
	    name, 
	    description, 
	    type, 
	    content, 
	    importModelSuccessful,
	    function (jqXHR, textStatus) {
	    }
	);

    } else {
	alert("The File APIs are not fully supported in this browser.");
   
    }
    return false;
}


/**
 * Calback function used to update the table of model, when a new
 * model is successfully imported. It simply retrive the info related
 * to the new model and add them in the table.
 */
function importModelSuccessful(data, textStatus, jqXHR) {		
    var id = extractModelId(data);
    fetchModelInfoById(
	id, 
	function (data, textStatus, jqXHR) {
	    var table = $("#models-table").dataTable();
	    table.fnAddData(data);
	    table.fnDraw();
	    alert("The model is now available!");
	}
    );
    return false;
}


/**
 * Callback function used when once a model has beem imported in the
 * repository, regardless of whether it was successful or failed. It
 * reset the form.
 */
function importModelComplete(jqXHR, textStatus) {
    $("#import-button")[0].disabled = true;	    
    $("#model-description").value = "";
    $("#model-name").val("");
    var content = $("#model-content")[0].value = "";
}


/**
 * Ask a confirmation before actually deleting a model from the repository.
 */
function doDeleteModel(id, tr) {
    var result = confirm("Do you really want to remove the model '" + id + "'?");
    if (result) {
	deleteModelById(
	    id,
	    function (data) {
		var table = $("#models-table").dataTable();
		table.fnDeleteRow(tr);
		table.fnDraw();
		alert("The model '" + id +"' has been succesfully deleted.");		
	    } 
	);
    }
}


/**
 * Show a modal panel containing the content of the model.
 *
 * @param id the unique ID that identifies the model in the repository
 */
function viewModel(id) {
    fetchModelById(
	id,
	function (data) {
	    $("#model-id").text(id);
	    $("#model-details")[0].innerHTML = "<pre>" + data.content + "</pre>";
	    $("#model-view").modal("show");
	}
    );
}






/**
 * @desc Convert a JSON table of objects with fields "name",
 * "description", and "kind", into a table that can be displayed by
 * the datatable frameworks.
 * 
 * @param data the data to convert from JSON to array of array of String
 *
 * @return the equivalent array structure
 */ 
function toModelTable(data) {
    var result = [];
    for (var i = 0; i < data.length; i++) {
        var result = result.concat([[data[i]["name"], data[i]["description"], data[i]["kind"]]]);
     }
    return result;
}


/**
 * @desc Show a given mapping in a model windows
 * 
 * @param uid the unique Identifier of the mapping to show 
 */
function viewMapping(uid) {
    fetchMappingEntriesOf(uid, function (json) {
	$("#mapping-id").text(uid);
	$("#mapping-details").dataTable().fnClearTable();
	$("#mapping-details").dataTable().fnAddData(json);
	$("#mapping-view").modal("show");
    });
}


/**
 * Delete the mapping whose UID is received as input. It also delete
 * the related row in the mapping table
 *
 * @param uid the unique identifier of the mapping to be deleted
 *
 * @param tr the row that has to be deleted from the table
 */
function doDeleteMapping(uid, tr) {
   var result = confirm("Do you really want to remove the mapping '" + uid + "'?");
    if (result) {
	deleteMappingById(
	    uid,
	    function (data) {
		var table = $("#mappings-table").dataTable();
		table.fnDeleteRow(tr);
		table.fnDraw();
		alert("The mapping '" + uid +"' has been succesfully deleted.");
	    } 
	);
    }
}


/**
 * Retrieve a comparison identified by the oracle ID and the mapping
 * ID, and visualize it as statistics;
 */
function viewComparison(oracleId, mappingId) {
    fetchComparisonAsStatsById(
	oracleId,
	mappingId,
	function (data, textStatus, jqXHR) {
	    updateChart([data]);
	    $("#comparison-view").modal("show");
	}
    );
}


/**
 * Invoke the deletetion of a given comparison. The comparison is
 * identified by the ID of the oracle and the ID of the mapping.
 *
 * @param oracleId the unique ID identifying the oracle in the mapping
 * repository
 *
 * @param mappingId the unique ID identifying the mapping in the
 * mapping repository
 */
function doDeleteComparison(oracleId, mappingId, row) {
    var confirmed = confirm("Do you really want to delete the comparison between '" + oracleId + "'  and '" + mappingId + "'?");
    if (confirmed) {
	deleteComparisonById(
	    oracleId, 
	    mappingId,
	    function (data, textStatus, jqXHR) {
		var comparisons = $("#comparisons-table").dataTable();
		comparisons.fnDeleteRow(row);
		comparisons.fnDraw();
		alert("Comparison successfully deleted!");
	    }
	);
    }
}

/*
 * MAIN PROGRAM
 */

$(document).ready(function() {


    $("#import-feedback").hide();
 
    // Initialize the form, with really supported formats
    fetchSupportedFormats(
	function (result) {
	    var candidates = $("#supported-formats")[0];
	    var content = "";
	    for (i=0 ; i<result.length ; i++) {
		content = content + "<label class='radio'><input type='radio' name='optionsRadios' id='model-type' value='" + result[i].label + "'></input>" + result[i].description + "</label>"
	    }
	    candidates.innerHTML = content;
	    console.log(result);
	}
    );

    // Initialisation of the model table
    fetchAllModelInfo(function (json) {
	    $('#models-table').dataTable( {
      		"sPaginationType": "bootstrap",
		"aaData": json,
		"aoColumns": [
		    { 		
			"sTitle": "Model UID",
			"mDataProp": "name",
			"fnRender": function ( object ) { 
			    var name =  object.aData.name;
			    return "<a href\"#\" onclick=\"viewModel(\'" + name + "');\" >" + name + "</a>";
			},
			"bUseRendered": false
		    },
		    { 
			"sTitle": "Description",
			"mDataProp": "description" 
		    },
		    { 
			"sTitle": "Type",
			"mDataProp": "kind" 
		    },
		    { 		
			"sTitle": "Actions",
			"mDataProp": null,
			"fnRender": function ( object ) {
			    return "<div class=\"btn-group\">"
				+ "<button class='btn btn-mini' onclick='doDeleteModel(\"" + object.aData.name + "\", $(this).parents(\"tr\"))'><i class='icon-remove-sign'></i></button>"
				+ "<button class='btn btn-mini' onclick='doEditModel(\"" + object.aData.name + "\", $(this).parents(\"tr\"))' disabled='true'><i class='icon-pencil'></i></button>"
				+ "</div>"
			},
			"bUseRendered": false
		    }
		],
		"oLanguage": {
		    "sLengthMenu": "_MENU_ records per page"
		}
	    } );
	});
   
    // Initialize the mapping table
    fetchAllMappingInfo(function (json) {
	$('#mappings-table').dataTable( {
	    "sPaginationType": "bootstrap",
	    "aaData": json,
	    "aoColumns": [
		{
		    "sTitle": "Mapping UID",
		    "mDataProp": "uid",
		    "fnRender": function ( object ) { 
			return "<a href=\"#\" onclick=\"viewMapping(\'" + object.aData.uid + "');\" >" + object.aData.uid + "</a>";
		    },
		    "bUseRendered": false
		},
		{
		    "sTitle": "Source Model",
		    "mDataProp": "sourceId"
		},
		{
		    "sTitle": "Target Model",
		    "mDataProp": "targetID" 
		},
		{
		    "sTitle": "Capacity",
		    "mDataProp": "capacity" 
		},
		{ 
		    "sTitle": "Status",
		    "mDataProp": "status"
		},
		{ 		
		    "sTitle": "Actions",
		    "mDataProp": null,
		    "fnRender": function ( object ) {
			return "<div class=\"btn-group\">"
			    + "<button class=\"btn btn-mini\" onclick=\"doDeleteMapping('" + object.aData.uid + "', $(this).parents('tr'));\"><i class=\"icon-remove-sign\"></i></button>"
			    + "<button class=\"btn btn-mini\" onclick=\"doEditMapping('" + object.aData.uid + "', $(this).parents('tr'));\" disabled=\"true\"><i class='icon-pencil'></i></button>"
			    + "</div>";
		    },
		    "bUseRendered": false
		}
	    ],
	    "oLanguage": {
		"sLengthMenu": "_MENU_ records per page"
	    }
	} );
    });

    // Initialization of a mapping
    $('#mapping-details').dataTable( {
      	"sPaginationType": "bootstrap",
	"aaData": [],
	"aoColumns": [
	    { 		
		"sTitle": "Source Element",
		"mDataProp": "source"
	    },
	    { 
		"sTitle": "Target Element",
		"mDataProp": "target" 
	    },
	    { 
		"sTitle": "Similarity",
		"mDataProp": "degree" 
	    },
	    { 
		"sTitle": "Origin",
		"mDataProp": "origin" 
	    },
	    { 		
		"sTitle": "Confirmation?",
		"mDataProp": "isValidated",
		"fnRender": function (object) {

		    var status = { unknown: "", approved: "", disapproved: "" };
		    if (object.aData["isValidated"] == null) { 
			status["unknown"] = "active";   
		    } else if (object.aData["isValidated"] == false) {
			status["disapproved"] = "active";   
		    } else {
			status["approved"] = "active";   
		    }

		    var callback = "function (data) { alert(\'Status update successful!\'); } "
		    var uid = $("#mapping-id")[0].innerHTML;
		    var source = object.aData.source;
		    var target = object.aData.target;
		    		    
		    var unknownButton = "<button class=\"btn btn-mini " + status["unknown"] + "\" onclick=\"setUnknown('" + uid + "', '" + source + "', '" + target + "', " + callback + ");\"><i class=\"icon-question-sign\"></i></button>";
		    var approveButton = "<button class=\"btn btn-mini " + status["approved"] + "\" onclick=\"approve('" + uid + "', '" + source + "', '" + target + "', " + callback + ");\"><i class=\"icon-thumbs-up\"></i></button>" ;
		    var disapproveButton = "<button class=\"btn btn-mini " + status["disapproved"] + "\" onclick=\"disapprove('" + uid + "', '" + source + "', '" + target + "', " + callback + ");\"><i class=\"icon-thumbs-down\"></i></button>";
		    
		    return "<div class=\"btn-group\" data-toggle=\"buttons-radio\">"
			+ unknownButton
			+ approveButton
			+ disapproveButton
			+ "</div>";
		},
		"bUseRendered": false
	    }
	],
	"oLanguage": {
	    "sLengthMenu": "_MENU_ records per page"
	}
    } );
   


    fetchAllComparisons(function (json) {
	$('#comparisons-table').dataTable( {
	    "sPaginationType": "bootstrap",
	    "aaData": json,
	    "aoColumns": [
		{
		    "sTitle": "Oracle ID",
		    "mDataProp": "oracle",
		    "fnRender": function ( object ) { 
			var oracle = object.aData.oracle;
			return "<a href=\"#\" onclick=\"viewMapping(\'" + oracle + "');\" >" + oracle + "</a>";
		    },
		    "bUseRendered": false
		},
		{
		    "sTitle": "Mapping ID",
		    "mDataProp": "mapping",
		    "fnRender": function ( object ) { 
			var mapping = object.aData.mapping;
			return "<a href=\"#\" onclick=\"viewMapping(\'" + mapping + "');\" >" + mapping + "</a>";
		    },
		    "bUseRendered": false
		},
		{
		    "sTitle": "T.P.",
		    "mDataProp": "tp" 
		},
		{
		    "sTitle": "T.N.",
		    "mDataProp": "tn" 
		},
		{ 
		    "sTitle": "F.P.",
		    "mDataProp": "fp"
		},
		{ 
		    "sTitle": "F.N.",
		    "mDataProp": "fn"
		},
		{ 		
		    "sTitle": "Actions",
		    "mDataProp": null,
		    "fnRender": function ( object ) {
			var oracle = object.aData.oracle;
			var mapping = object.aData.mapping;
			return "<div class=\"btn-group\">"
			    + "<button class=\"btn btn-mini\" onclick=\"doDeleteComparison('" + oracle + "', '" + mapping + "', $(this).parents('tr'));\"><i class=\"icon-remove-sign\"></i></button>"
			    + "<button class=\"btn btn-mini\" onclick=\"viewComparison('" + oracle + "', '" + mapping + "');\"><i class=\"icon-zoom-in\"></i></button>"
			    + "</div>";
		    },
		    "bUseRendered": false
		}
	    ],
	    "oLanguage": {
		"sLengthMenu": "_MENU_ records per page"
	    }
	} );
    });

} );
