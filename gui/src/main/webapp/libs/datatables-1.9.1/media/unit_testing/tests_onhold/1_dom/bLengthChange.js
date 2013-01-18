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
oTest.fnStart( "bLengthChange" );

$(document).ready( function () {
	/* Check the default */
	$('#example').dataTable();
	
	oTest.fnTest( 
		"Length div exists by default",
		null,
		function () { return document.getElementById('example_length') != null; }
	);
	
	oTest.fnTest(
		"Four default options",
		null,
		function () { return $("select[name=example_length] option").length == 4; }
	);
	
	oTest.fnTest(
		"Default options",
		null,
		function () {
			var opts = $("select[name='example_length'] option");
			return opts[0].getAttribute('value') == 10 && opts[1].getAttribute('value') == 25 &&
				opts[2].getAttribute('value') == 50 && opts[3].getAttribute('value') == 100;
		}
	);
	
	oTest.fnTest(
		"Info takes length into account",
		null,
		function () { return document.getElementById('example_info').innerHTML == 
			"Showing 1 to 10 of 57 entries"; }
	);
	
	/* Check can disable */
	oTest.fnTest( 
		"Change length can be disabled",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"bLengthChange": false
			} );
		},
		function () { return document.getElementById('example_length') == null; }
	);
	
	oTest.fnTest(
		"Information takes length disabled into account",
		null,
		function () { return document.getElementById('example_info').innerHTML == 
			"Showing 1 to 10 of 57 entries"; }
	);
	
	/* Enable makes no difference */
	oTest.fnTest( 
		"Length change enabled override",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"bLengthChange": true
			} );
		},
		function () { return document.getElementById('example_length') != null; }
	);
	
	
	
	oTest.fnComplete();
} );