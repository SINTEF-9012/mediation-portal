// mediator.js


/**
 * Check the values of the parameters and trigger the related
 * mediation.
 *
 */
function doMediation() { 
    var source = $("#model-source").val();
    var target = $("#model-target").val();
    var algo = $("#algo-select").val();
    var confirmed = confirm("Do you really want at start a mediation between model " 
	    + source + "' and model '" + target + "' the '"  + algo + "' algorithm ");
    if (confirmed) {
	mediate(
	    source, 
	    target, 
	    algo,
	    updateResult
	);
    }
    return false;
}

function updateResult(url) {
    console.log(url);
    alert("Mediation started!");
    var mappingId = extractMappingId(url);
    $("#mapping-id").text(mappingId);
    fetchMappingEntriesOf(mappingId, function (json) {
	var table = $("#results").dataTable();
	table.fnClearTable();
	table.fnAddData(json);
    });
}


// MAIN PROGRAM


$(document).ready(function() {

    fetchAllModelInfo(function(json) {
	for(i=0; i < json.length; i++) {
	    var opt = "<option>" + json[i]["name"] + "</option>";
	    $("#model-source").append(opt);
	    $("#model-target").append(opt);
	}
    })
    
    fetchAlgorithms(function(json) {
	for(i=0; i < json.length; i++) {
	    var opt = "<option>" + json[i] + "</option>";
	    $("#algo-select").append(opt);
	}
    })

    
//    fetchMappingEntriesOf($("mapping-id").innerHTML, function(json){
    $('#results').dataTable( {
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
    });
} );



// retrieve models from the model repository

