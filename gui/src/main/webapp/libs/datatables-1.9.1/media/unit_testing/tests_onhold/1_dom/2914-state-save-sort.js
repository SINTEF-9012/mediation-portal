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
// DATA_TEMPLATE: dom_data
oTest.fnStart( "2914 - State saving with an empty array" );

$(document).ready( function () {
	document.cookie = "";
	$('#example').dataTable( {
		"bStateSave": true,
		"aaSorting": []
	} );
	
	oTest.fnTest( 
		"No sort",
		null,
		function () { return $('#example tbody td:eq(3)').html() == "4"; }
	);
	
	oTest.fnTest( 
		"Next page",
		function () {
			$('#example').dataTable().fnPageChange( 'next' );
		},
		function () { return $('#example tbody td:eq(1)').html() == "Camino 1.0"; }
	);
	
	oTest.fnTest( 
		"Destroy the table and remake it - checking we are still on the next page",
		function () {
			$('#example').dataTable( {
				"bStateSave": true,
					"aaSorting": [],
				"bDestroy": true
			} );
		},
		function () { return $('#example tbody td:eq(1)').html() == "Camino 1.0"; }
	);
	
	oTest.fnCookieDestroy( $('#example').dataTable() );
	oTest.fnComplete();
} );