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
oTest.fnStart( "aaSorting" );

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
		"Default sorting is single column",
		null,
		function () {
			return oSettings.aaSorting.length == 1 && typeof oSettings.aaSorting[0] == 'object';
		}
	);
	
	oTest.fnWaitTest( 
		"Default sorting is first column asc",
		null,
		function () {
			return oSettings.aaSorting[0].length == 3 && oSettings.aaSorting[0][0] == 0 &&
				oSettings.aaSorting[0][1] == 'asc';
		}
	);
	
	oTest.fnWaitTest( 
		"Sorting is applied",
		null,
		function () { return $('#example tbody td:eq(0)').html() == "Gecko"; }
	);
	
	
	oTest.fnWaitTest( 
		"Custom sorting on single string column asc",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/objects.txt",
				"aoColumns": [
					{ "mDataProp": "engine" },
					{ "mDataProp": "browser" },
					{ "mDataProp": "platform" },
					{ "mDataProp": "version" },
					{ "mDataProp": "grade" }
				],
				"aaSorting": [['1','asc']]
			} );
		},
		function () { return $('#example tbody td:eq(1)').html() == "All others"; }
	);
	
	
	oTest.fnWaitTest( 
		"Custom sorting on single string column desc",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/objects.txt",
				"aoColumns": [
					{ "mDataProp": "engine" },
					{ "mDataProp": "browser" },
					{ "mDataProp": "platform" },
					{ "mDataProp": "version" },
					{ "mDataProp": "grade" }
				],
				"aaSorting": [['1','desc']]
			} );
		},
		function () { return $('#example tbody td:eq(1)').html() == "Seamonkey 1.1"; }
	);
	
	
	oTest.fnWaitTest( 
		"Custom sorting on single int column asc",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/objects.txt",
				"aoColumns": [
					{ "mDataProp": "engine" },
					{ "mDataProp": "browser" },
					{ "mDataProp": "platform" },
					{ "mDataProp": "version" },
					{ "mDataProp": "grade" }
				],
				"aaSorting": [['1','asc']]
			} );
		},
		function () { return $('#example tbody td:eq(3)').html() == "-"; }
	);
	
	
	oTest.fnWaitTest( 
		"Custom sorting on single int column desc",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/objects.txt",
				"aoColumns": [
					{ "mDataProp": "engine" },
					{ "mDataProp": "browser" },
					{ "mDataProp": "platform" },
					{ "mDataProp": "version" },
					{ "mDataProp": "grade" }
				],
				"aaSorting": [['1','desc']]
			} );
		},
		function () { return $('#example tbody td:eq(1)').html() == "Seamonkey 1.1"; }
	);
	
	
	oTest.fnWaitTest( 
		"Multi-column sorting (2 column) - string asc / string asc",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/objects.txt",
				"aoColumns": [
					{ "mDataProp": "engine" },
					{ "mDataProp": "browser" },
					{ "mDataProp": "platform" },
					{ "mDataProp": "version" },
					{ "mDataProp": "grade" }
				],
				"aaSorting": [['0','asc'], ['1','asc']]
			} );
		},
		function () { return $('#example tbody td:eq(1)').html() == "Camino 1.0"; }
	);
	
	oTest.fnWaitTest( 
		"Multi-column sorting (2 column) - string asc / string desc",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/objects.txt",
				"aoColumns": [
					{ "mDataProp": "engine" },
					{ "mDataProp": "browser" },
					{ "mDataProp": "platform" },
					{ "mDataProp": "version" },
					{ "mDataProp": "grade" }
				],
				"aaSorting": [['0','asc'], ['1','desc']]
			} );
		},
		function () { return $('#example tbody td:eq(1)').html() == "Seamonkey 1.1"; }
	);
	
	oTest.fnWaitTest( 
		"Multi-column sorting (2 column) - string desc / string asc",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/objects.txt",
				"aoColumns": [
					{ "mDataProp": "engine" },
					{ "mDataProp": "browser" },
					{ "mDataProp": "platform" },
					{ "mDataProp": "version" },
					{ "mDataProp": "grade" }
				],
				"aaSorting": [['0','desc'], ['1','asc']]
			} );
		},
		function () { return $('#example tbody td:eq(1)').html() == "iPod Touch / iPhone"; }
	);
	
	oTest.fnWaitTest( 
		"Multi-column sorting (2 column) - string desc / string desc",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/objects.txt",
				"aoColumns": [
					{ "mDataProp": "engine" },
					{ "mDataProp": "browser" },
					{ "mDataProp": "platform" },
					{ "mDataProp": "version" },
					{ "mDataProp": "grade" }
				],
				"aaSorting": [['0','desc'], ['1','desc']]
			} );
		},
		function () { return $('#example tbody td:eq(1)').html() == "Safari 3.0"; }
	);
	
	
	oTest.fnWaitTest( 
		"Multi-column sorting (2 column) - string asc / int asc",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/objects.txt",
				"aoColumns": [
					{ "mDataProp": "engine" },
					{ "mDataProp": "browser" },
					{ "mDataProp": "platform" },
					{ "mDataProp": "version" },
					{ "mDataProp": "grade" }
				],
				"aaSorting": [['0','asc'], ['3','asc']]
			} );
		},
		function () { return $('#example tbody td:eq(3)').html() == "1"; }
	);
	
	oTest.fnWaitTest( 
		"Multi-column sorting (2 column) - string asc / int desc",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/objects.txt",
				"aoColumns": [
					{ "mDataProp": "engine" },
					{ "mDataProp": "browser" },
					{ "mDataProp": "platform" },
					{ "mDataProp": "version" },
					{ "mDataProp": "grade" }
				],
				"aaSorting": [['0','asc'], ['3','desc']]
			} );
		},
		function () { return $('#example tbody td:eq(3)').html() == "1.9"; }
	);
	
	oTest.fnWaitTest( 
		"Multi-column sorting (2 column) - string desc / int asc",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/objects.txt",
				"aoColumns": [
					{ "mDataProp": "engine" },
					{ "mDataProp": "browser" },
					{ "mDataProp": "platform" },
					{ "mDataProp": "version" },
					{ "mDataProp": "grade" }
				],
				"aaSorting": [['0','desc'], ['3','asc']]
			} );
		},
		function () { return $('#example tbody td:eq(3)').html() == "125.5"; }
	);
	
	oTest.fnWaitTest( 
		"Multi-column sorting (2 column) - string desc / int desc",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/objects.txt",
				"aoColumns": [
					{ "mDataProp": "engine" },
					{ "mDataProp": "browser" },
					{ "mDataProp": "platform" },
					{ "mDataProp": "version" },
					{ "mDataProp": "grade" }
				],
				"aaSorting": [['0','desc'], ['3','desc']]
			} );
		},
		function () { return $('#example tbody td:eq(3)').html() == "522.1"; }
	);
	
	oTest.fnWaitTest( 
		"Multi-column sorting (3 column) - string asc / int asc / string asc",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/objects.txt",
				"aoColumns": [
					{ "mDataProp": "engine" },
					{ "mDataProp": "browser" },
					{ "mDataProp": "platform" },
					{ "mDataProp": "version" },
					{ "mDataProp": "grade" }
				],
				"aaSorting": [['0','asc'], ['3','asc'], ['1','asc']]
			} );
		},
		function () { return $('#example tbody tr:eq(7) td:eq(1)').html() == "Firefox 1.0"; }
	);
	
	
	oTest.fnComplete();
} );