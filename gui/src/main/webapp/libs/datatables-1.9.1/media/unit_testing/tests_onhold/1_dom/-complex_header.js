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
// DATA_TEMPLATE: -complex_header
oTest.fnStart( "Complex header" );


$(document).ready( function () {
	$('#example').dataTable();
	
	oTest.fnTest( 
		"Sorting on colspan has no effect",
		function () { $('#example thead th:eq(1)').click(); },
		function () { return $('#example tbody tr td:eq(1)').html() == "Firefox 1.0"; }
	);
	
	oTest.fnTest( 
		"Sorting on non-unique TH and first TH has no effect",
		function () { $('#example thead th:eq(2)').click(); },
		function () { return $('#example tbody tr td:eq(1)').html() == "Firefox 1.0"; }
	);
	
	oTest.fnTest( 
		"Sorting on non-unique TH and second TH will sort",
		function () { $('#example thead th:eq(6)').click(); },
		function () { return $('#example tbody tr td:eq(4)').html() == "A"; }
	);
	
	oTest.fnTest( 
		"Sorting on non-unique TH and second TH will sort - reserve",
		function () { $('#example thead th:eq(6)').click(); },
		function () { return $('#example tbody tr td:eq(4)').html() == "X"; }
	);
	
	oTest.fnTest( 
		"Sorting on unique TH will sort",
		function () { $('#example thead th:eq(5)').click(); },
		function () { return $('#example tbody tr td:eq(3)').html() == "-"; }
	);
	
	oTest.fnTest( 
		"Sorting on unique TH will sort - reserve",
		function () { $('#example thead th:eq(5)').click(); },
		function () { return $('#example tbody tr td:eq(3)').html() == "522.1"; }
	);
	
	oTest.fnTest( 
		"Sorting on unique rowspan TH will sort",
		function () { $('#example thead th:eq(0)').click(); },
		function () { return $('#example tbody tr td:eq(0)').html() == "Gecko"; }
	);
	
	
	oTest.fnComplete();
} );