


var defaultBackEnds = [
    { 
	"isActive": true, 
	"name": "LOCALHOST", 
	"url": "http://localhost:8080/sensapp/" 
    },
    { 
	"isActive": false, 
	"name": "SINTEF-DEMO", 
	"url": "http://54.247.114.191/sensapp" 
    }
];


/**
 * @desc Retrieve the back-ends from the local storage, or display an
 * alert is local storage is not supported by the browser.
 *
 */
function fetchBackEnds() {
    if (localStorage) {
	$("#no-local-storage").hide();
	$("#back-ends").dataTable( {
      	    "sPaginationType": "bootstrap",
	    "bProcessing": true,
	    "aaData": defaultBackEnds,
	 /*   "aoColumns": [
		{ "mTitle": "Active?",
		  "mData": "isActive",
		  "fnRender": function ( isActive ) {
		      if (isActive) {
			  return "<input type=\"radio\" id=\"isActive\"/>"
		      }
		  },
		},
		{ 
		    "mTitle": "Back-end Name",
		    "mData": "name" 
		},
		{ 
		    "mTitle": "URL",
		    "mData": "url"
		}
	    ],  */
	    "aoColumns": [
		{ "mData": "isActive" },
		{ "mData": "name" },
		{ "mData": "url" },
	    ],
	    "oLanguage": {
		"sLengthMenu": "_MENU_ records per page"
	    }
	} );	
    } else {
	$("#no-local-storage").show();
    }
}


/**
 * Main Program
 */

$("#no-local-storage").hide();
fetchBackEnds();