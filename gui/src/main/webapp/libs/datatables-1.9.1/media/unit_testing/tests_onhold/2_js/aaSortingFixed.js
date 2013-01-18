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
// DATA_TEMPLATE: js_data
oTest.fnStart( "aaSortingFixed" );

$(document).ready( function () {
	/* Check the default */
	var oTable = $('#example').dataTable( {
		"aaData": gaaData
	} );
	var oSettings = oTable.fnSettings();
	
	oTest.fnTest( 
		"No fixed sorting by default",
		null,
		function () {
			return oSettings.aaSortingFixed == null;
		}
	);
	
	
	oTest.fnTest( 
		"Fixed sorting on first column (string/asc) with user sorting on second column (string/asc)",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"aaData": gaaData,
				"aaSortingFixed": [['0','asc']]
			} );
			$('#example thead th:eq(1)').click();
		},
		function () { return $('#example tbody td:eq(1)').html() == "Camino 1.0"; }
	);
	
	oTest.fnTest( 
		"Fixed sorting on first column (string/asc) with user sorting on second column (string/desc)",
		function () {
			$('#example thead th:eq(1)').click();
		},
		function () { return $('#example tbody td:eq(1)').html() == "Seamonkey 1.1"; }
	);
	
	oTest.fnTest( 
		"Fixed sorting on fourth column (int/asc) with user sorting on second column (string/asc)",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"aaData": gaaData,
				"aaSortingFixed": [['3','asc']]
			} );
			$('#example thead th:eq(1)').click();
		},
		function () { return $('#example tbody td:eq(1)').html() == "All others"; }
	);
	
	oTest.fnTest( 
		"Fixed sorting on fourth column (int/asc) with user sorting on second column (string/desc)",
		function () {
			$('#example thead th:eq(1)').click();
		},
		function () { return $('#example tbody td:eq(1)').html() == "PSP browser"; }
	);
	
	
	oTest.fnComplete();
} );