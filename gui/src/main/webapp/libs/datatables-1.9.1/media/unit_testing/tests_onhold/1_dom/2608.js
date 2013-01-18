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
oTest.fnStart( "2608 - State saving escaping filters" );

$(document).ready( function () {
	$('#example').dataTable( {
		"bStateSave": true
	} );
	
	oTest.fnTest( 
		"Set the filter",
		function () {
			$('#example_filter input').val( '\\s*CVM\\s*$' );
			$('#example_filter input').keyup();
		},
		function () { return $('#example_filter input').val() == '\\s*CVM\\s*$'; }
	);
	
	oTest.fnTest( 
		"Destroy the table and remake it - checking the filter was saved",
		function () {
			$('#example').dataTable( {
				"bStateSave": true,
				"bDestroy": true
			} );
		},
		function () { return $('#example_filter input').val() == '\\s*CVM\\s*$'; }
	);
	
	oTest.fnTest( 
		"Do it again without state saving and make sure filter is empty",
		function () {
			$('#example').dataTable( {
				"bDestroy": true
			} );
		},
		function () { return $('#example_filter input').val() == ''; }
	);
	
	oTest.fnTest( 
		"Clean up",
		function () {
			$('#example').dataTable( {
				"bStateSave": true,
				"bDestroy": true
			} );
			$('#example_filter input').val( '' );
			$('#example_filter input').keyup();
		},
		function () { return $('#example_filter input').val() == ''; }
	);
	
	oTest.fnCookieDestroy( $('#example').dataTable() );
	oTest.fnComplete();
} );