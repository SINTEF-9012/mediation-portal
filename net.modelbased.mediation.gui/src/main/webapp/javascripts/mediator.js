// mediator.js

$(document).ready(function() {

    fetchModels(function(json) {
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

    
    $('#results').dataTable( {
      	"sPaginationType": "bootstrap",
	"oLanguage": {
	    "sLengthMenu": "_MENU_ records per page"
	}
    } );
} );


// retrieve models from the model repository

