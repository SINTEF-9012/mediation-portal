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
oTest.fnStart( "aoSearchCols" );

/* We could be here forever testing this one, so we test a limited subset on a couple of colums */

$(document).ready( function () {
	/* Check the default */
	var oTable = $('#example').dataTable( {
		"sAjaxSource": "../../../examples/ajax/sources/objects.txt",
		"aoColumns": [
			{ "mDataProp": "engine" },
			{ "mDataProp": "browser" },
			{ "mDataProp": "platform" },
			{ "mDataProp": "version" },
			{ "mDataProp": "grade" }
		]
	} );
	var oSettings = oTable.fnSettings();
	
	oTest.fnWaitTest( 
		"Default should be to have a empty colums array",
		null,
		function () {
			var bReturn = 
				oSettings.aoPreSearchCols[0].sSearch == 0 && !oSettings.aoPreSearchCols[0].bRegex &&
				oSettings.aoPreSearchCols[1].sSearch == 0 && !oSettings.aoPreSearchCols[1].bRegex &&
				oSettings.aoPreSearchCols[2].sSearch == 0 && !oSettings.aoPreSearchCols[2].bRegex &&
				oSettings.aoPreSearchCols[3].sSearch == 0 && !oSettings.aoPreSearchCols[3].bRegex &&
				oSettings.aoPreSearchCols[4].sSearch == 0 && !oSettings.aoPreSearchCols[4].bRegex;
			return bReturn;
		}
	);
	
	
	oTest.fnWaitTest( 
		"Search on a single column - no regex statement given",
		function () {
			oSession.fnRestore();
			oTable = $('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/objects.txt",
				"aoColumnDefs": [
					{ "mDataProp": "engine", "aTargets": [0] },
					{ "mDataProp": "browser", "aTargets": [1] },
					{ "mDataProp": "platform", "aTargets": [2] },
					{ "mDataProp": "version", "aTargets": [3] },
					{ "mDataProp": "grade", "aTargets": [4] }
				],
				"aoSearchCols": [
					null,
					{ "sSearch": "Mozilla" },
					null,
					{ "sSearch": "1" },
					null
				]
			} );
		},
		function () { return $('#example_info').html() == "Showing 1 to 9 of 9 entries (filtered from 57 total entries)"; }
	);
	
	oTest.fnWaitTest( 
		"Search on two columns - no regex statement given",
		function () {
			oSession.fnRestore();
			oTable = $('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/objects.txt",
				"aoColumnDefs": [
					{ "mDataProp": "engine", "aTargets": [0] },
					{ "mDataProp": "browser", "aTargets": [1] },
					{ "mDataProp": "platform", "aTargets": [2] },
					{ "mDataProp": "version", "aTargets": [3] },
					{ "mDataProp": "grade", "aTargets": [4] }
				],
				"aoSearchCols": [
					null,
					{ "sSearch": "Mozilla" },
					null,
					{ "sSearch": "1.5" },
					null
				]
			} );
		},
		function () { return $('#example tbody tr:eq(0) td:eq(3)').html() == "1.5"; }
	);
	
	oTest.fnWaitTest( 
		"Search on single column - escape regex false",
		function () {
			oSession.fnRestore();
			oTable = $('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/objects.txt",
				"aoColumnDefs": [
					{ "mDataProp": "engine", "aTargets": [0] },
					{ "mDataProp": "browser", "aTargets": [1] },
					{ "mDataProp": "platform", "aTargets": [2] },
					{ "mDataProp": "version", "aTargets": [3] },
					{ "mDataProp": "grade", "aTargets": [4] }
				],
				"aoSearchCols": [
					{ "sSearch": ".*ML", "bEscapeRegex": false },
					null,
					null,
					null,
					null
				]
			} );
		},
		function () { return $('#example_info').html() == "Showing 1 to 3 of 3 entries (filtered from 57 total entries)"; }
	);
	
	oTest.fnWaitTest( 
		"Search on two columns - escape regex false on first, true on second",
		function () {
			oSession.fnRestore();
			oTable = $('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/objects.txt",
				"aoColumnDefs": [
					{ "mDataProp": "engine", "aTargets": [0] },
					{ "mDataProp": "browser", "aTargets": [1] },
					{ "mDataProp": "platform", "aTargets": [2] },
					{ "mDataProp": "version", "aTargets": [3] },
					{ "mDataProp": "grade", "aTargets": [4] }
				],
				"aoSearchCols": [
					{ "sSearch": ".*ML", "bEscapeRegex": false },
					{ "sSearch": "3.3", "bEscapeRegex": true },
					null,
					null,
					null
				]
			} );
		},
		function () { return $('#example tbody tr:eq(0) td:eq(1)').html() == "Konqureror 3.3"; }
	);
	
	oTest.fnWaitTest( 
		"Search on two columns (no records) - escape regex false on first, true on second",
		function () {
			oSession.fnRestore();
			oTable = $('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/objects.txt",
				"aoColumnDefs": [
					{ "mDataProp": "engine", "aTargets": [0] },
					{ "mDataProp": "browser", "aTargets": [1] },
					{ "mDataProp": "platform", "aTargets": [2] },
					{ "mDataProp": "version", "aTargets": [3] },
					{ "mDataProp": "grade", "aTargets": [4] }
				],
				"aoSearchCols": [
					{ "sSearch": ".*ML", "bEscapeRegex": false },
					{ "sSearch": "Allan", "bEscapeRegex": true },
					null,
					null,
					null
				]
			} );
		},
		function () { return $('#example tbody tr:eq(0) td:eq(0)').html() == "No matching records found"; }
	);
	
	oTest.fnComplete();
} );