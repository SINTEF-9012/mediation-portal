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
oTest.fnStart( "aoColumns.iDataSort" );

$(document).ready( function () {
	/* Should know that sorting already works by default from other tests, so we can jump
	 * right in here
	 */
	var oTable = $('#example').dataTable( {
		"sAjaxSource": "../../../examples/ajax/sources/objects.txt",
		"aoColumns": [
			{ "mDataProp": "engine" },
			{ "mDataProp": "browser", "iDataSort": 4 },
			{ "mDataProp": "platform" },
			{ "mDataProp": "version" },
			{ "mDataProp": "grade" }
		]
	} );
	var oSettings = oTable.fnSettings();
	
	oTest.fnWaitTest( 
		"Sorting on first column is uneffected",
		null,
		function () { return $('#example tbody tr:eq(0) td:eq(0)').html() == 'Gecko'; }
	);
	
	oTest.fnWaitTest( 
		"Sorting on second column is the order of the fifth",
		function () { $('#example thead th:eq(1)').click(); },
		function () { return $('#example tbody tr:eq(0) td:eq(4)').html() == 'A'; }
	);
	
	oTest.fnWaitTest( 
		"Reserve sorting on second column uses fifth column as well",
		function () { $('#example thead th:eq(1)').click(); },
		function () { return $('#example tbody tr:eq(0) td:eq(4)').html() == 'X'; }
	);
	
	oTest.fnWaitTest( 
		"Sorting on 5th column retains it's own sorting",
		function () { $('#example thead th:eq(4)').click(); },
		function () { return $('#example tbody tr:eq(0) td:eq(4)').html() == 'A'; }
	);
	
	
	oTest.fnWaitTest( 
		"Use 2nd col for sorting 5th col and via-versa - no effect on first col sorting",
		function () {
			mTmp = 0;
			oSession.fnRestore();
			oTable = $('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/objects.txt",
				"aoColumns": [
					{ "mDataProp": "engine" },
					{ "mDataProp": "browser", "iDataSort": 4 },
					{ "mDataProp": "platform" },
					{ "mDataProp": "version" },
					{ "mDataProp": "grade", "iDataSort": 1 }
				]
			} );
		},
		function () { return $('#example tbody tr:eq(0) td:eq(0)').html() == 'Gecko'; }
	);
	
	oTest.fnWaitTest( 
		"2nd col sorting uses fifth col",
		function () { $('#example thead th:eq(1)').click(); },
		function () { return $('#example tbody tr:eq(0) td:eq(4)').html() == 'A'; }
	);
	
	oTest.fnWaitTest( 
		"2nd col sorting uses fifth col - reversed",
		function () { $('#example thead th:eq(1)').click(); },
		function () { return $('#example tbody tr:eq(0) td:eq(4)').html() == 'X'; }
	);
	
	oTest.fnWaitTest( 
		"5th col sorting uses 2nd col",
		function () { $('#example thead th:eq(4)').click(); },
		function () { return $('#example tbody tr:eq(0) td:eq(1)').html() == 'All others'; }
	);
	
	oTest.fnWaitTest( 
		"5th col sorting uses 2nd col - reversed",
		function () { $('#example thead th:eq(4)').click(); },
		function () { return $('#example tbody tr:eq(0) td:eq(1)').html() == 'Seamonkey 1.1'; }
	);
	
	
	oTest.fnComplete();
} );