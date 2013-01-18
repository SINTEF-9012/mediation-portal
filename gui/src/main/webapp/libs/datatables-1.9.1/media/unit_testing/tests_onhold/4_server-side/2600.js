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
oTest.fnStart( "2600 - Display rewind when changing length" );

$(document).ready( function () {
	$('#example').dataTable( {
		"bServerSide": true,
		"sAjaxSource": "../../../examples/server_side/scripts/server_processing.php"
	} );
	
	oTest.fnWaitTest( 
		"Info correct on init",
		null,
		function () { return $('#example_info').html() == "Showing 1 to 10 of 57 entries"; }
	);
	
	oTest.fnWaitTest( 
		"Page 2",
		function () { $('#example_next').click(); },
		function () { return $('#example_info').html() == "Showing 11 to 20 of 57 entries"; }
	);
	
	oTest.fnWaitTest( 
		"Page 3",
		function () { $('#example_next').click(); },
		function () { return $('#example_info').html() == "Showing 21 to 30 of 57 entries"; }
	);
	
	oTest.fnWaitTest( 
		"Page 4",
		function () { $('#example_next').click(); },
		function () { return $('#example_info').html() == "Showing 31 to 40 of 57 entries"; }
	);
	
	oTest.fnWaitTest( 
		"Page 5",
		function () { $('#example_next').click(); },
		function () { return $('#example_info').html() == "Showing 41 to 50 of 57 entries"; }
	);
	
	oTest.fnWaitTest( 
		"Rewind",
		function () { $('#example_length select').val('100'); $('#example_length select').change(); },
		function () { return $('#example_info').html() == "Showing 1 to 57 of 57 entries"; }
	);
	
	oTest.fnComplete();
} );