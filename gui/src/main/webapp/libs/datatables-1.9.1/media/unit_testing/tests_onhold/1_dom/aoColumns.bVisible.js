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
oTest.fnStart( "aoColumns.bVisible" );

$(document).ready( function () {
	/* Check the default */
	var oTable = $('#example').dataTable();
	var oSettings = oTable.fnSettings();
	
	oTest.fnTest( 
		"All columns are visible by default",
		null,
		function () { return $('#example tbody tr:eq(0) td').length == 5; }
	);
	
	oTest.fnTest( 
		"Can hide one column and it removes td column from DOM",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"aoColumns": [
					null,
					{ "bVisible": false },
					null,
					null,
					null
				]
			} );
		},
		function () { return $('#example tbody tr:eq(0) td').length == 4; }
	);
	
	oTest.fnTest( 
		"Can hide one column and it removes thead th column from DOM",
		null,
		function () { return $('#example thead tr:eq(0) th').length == 4; }
	);
	
	oTest.fnTest( 
		"Can hide one column and it removes tfoot th column from DOM",
		null,
		function () { return $('#example tfoot tr:eq(0) th').length == 4; }
	);
	
	oTest.fnTest( 
		"The correct thead column has been hidden",
		null,
		function () {
			var jqNodes = $('#example thead tr:eq(0) th');
			var bReturn = 
				jqNodes[0].innerHTML == "Rendering engine" &&
				jqNodes[1].innerHTML == "Platform(s)" &&
				jqNodes[2].innerHTML == "Engine version" &&
				jqNodes[3].innerHTML == "CSS grade";
			return bReturn;
		}
	);
	
	oTest.fnTest( 
		"The correct tbody column has been hidden",
		function () {
			oDispacher.click( $('#example thead th:eq(1)')[0], { 'shift': true } );
		},
		function () {
			var jqNodes = $('#example tbody tr:eq(0) td');
			var bReturn = 
				jqNodes[0].innerHTML == "Gecko" &&
				jqNodes[1].innerHTML == "Gnome" &&
				jqNodes[2].innerHTML == "1.8" &&
				jqNodes[3].innerHTML == "A";
			return bReturn;
		}
	);
	
	
	oTest.fnTest( 
		"Can hide multiple columns and it removes td column from DOM",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"aoColumns": [
					null,
					{ "bVisible": false },
					{ "bVisible": false },
					null,
					{ "bVisible": false }
				]
			} );
		},
		function () { return $('#example tbody tr:eq(0) td').length == 2; }
	);
	
	oTest.fnTest( 
		"Multiple hide - removes thead th column from DOM",
		null,
		function () { return $('#example thead tr:eq(0) th').length == 2; }
	);
	
	oTest.fnTest( 
		"Multiple hide - removes tfoot th column from DOM",
		null,
		function () { return $('#example tfoot tr:eq(0) th').length == 2; }
	);
	
	oTest.fnTest( 
		"Multiple hide - the correct thead columns have been hidden",
		null,
		function () {
			var jqNodes = $('#example thead tr:eq(0) th');
			var bReturn = 
				jqNodes[0].innerHTML == "Rendering engine" &&
				jqNodes[1].innerHTML == "Engine version"
			return bReturn;
		}
	);
	
	oTest.fnTest( 
		"Multiple hide - the correct tbody columns have been hidden",
		function () {
			oDispacher.click( $('#example thead th:eq(1)')[0], { 'shift': true } );
		},
		function () {
			var jqNodes = $('#example tbody tr:eq(0) td');
			var bReturn = 
				jqNodes[0].innerHTML == "Gecko" &&
				jqNodes[1].innerHTML == "1"
			return bReturn;
		}
	);
	
	
	oTest.fnComplete();
} );