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
oTest.fnStart( "bProcessing" );

/* It's actually a bit hard to set this one due to the fact that it will only be shown
 * when DataTables is doing some kind of processing. The server-side processing is a bit
 * better to test this than here - so we just the interal functions to enable it and check
 * that it is available
 */

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
		"Processing is off by default",
		null,
		function () { return oSettings.oFeatures.bProcessing == false; }
	);
	
	oTest.fnWaitTest( 
		"Processing div is not in the DOM",
		function () { oTable.oApi._fnProcessingDisplay( oSettings, true ); },
		function () { return document.getElementById('example_processing') == null; }
	);
	
	oTest.fnWaitTest( 
		"Processing div cannot be shown",
		function () { oTable.oApi._fnProcessingDisplay( oSettings, true ); },
		function () { return document.getElementById('example_processing') == null; }
	);
	
	oTest.fnWaitTest( 
		"Processing div cannot be hidden",
		function () { oTable.oApi._fnProcessingDisplay( oSettings, false ); },
		function () { return document.getElementById('example_processing') == null; }
	);
	
	
	/* Check can disable */
	oTest.fnWaitTest( 
		"Processing can be enabled",
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
				"bProcessing": true
			} );
			oSettings = oTable.fnSettings();
		},
		function () { return oSettings.oFeatures.bProcessing == true; }
	);
	
	oTest.fnWaitTest( 
		"Processing div is in the DOM",
		function () { oTable.oApi._fnProcessingDisplay( oSettings, true ); },
		function () { return document.getElementById('example_processing'); }
	);
	
	oTest.fnWaitTest( 
		"Processing div is hidden by default",
		function () { oTable.oApi._fnProcessingDisplay( oSettings, true ); },
		function () { return document.getElementById('example_processing').style.visibility = "hidden"; }
	);
	
	oTest.fnWaitTest( 
		"Processing div can be shown",
		function () { oTable.oApi._fnProcessingDisplay( oSettings, true ); },
		function () { return document.getElementById('example_processing').style.visibility = "visible"; }
	);
	
	oTest.fnWaitTest( 
		"Processing div can be hidden",
		function () { oTable.oApi._fnProcessingDisplay( oSettings, false ); },
		function () { return document.getElementById('example_processing').style.visibility = "hidden"; }
	);
	
	/* Enable makes no difference */
	oTest.fnWaitTest( 
		"Processing disabled override",
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
				"bProcessing": false
			} );
			oSettings = oTable.fnSettings();
		},
		function () { return oSettings.oFeatures.bProcessing == false; }
	);
	
	oTest.fnWaitTest( 
		"Processing div is not in the DOM",
		function () { oTable.oApi._fnProcessingDisplay( oSettings, true ); },
		function () { return document.getElementById('example_processing') == null; }
	);
	
	
	
	oTest.fnComplete();
} );