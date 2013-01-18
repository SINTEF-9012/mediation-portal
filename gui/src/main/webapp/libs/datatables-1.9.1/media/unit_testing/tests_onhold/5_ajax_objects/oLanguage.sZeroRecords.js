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
oTest.fnStart( "oLanguage.sZeroRecords" );

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
		"Zero records language is 'No matching records found' by default",
		null,
		function () { return oSettings.oLanguage.sZeroRecords == "No matching records found"; }
	);
	
	oTest.fnWaitTest(
		"Text is shown when empty table (after filtering)",
		function () { oTable.fnFilter('nothinghere'); },
		function () { return $('#example tbody tr td')[0].innerHTML == "No matching records found" }
	);
	
	
	
	oTest.fnWaitTest( 
		"Zero records language can be defined",
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
				"oLanguage": {
					"sZeroRecords": "unit test"
				}
			} );
			oSettings = oTable.fnSettings();
		},
		function () { return oSettings.oLanguage.sZeroRecords == "unit test"; }
	);
	
	oTest.fnWaitTest(
		"Text is shown when empty table (after filtering)",
		function () { oTable.fnFilter('nothinghere2'); },
		function () { return $('#example tbody tr td')[0].innerHTML == "unit test" }
	);
	
	
	oTest.fnComplete();
} );