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
oTest.fnStart( "bPaginate" );

$(document).ready( function () {
	/* Check the default */
	$('#example').dataTable( {
		"sAjaxSource": "../../../examples/ajax/sources/objects.txt",
		"aoColumns": [
			{ "mDataProp": "engine" },
			{ "mDataProp": "browser" },
			{ "mDataProp": "platform" },
			{ "mDataProp": "version" },
			{ "mDataProp": "grade" }
		]
	} );
	
	oTest.fnWaitTest( 
		"Pagiantion div exists by default",
		null,
		function () { return document.getElementById('example_paginate') != null; }
	);
	
	oTest.fnWaitTest(
		"Information div takes paging into account",
		null,
		function () { return document.getElementById('example_info').innerHTML == 
			"Showing 1 to 10 of 57 entries"; }
	);
	
	/* Check can disable */
	oTest.fnWaitTest( 
		"Pagiantion can be disabled",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/objects.txt",
				"aoColumnDefs": [
					{ "mDataProp": "engine", "aTargets": [0] },
					{ "mDataProp": "browser", "aTargets": [1] },
					{ "mDataProp": "platform", "aTargets": [2] },
					{ "mDataProp": "version", "aTargets": [3] },
					{ "mDataProp": "grade", "aTargets": [4] }
				],
				"bPaginate": false
			} );
		},
		function () { return document.getElementById('example_paginate') == null; }
	);
	
	oTest.fnWaitTest(
		"Information div takes paging disabled into account",
		null,
		function () { return document.getElementById('example_info').innerHTML == 
			"Showing 1 to 57 of 57 entries"; }
	);
	
	/* Enable makes no difference */
	oTest.fnWaitTest( 
		"Pagiantion enabled override",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/objects.txt",
				"aoColumnDefs": [
					{ "mDataProp": "engine", "aTargets": [0] },
					{ "mDataProp": "browser", "aTargets": [1] },
					{ "mDataProp": "platform", "aTargets": [2] },
					{ "mDataProp": "version", "aTargets": [3] },
					{ "mDataProp": "grade", "aTargets": [4] }
				],
				"bPaginate": true
			} );
		},
		function () { return document.getElementById('example_paginate') != null; }
	);
	
	
	
	oTest.fnComplete();
} );