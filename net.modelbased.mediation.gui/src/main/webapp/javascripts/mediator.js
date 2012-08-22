// mediator.js

$(document).ready(function() {
    $('#results').dataTable( {
      	"sPaginationType": "bootstrap",
	"oLanguage": {
	    "sLengthMenu": "_MENU_ records per page"
	}
    } );
} );
