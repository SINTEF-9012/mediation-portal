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
// DATA_TEMPLATE: dom_data
oTest.fnStart( "bSort" );

$(document).ready( function () {
	/* Check the default */
	$('#example').dataTable();
	
	oTest.fnTest( 
		"Sorting is on by default",
		null,
		function () { return $('#example tbody td:eq(0)').html() == "Gecko"; }
	);
	
	oTest.fnTest( 
		"Sorting Asc by default class applied",
		null,
		function () { return $('#example thead th:eq(0)').hasClass("sorting_asc"); }
	);
	
	oTest.fnTest(
		"Click on second column",
		function () { $('#example thead th:eq(1)').click(); },
		function () { return $('#example tbody td:eq(1)').html() == "All others"; }
	);
	
	oTest.fnTest( 
		"Sorting class removed from first column",
		null,
		function () { return $('#example thead th:eq(0)').hasClass("sorting_asc") != true; }
	);
	
	oTest.fnTest( 
		"Sorting asc class applied to second column",
		null,
		function () { return $('#example thead th:eq(1)').hasClass("sorting_asc"); }
	);
	
	oTest.fnTest(
		"Reverse on second column",
		function () { $('#example thead th:eq(1)').click(); },
		function () { return $('#example tbody td:eq(1)').html() == "Seamonkey 1.1"; }
	);
	
	oTest.fnTest( 
		"Sorting acs class removed from second column",
		null,
		function () { return $('#example thead th:eq(1)').hasClass("sorting_asc") != true; }
	);
	
	oTest.fnTest( 
		"Sorting desc class applied to second column",
		null,
		function () { return $('#example thead th:eq(1)').hasClass("sorting_desc"); }
	);
	
	/* Check can disable */
	oTest.fnTest( 
		"Pagiantion can be disabled",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"bSort": false
			} );
		},
		function () { return $('#example tbody td:eq(3)').html() == "4"; }
	);
	
	oTest.fnTest(
		"Disabled classes applied",
		null,
		function () { return $('#example thead th:eq(0)').hasClass('sorting_disabled'); }
	);
	
	oTest.fnTest(
		"Click on second column has no effect",
		function () { $('#example thead th:eq(1)').click(); },
		function () { return $('#example tbody td:eq(3)').html() == "4"; }
	);
	
	oTest.fnTest(
		"Reverse on second column has no effect",
		function () { $('#example thead th:eq(1)').click(); },
		function () { return $('#example tbody td:eq(3)').html() == "4"; }
	);
	
	/* Enable makes no difference */
	oTest.fnTest( 
		"Sorting enabled override",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"bSort": true
			} );
		},
		function () { return $('#example tbody td:eq(0)').html() == "Gecko"; }
	);
	
	
	
	oTest.fnComplete();
} );