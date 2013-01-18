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
oTest.fnStart( "iDisplayLength" );

$(document).ready( function () {
	/* Check the default */
	$('#example').dataTable( {
		"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
		"bDeferRender": true
	} );
	
	oTest.fnWaitTest( 
		"Default length is ten",
		null,
		function () { return $('#example tbody tr').length == 10; }
	);
	
	oTest.fnWaitTest( 
		"Select menu shows 10",
		null,
		function () { return $('#example_length select').val() == 10; }
	);
	
	
	oTest.fnWaitTest( 
		"Set initial length to 25",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"bDeferRender": true,
				"iDisplayLength": 25
			} );
		},
		function () { return $('#example tbody tr').length == 25; }
	);
	
	oTest.fnWaitTest( 
		"Select menu shows 25",
		null,
		function () { return $('#example_length select').val() == 25; }
	);
	
	
	oTest.fnWaitTest( 
		"Set initial length to 100",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"bDeferRender": true,
				"iDisplayLength": 100
			} );
		},
		function () { return $('#example tbody tr').length == 57; }
	);
	
	oTest.fnWaitTest( 
		"Select menu shows 25",
		null,
		function () { return $('#example_length select').val() == 100; }
	);
	
	
	oTest.fnWaitTest( 
		"Set initial length to 23 (unknown select menu length)",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"bDeferRender": true,
				"iDisplayLength": 23
			} );
		},
		function () { return $('#example tbody tr').length == 23; }
	);
	
	oTest.fnWaitTest( 
		"Select menu shows 10 (since 23 is unknow)",
		null,
		function () { return $('#example_length select').val() == 10; }
	);
	
	
	oTest.fnComplete();
} );