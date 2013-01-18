/*
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: root
 *
 * Mediation Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * Mediation Portal is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with Mediation Portal. If not, see
 * <http://www.gnu.org/licenses/>.
 */




/**
 * Store the given back-ends in the local storage if supported
 */
function storeBackEnds(backEnds) {
    if (localStorage) {
	localStorage[BACKEND_LIST] = JSON.stringify(backEnds);
    
    } else {
	$("#no-local-storage").show();
	setTimeout(
	    function() {
		$("#no-local-storage").hide();
	    }, 
	    3000
	);
    }
}








/**
 * @desc Update the 'backEnds' variable so as the given back-end is
 * activated. All other back ends will be desactivated;
 *
 * @param name the name of the back-end to activate.
 */
function selectBackEnd(radio) {
    var backEnds = getAllBackEnds();
    var selected = null;
    for(var i = 0 ; i<backEnds.length ; i++) {
	if (backEnds[i].name == radio.value) {
	    selected = backEnds[i];
	    backEnds[i].isActive = true;
	} else {
	    backEnds[i].isActive = false;
	}
    }
    $("#current-backend").text(selected.name);
    $("#update-local-storage").show();
    setTimeout(
	function() {
	    $("#update-local-storage").hide();
	}, 
	3000
    );
    storeBackEnds(backEnds);   
    $("#back-ends").dataTable().fnClearTable();
    $("#back-ends").dataTable().fnAddData(backEnds);
}

/**
 * @desc Retrieve the back-ends from the local storage, or display an
 * alert is local storage is not supported by the browser.
 *
 */
function fetchBackEnds() {
    if (localStorage) {
	var backEnds = getAllBackEnds();
	$("#no-local-storage").hide();
	$("#back-ends").dataTable( {
      	    "sPaginationType": "bootstrap",
	    "aaData": backEnds,
	    "aoColumns": [
		{ 		
		    "sTitle": "Active?",
		    "mDataProp": "isActive",
		    "fnRender": function ( object ) {
			if (object.aData.isActive) {
			    return "<input type='radio' name='isActive' checked='true' value='" + object.aData.name + "' onclick='selectBackEnd(this)'></input>";
			} else {
			    return "<input type='radio' name='isActive' value='" + object.aData.name + "' onclick='selectBackEnd(this)'></input>";
			}
		    },  
		},
		{ 
		    "sTitle": "Name",
		    "mDataProp": "name" 
		},
		{ 
		    "sTitle": "Back-end URL",
		    "mDataProp": "url" 
		},
	    ],
	    "oLanguage": {
		"sLengthMenu": "_MENU_ records per page"
	    },
	} );	
    } else {
	$("#no-local-storage").show();
    }
}


/**
 * Main Program
 */


// Hide the no local storage message

$("#no-local-storage").hide();

$("#update-local-storage").hide();

fetchBackEnds();