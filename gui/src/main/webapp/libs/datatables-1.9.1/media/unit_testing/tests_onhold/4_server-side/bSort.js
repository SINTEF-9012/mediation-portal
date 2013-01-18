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
// DATA_TEMPLATE: empty_table
oTest.fnStart( "bSort" );

$(document).ready( function () {
	/* Check the default */
	$('#example').dataTable( {
		"bServerSide": true,
		"sAjaxSource": "../../../examples/server_side/scripts/server_processing.php"
	} );
	
	oTest.fnWaitTest( 
		"Sorting is on by default",
		null,
		function () { return $('#example tbody td:eq(1)').html() == "Firefox 1.0"; }
	);
	
	oTest.fnWaitTest( 
		"Sorting Asc by default class applied",
		null,
		function () { return $('#example thead th:eq(0)').hasClass("sorting_asc"); }
	);
	
	oTest.fnWaitTest(
		"Click on second column",
		function () { $('#example thead th:eq(1)').click(); },
		function () { return $('#example tbody td:eq(1)').html() == "All others"; }
	);
	
	oTest.fnWaitTest( 
		"Sorting class removed from first column",
		null,
		function () { return $('#example thead th:eq(0)').hasClass("sorting_asc") != true; }
	);
	
	oTest.fnWaitTest( 
		"Sorting asc class applied to second column",
		null,
		function () { return $('#example thead th:eq(1)').hasClass("sorting_asc"); }
	);
	
	oTest.fnWaitTest(
		"Reverse on second column",
		function () { $('#example thead th:eq(1)').click(); },
		function () { return $('#example tbody td:eq(1)').html() == "Seamonkey 1.1"; }
	);
	
	oTest.fnWaitTest( 
		"Sorting acs class removed from second column",
		null,
		function () { return $('#example thead th:eq(1)').hasClass("sorting_asc") != true; }
	);
	
	oTest.fnWaitTest( 
		"Sorting desc class applied to second column",
		null,
		function () { return $('#example thead th:eq(1)').hasClass("sorting_desc"); }
	);
	
	/* Check can disable */
	oTest.fnWaitTest( 
		"Pagiantion can be disabled",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"bServerSide": true,
		"sAjaxSource": "../../../examples/server_side/scripts/server_processing.php",
				"bSort": false
			} );
		},
		function () { return $('#example tbody td:eq(3)').html() == "4"; }
	);
	
	oTest.fnWaitTest(
		"Click on second column has no effect",
		function () { $('#example thead th:eq(1)').click(); },
		function () { return $('#example tbody td:eq(3)').html() == "4"; }
	);
	
	oTest.fnWaitTest(
		"Reverse on second column has no effect",
		function () { $('#example thead th:eq(1)').click(); },
		function () { return $('#example tbody td:eq(3)').html() == "4"; }
	);
	
	/* Enable makes no difference */
	oTest.fnWaitTest( 
		"Sorting enabled override",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"bServerSide": true,
		"sAjaxSource": "../../../examples/server_side/scripts/server_processing.php",
				"bSort": true
			} );
		},
		function () { return $('#example tbody td:eq(1)').html() == "Firefox 1.0"; }
	);
	
	
	
	oTest.fnComplete();
} );