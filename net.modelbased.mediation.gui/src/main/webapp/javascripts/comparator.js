/**
 * Provides events handlers and helpers function that are used in the
 * comparator page.
 *
 * @author Franck Chauvel - SINTEF ICT
 */


/**
 * Check whether the information provided in the comparator form are
 * consistent so as to trigger a comparison request. If the
 * information are sufficient, we activate the submit button.
 *
 */
function validateComparison() {

    var oracleOk = ($("#oracle-selection").val() != "");

    var mappingsOk =  false ;
    if($("#mappings-selection").val()) {
	    mappingsOk = ($("#mappings-selection").val().length > 0);
    }

    if (oracleOk && mappingsOk) {
	$("#compare-button")[0].disabled = false;
    }    
}



/**
 * Invoke the comparator
 *
 * @param oracleId the unique ID of the oracle mapping
 *
 * @param mappingId an array of mappings that are to be compared with
 * the oracle
 */
function doCompare() {
    var oracleId = $("#oracle-selection").val();
    var mappingIds = $("#mappings-selection").val();
    compare(
	oracleId,
	mappingIds,
	comparisonSuccessful,
	comparisonComplete
    );
}


/** 
 * Callback function that is called when a comparison is
 * successful. It refresh the graphical view and disable the
 * comparison submit button.
 * 
 * @param data the URL where the comparison with the given oracle are
 * available
 */
function comparisonSuccessful(data, textStatus, jqXHR) {
    var oracleId = extractOracleId(data);
    fetchComparisonAsStatsByOracle(
	oracleId,
	function (data, textStatus, jqXHR) {
	    // We collect only the mappings that are selected
	    var result = [];
	    var selected = $("#mappings-selection").val();
	    for (i=0 ; i<selected.length ; i++) {
		var found = false;
		var index = 0;
		while (index < data.length && !found) {
		    var comparison = data[index];
		    if (data[index].mapping == selected[i]) {
			found = true;
			result[i] = data[index];
		    }
		    index = index + 1;
		}
	    }
	    
	    // we update the graph
	    updateChart(result);
	}
    );
}



/**
 * Callback function that is invoked when the comparison is over,
 * regardless of whether it succeed or it failed.
 */
function comparisonComplete(jqXHR, textStatus) {
    $("#compare-button")[0].disabled = true;
}



/*
 *
 * MAIN PROGRAM
 */ 


$(document).ready(
    function () {
	
	// Bind the list of available mappings to the oracle and
	// mappings selection
	fetchAllMappingInfo( 
	    function(json) {
		for(i=0; i < json.length; i++) {
		    var opt = "<option>" + json[i]["uid"] + "</option>";
		    $("#oracle-selection").append(opt);
		    $("#mappings-selection").append(opt);
		}
	    }
	)

    }
);