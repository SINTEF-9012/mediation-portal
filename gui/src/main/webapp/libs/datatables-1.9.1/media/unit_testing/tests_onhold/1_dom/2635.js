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
oTest.fnStart( "2635 - Hiding column and state saving" );

$(document).ready( function () {
	$('#example').dataTable( {
		"bStateSave": true
	} );
	
	oTest.fnTest( 
		"Set the hidden column",
		function () {
			$('#example').dataTable().fnSetColumnVis( 2, false );
		},
		function () { return $('#example thead th').length == 4; }
	);
	
	oTest.fnTest( 
		"Destroy the table and remake it - checking one column was removed",
		function () {
			$('#example').dataTable( {
				"bStateSave": true,
				"bDestroy": true
			} );
		},
		function () { return $('#example thead th').length == 4; }
	);
	
	oTest.fnTest( 
		"Do it again without state saving and make sure we are back to 5 columns",
		function () {
			$('#example').dataTable( {
				"bDestroy": true
			} );
		},
		function () { return $('#example thead th').length == 5; }
	);
	
	oTest.fnCookieDestroy( $('#example').dataTable() );
	oTest.fnComplete();
} );