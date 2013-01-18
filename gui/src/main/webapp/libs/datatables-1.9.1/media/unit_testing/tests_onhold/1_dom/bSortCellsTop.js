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
// DATA_TEMPLATE: dom_data_two_headers
oTest.fnStart( "bSortCellsTop" );

$(document).ready( function () {
	/* Check the default */
	var oTable = $('#example').dataTable();
	var oSettings = oTable.fnSettings();
	
	oTest.fnTest( 
		"Sorting class is on the bottom cells by default",
		null,
		function () { return $('#example thead tr:eq(1) th:eq(0)').hasClass('sorting_asc'); }
	);
	
	oTest.fnTest( 
		"Sorting is performed on bottom cells",
		function () { return $('#example thead tr:eq(1) th:eq(0)').click(); },
		function () { return $('#example tbody tr:eq(0) td:eq(0)').html() == "Webkit"; }
	);
	
	oTest.fnTest( 
		"Sorting class is updated on the bottom cells",
		null,
		function () { return $('#example thead tr:eq(1) th:eq(0)').hasClass('sorting_desc'); }
	);
	
	oTest.fnTest( 
		"Clicking on top cells has no effect",
		function () { return $('#example thead tr:eq(0) th:eq(0)').click(); },
		function () { return $('#example tbody tr:eq(0) td:eq(0)').html() == "Webkit"; }
	);
	
	oTest.fnTest( 
		"Clicking on another top cell has no effect",
		function () { return $('#example thead tr:eq(0) th:eq(3)').click(); },
		function () { return $('#example tbody tr:eq(0) td:eq(0)').html() == "Webkit"; }
	);
	
	
	oTest.fnTest( 
		"Sorting class is on the top cell when bSortCellsTop is true",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"bSortCellsTop": true
			} );
		},
		function () { return $('#example thead tr:eq(0) th:eq(0)').hasClass('sorting_asc'); }
	);
	
	oTest.fnTest( 
		"Sorting is performed on top cells now",
		function () { return $('#example thead tr:eq(0) th:eq(0)').click(); },
		function () { return $('#example tbody tr:eq(0) td:eq(0)').html() == "Webkit"; }
	);
	
	oTest.fnTest( 
		"Sorting class is updated on the top cells",
		null,
		function () { return $('#example thead tr:eq(0) th:eq(0)').hasClass('sorting_desc'); }
	);
	
	oTest.fnTest( 
		"Clicking on bottom cells has no effect",
		function () { return $('#example thead tr:eq(1) th:eq(0)').click(); },
		function () { return $('#example tbody tr:eq(0) td:eq(0)').html() == "Webkit"; }
	);
	
	oTest.fnTest( 
		"Clicking on another bottom cell has no effect",
		function () { return $('#example thead tr:eq(1) th:eq(3)').click(); },
		function () { return $('#example tbody tr:eq(0) td:eq(0)').html() == "Webkit"; }
	);
	
	
	oTest.fnComplete();
} );