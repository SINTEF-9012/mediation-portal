// mediator.js

$(document).ready(function() {

  fetchModels(function(json) {
      for(i=0; i < json.length; i++)
	  var opt = $("<option/>");
          opt.attr({'text': json[i].name, 'value': json[i].name})
	  $("#model-input").append();
      alert(json);
  })

    $('#results').dataTable( {
      	"sPaginationType": "bootstrap",
	"oLanguage": {
	    "sLengthMenu": "_MENU_ records per page"
	}
    } );
} );


// retrieve models from the model repository

