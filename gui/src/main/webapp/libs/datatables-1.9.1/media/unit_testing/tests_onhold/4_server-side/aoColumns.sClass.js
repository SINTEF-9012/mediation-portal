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
oTest.fnStart( "aoColumns.sClass" );

$(document).ready( function () {
	/* Check the default */
	var oTable = $('#example').dataTable( {
		"bServerSide": true,
		"sAjaxSource": "../../../examples/server_side/scripts/server_processing.php"
	} );
	var oSettings = oTable.fnSettings();
	
	oTest.fnWaitTest( 
		"By default the test class hasn't been applied to the column (sanity!)",
		null,
		function () { return $('#example tbody tr:eq(0) td:eq(2)').hasClass('unittest') == false; }
	);
	
	oTest.fnWaitTest( 
		"Add a class to a single column - first row",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"bServerSide": true,
		"sAjaxSource": "../../../examples/server_side/scripts/server_processing.php",
				"aoColumns": [
					null,
					null,
					{ "sClass": 'unittest' },
					null,
					null
				]
			} );
		},
		function () { return $('#example tbody tr:eq(1) td:eq(2)').hasClass('unittest'); }
	);
	
	oTest.fnWaitTest( 
		"Add a class to a single column - third row",
		null,
		function () { return $('#example tbody tr:eq(3) td:eq(2)').hasClass('unittest'); }
	);
	
	oTest.fnWaitTest( 
		"Add a class to a single column - last row",
		null,
		function () { return $('#example tbody tr:eq(9) td:eq(2)').hasClass('unittest'); }
	);
	
	oTest.fnWaitTest( 
		"Add a class to a single column - has not applied to other columns - 1st",
		null,
		function () { return $('#example tbody tr:eq(3) td:eq(0)').hasClass('unittest') == false; }
	);
	
	oTest.fnWaitTest( 
		"Add a class to a single column - has not applied to other columns - 5th",
		null,
		function () { return $('#example tbody tr:eq(3) td:eq(4)').hasClass('unittest') == false; }
	);
	
	oTest.fnWaitTest( 
		"Add a class to a single column - seventh row - second page",
		function () { $('#example_next').click(); },
		function () { return $('#example tbody tr:eq(6) td:eq(2)').hasClass('unittest'); }
	);
	
	oTest.fnWaitTest( 
		"Add a class to a single column - has not applied to header",
		null,
		function () { return $('#example thead tr:eq(3) th:eq(4)').hasClass('unittest') == false; }
	);
	
	oTest.fnWaitTest( 
		"Add a class to a single column - has not applied to footer",
		null,
		function () { return $('#example thead tr:eq(3) th:eq(4)').hasClass('unittest') == false; }
	);
	
	
	oTest.fnWaitTest( 
		"Class defined for multiple columns - first row",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"bServerSide": true,
		"sAjaxSource": "../../../examples/server_side/scripts/server_processing.php",
				"aoColumns": [
					{ "sClass": 'unittest2' },
					null,
					null,
					{ "sClass": 'unittest1' },
					null
				]
			} );
		},
		function () {
			var bReturn = 
				$('#example tbody tr:eq(3) td:eq(0)').hasClass('unittest2') &&
				$('#example tbody tr:eq(8) td:eq(3)').hasClass('unittest1');
			return bReturn;
		}
	);
	
	oTest.fnWaitTest( 
		"Class defined for multiple columns - has not applied to other columns - 5th 1",
		null,
		function () { return $('#example tbody tr:eq(0) td:eq(4)').hasClass('unittest1') == false; }
	);
	
	oTest.fnWaitTest( 
		"Class defined for multiple columns - has not applied to other columns - 5th 2",
		null,
		function () { return $('#example tbody tr:eq(6) td:eq(4)').hasClass('unittest2') == false; }
	);
	
	
	oTest.fnComplete();
} );