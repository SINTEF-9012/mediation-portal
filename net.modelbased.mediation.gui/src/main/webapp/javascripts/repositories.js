/*
 * Provides helpers function to fetch data from the various
 * reposiories of the mediation portal.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 */


var MODEL_REPOSITORY_URL = "http://localhost:8080/sensapp/mediation/repositories/models";



/**
 * @desc Import a new model in the model repository.
 */
function importModel() {
    var name = $("#model-name").val();
    var description = $("#model-description").val();
    var type = $("input[id=model-type]:checked").val();
    var file = $("#model-content").val();
    if (file) {
	var reader = new FileReader();
	reader.onload = ( function(file) {
        return function(e) {
                  };
      })(f);
	

	}
	var req = { name: name, description: description, type: type };
	alert("name: " + name + "\ndescription: " + description + "\ntype: " + type + "\ncontent: " + content);
	
    } else {
	alert("You must specify a file!")

    }
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
 * @desc Fetch information regarding models stored in the model
 * repository and udpdate the "#models-table" table, when the result
 * are retrieved. It display an alert dialogue when it's not possible
 * to access the model repository 
 */
function fetchModels() {
    $.ajax({
	url: MODEL_REPOSITORY_URL + "?flatten=true",
	type: "get",
	dataType: "json",
	success: function (json) {
	    var models = toModelTable(json);
	    $('#models-table').dataTable( {
      		"sPaginationType": "bootstrap",
		"aaData": models,
		"aoColumns": [
		    { "sTitle": "Model UID" },
		    { "sTitle": "Description" },
		    { "sTitle": "Type" }
		], 
		"oLanguage": {
		    "sLengthMenu": "_MENU_ records per page"
		}
	    } );
	},
	failure: function () {
	    alert("Unable to reach the model repository!");
	}
    });
}


/*
 * MAIN PROGRAM
 */

$(document).ready(function() {
    // Initialisation of the model table
    fetchModels();
   
    $('#mappings-table').dataTable( {
	"sPaginationType": "bootstrap",
	"oLanguage": {
	    "sLengthMenu": "_MENU_ records per page"
	}
    } );
    $('#comparisons-table').dataTable( {
	"sPaginationType": "bootstrap",
	"oLanguage": {
	    "sLengthMenu": "_MENU_ records per page"
		  }
    } );
} );
