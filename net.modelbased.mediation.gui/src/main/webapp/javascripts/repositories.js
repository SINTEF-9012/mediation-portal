//
//

$(document).ready(function() {
    $('#models-table').dataTable( {
      	"sPaginationType": "bootstrap",
	"oLanguage": {
	    "sLengthMenu": "_MENU_ records per page"
	}
    } );
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


