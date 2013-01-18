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
oTest.fnStart( "8549 - string sorting non-string types" );

$(document).ready( function () {
	var test = false;

	$.fn.dataTable.ext.sErrMode = "throw";



	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Shallow properties
	 */
	
	$('#example').dataTable( {
		"aaData": [
			[ null ],
			[ 5 ],
			[ "1a" ],
			[ new Date(0) ]
		],
		"aoColumns": [
			{ "sTitle": "Test" }
		]
	} );
	
	oTest.fnTest( 
		"Sorting works - first cell is empty",
		null,
		function () { return $('#example tbody tr:eq(0) td:eq(0)').html() === ""; }
	);
	
	oTest.fnTest( 
		"Second cell is 1a",
		null,
		function () { return $('#example tbody tr:eq(1) td:eq(0)').html() === "1a"; }
	);
	
	oTest.fnTest( 
		"Third cell is 5",
		null,
		function () { return $('#example tbody tr:eq(2) td:eq(0)').html() === "5"; }
	);
	
	
	oTest.fnComplete();
} );