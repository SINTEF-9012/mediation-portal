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
// DATA_TEMPLATE: deferred_table
/*
 */
oTest.fnStart( "Defer loading tests" );

$(document).ready( function () {
	var gotServerData = false;
	
	$('#example').dataTable( {
		"iDeferLoading": 57,
		"bServerSide": true,
		"sAjaxSource": "../../../examples/server_side/scripts/server_processing.php",
		"fnServerData": function (url, data, fn) {
			$.ajax( {
				"url": url,
				"data": data,
				"success": function(json) {
					gotServerData = true;
					fn( json );
				},
				"dataType": "json",
				"cache": false
			} );
		}
	} );
	
	oTest.fnWaitTest( 
		"10 rows shown on the first page",
		null,
		function () { return $('#example tbody tr').length == 10; }
	);
	
	oTest.fnWaitTest( 
		"No request to the server yet",
		null,
		function () { return !gotServerData; }
	);
	
	oTest.fnTest(
		"Information on zero config",
		null,
		function () { return document.getElementById('example_info').innerHTML == "Showing 1 to 10 of 57 entries"; }
	);
	
	oTest.fnWaitTest( 
		"Initial data order retained",
		null,
		function () { return $('#example tbody td:eq(0)').html() == "Gecko"; }
	);
	
	oTest.fnWaitTest( 
		"Initial data order retained 2",
		null,
		function () { return $('#example tbody td:eq(1)').html() == "Firefox 1.0"; }
	);
	
	oTest.fnWaitTest( 
		"Still no request to the server yet",
		null,
		function () { return !gotServerData; }
	);
	
	oTest.fnWaitTest( 
		"Sorting (first click) on second column",
		function () { $('#example thead th:eq(1)').click(); },
		function () { return $('#example tbody td:eq(1)').html() == "All others"; }
	);
	
	oTest.fnWaitTest( 
		"Now we've had a request",
		null,
		function () { return gotServerData; }
	);
	
	oTest.fnTest(
		"Information after sort",
		null,
		function () { return document.getElementById('example_info').innerHTML == "Showing 1 to 10 of 57 entries"; }
	);
	
	oTest.fnWaitTest( 
		"Sorting (second click) on second column",
		function () { $('#example thead th:eq(1)').click(); },
		function () { return $('#example tbody td:eq(1)').html() == "Seamonkey 1.1"; }
	);
	
	oTest.fnWaitTest( 
		"Sorting (third click) on second column",
		function () { $('#example thead th:eq(1)').click(); },
		function () { return $('#example tbody td:eq(1)').html() == "All others"; }
	);
	
	
	oTest.fnComplete();
} );