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
oTest.fnStart( "aoColumns.bSortable" );

$(document).ready( function () {
	/* Check the default */
	var oTable = $('#example').dataTable();
	var oSettings = oTable.fnSettings();
	
	oTest.fnTest( 
		"All columns are sortable by default",
		function () { $('#example thead th:eq(1)').click(); },
		function () { return $('#example tbody tr:eq(0) td:eq(1)').html() == "All others"; }
	);
	
	oTest.fnTest( 
		"Can disable sorting from one column",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"aoColumns": [
					null,
					{ "bSortable": false },
					null,
					null,
					null
				]
			} );
			$('#example thead th:eq(1)').click();
		},
		function () { return $('#example tbody tr:eq(0) td:eq(1)').html() != "All others"; }
	);
	
	oTest.fnTest( 
		"Disabled column has no sorting class",
		null,
		function () { return $('#example thead th:eq(1)').hasClass("sorting_asc") == false; }
	);
	
	oTest.fnTest( 
		"Other columns can still sort",
		function () {
			$('#example thead th:eq(4)').click();
			$('#example thead th:eq(4)').click();
		},
		function () { return $('#example tbody tr:eq(0) td:eq(4)').html() == "X"; }
	);
	
	oTest.fnTest( 
		"Disable sorting on multiple columns - no sorting classes",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"aoColumns": [
					null,
					{ "bSortable": false },
					null,
					{ "bSortable": false },
					null
				]
			} );
		},
		function () {
			var bReturn = 
				$('#example thead th:eq(1)').hasClass("sorting") ||
				$('#example thead th:eq(3)').hasClass("sorting")
			return bReturn == false;
		}
	);
	
	oTest.fnTest( 
		"Sorting on disabled column 1 has no effect",
		function () {
			$('#example thead th:eq(1)').click();
		},
		function () { return $('#example tbody tr:eq(0) td:eq(1)').html() != "All others"; }
	);
	
	oTest.fnTest( 
		"Sorting on disabled column 2 has no effect",
		function () {
			$('#example thead th:eq(3)').click();
		},
		function () { return $('#example tbody tr:eq(0) td:eq(3)').html() != "-"; }
	);
	
	oTest.fnTest( 
		"Second sort on disabled column 2 has no effect",
		function () {
			$('#example thead th:eq(3)').click();
		},
		function () { return $('#example tbody tr:eq(0) td:eq(3)').html() != "-"; }
	);
	
	oTest.fnTest( 
		"Even with multiple disabled sorting columns other columns can still sort",
		function () {
			$('#example thead th:eq(4)').click();
			$('#example thead th:eq(4)').click();
		},
		function () { return $('#example tbody tr:eq(0) td:eq(4)').html() == "X"; }
	);
	
	
	oTest.fnComplete();
} );