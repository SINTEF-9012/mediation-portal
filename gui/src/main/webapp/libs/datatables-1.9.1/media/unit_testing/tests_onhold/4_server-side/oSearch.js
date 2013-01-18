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
oTest.fnStart( "oSearch" );

/* Note with my server-side scripts the regex option has no effect - this just runs that down */

$(document).ready( function () {
	/* Check the default */
	var oTable = $('#example').dataTable( {
		"bServerSide": true,
		"sAjaxSource": "../../../examples/server_side/scripts/server_processing.php"
	} );
	var oSettings = oTable.fnSettings();
	
	oTest.fnWaitTest( 
		"Default values should be blank",
		null,
		function () {
			var bReturn = oSettings.oPreviousSearch.sSearch == "" && 
			              !oSettings.oPreviousSearch.bRegex;
			return bReturn;
		}
	);
	
	/* This test might be considered iffy since the full object isn't given, but it's reasonable to
	 * expect DataTables to cope with this. It should just assumine regex false
	 */
	oTest.fnWaitTest( 
		"Search term only in object",
		function () {
			oSession.fnRestore();
			oTable = $('#example').dataTable( {
				"bServerSide": true,
		"sAjaxSource": "../../../examples/server_side/scripts/server_processing.php",
				"oSearch": {
					"sSearch": "Mozilla"
				}
			} );
		},
		function () { return $('#example tbody tr:eq(0) td:eq(3)').html() == "1"; }
	);
	
	oTest.fnWaitTest( 
		"New search will kill old one",
		function () {
			oTable.fnFilter("Opera");
		},
		function () { return $('#example tbody tr:eq(0) td:eq(1)').html() == "Opera 7.0"; }
	);
	
	oTest.fnWaitTest( 
		"Search plain text term and escape regex true",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"bServerSide": true,
				"sAjaxSource": "../../../examples/server_side/scripts/server_processing.php",
				"oSearch": {
					"sSearch": "DS",
					"bRegex": false
				}
			} );
		},
		function () { return $('#example tbody tr:eq(0) td:eq(1)').html() == "Nintendo DS browser"; }
	);
	
	oTest.fnWaitTest( 
		"Search plain text term and escape regex false",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"bServerSide": true,
				"sAjaxSource": "../../../examples/server_side/scripts/server_processing.php",
				"oSearch": {
					"sSearch": "Opera",
					"bRegex": true
				}
			} );
		},
		function () { return $('#example tbody tr:eq(0) td:eq(1)').html() == "Opera 7.0"; }
	);
	
	oTest.fnWaitTest( 
		"Search regex text term and escape regex true",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"bServerSide": true,
				"sAjaxSource": "../../../examples/server_side/scripts/server_processing.php",
				"oSearch": {
					"sSearch": "1.*",
					"bRegex": false
				}
			} );
		},
		function () { return $('#example tbody tr:eq(0) td:eq(0)').html() == "No matching records found"; }
	);
	
	
	oTest.fnComplete();
} );