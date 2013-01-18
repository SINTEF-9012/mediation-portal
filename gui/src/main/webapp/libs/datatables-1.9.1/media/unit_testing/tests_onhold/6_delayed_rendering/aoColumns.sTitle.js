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
oTest.fnStart( "aoColumns.sTitle" );

$(document).ready( function () {
	/* Check the default */
	var oTable = $('#example').dataTable( {
		"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
		"bDeferRender": true
	} );
	var oSettings = oTable.fnSettings();
	
	oTest.fnWaitTest( 
		"If not given, then the columns titles are empty",
		null,
		function () {
			var jqNodes = $('#example thead tr:eq(0) th');
			var bReturn = 
				jqNodes[0].innerHTML == "Rendering engine" &&
				jqNodes[1].innerHTML == "Browser" &&
				jqNodes[2].innerHTML == "Platform(s)" &&
				jqNodes[3].innerHTML == "Engine version" &&
				jqNodes[4].innerHTML == "CSS grade";
			return bReturn;
		}
	);
	
	oTest.fnWaitTest( 
		"Can set a single column title - and others are read from DOM",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"bDeferRender": true,
				"aoColumns": [
					null,
					{ "sTitle": 'unit test' },
					null,
					null,
					null
				]
			} );
		},
		function () {
			var jqNodes = $('#example thead tr:eq(0) th');
			var bReturn = 
				jqNodes[0].innerHTML == "Rendering engine" &&
				jqNodes[1].innerHTML == "unit test" &&
				jqNodes[2].innerHTML == "Platform(s)" &&
				jqNodes[3].innerHTML == "Engine version" &&
				jqNodes[4].innerHTML == "CSS grade";
			return bReturn;
		}
	);
	
	oTest.fnWaitTest( 
		"Can set multiple column titles",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"bDeferRender": true,
				"aoColumns": [
					null,
					{ "sTitle": 'unit test 1' },
					null,
					null,
					{ "sTitle": 'unit test 2' }
				]
			} );
		},
		function () {
			var jqNodes = $('#example thead tr:eq(0) th');
			var bReturn = 
				jqNodes[0].innerHTML == "Rendering engine" &&
				jqNodes[1].innerHTML == "unit test 1" &&
				jqNodes[2].innerHTML == "Platform(s)" &&
				jqNodes[3].innerHTML == "Engine version" &&
				jqNodes[4].innerHTML == "unit test 2";
			return bReturn;
		}
	);
	
	
	oTest.fnComplete();
} );