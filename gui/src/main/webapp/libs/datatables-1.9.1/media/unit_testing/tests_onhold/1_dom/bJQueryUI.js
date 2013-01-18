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
oTest.fnStart( "bJQueryUI" );

$(document).ready( function () {
	$('#example').dataTable( {
		"bJQueryUI": true
	} );
	
	oTest.fnTest( 
		"Header elements are fully wrapped by DIVs",
		null,
		function () {
			var test = true;
			$('#example thead th').each( function () {
				if ( this.childNodes > 1 ) {
					test = false;
				}
			} );
			return test;
		}
	);
	
	oTest.fnTest( 
		"One div for each header element",
		null,
		function () {
			return $('#example thead th div').length == 5;
		}
	);
	
	oTest.fnTest( 
		"One span for each header element, nested as child of div",
		null,
		function () {
			return $('#example thead th div>span').length == 5;
		}
	);
	
	oTest.fnComplete();
} );