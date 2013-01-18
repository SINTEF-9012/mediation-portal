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
oTest.fnStart( "5508 - Table container width doesn't change when filtering applied to scrolling table" );

$(document).ready( function () {
	$('#example').dataTable( {
		"sScrollY": "300px",
		"bPaginate": false
	} );
	
	oTest.fnTest( 
		"Width of container 800px on init with scroll",
		null,
		function () { return $('div.dataTables_scrollBody').width() == 800; }
	);
	
	oTest.fnTest( 
		"Unaltered when filter applied",
		function () { $('#example').dataTable().fnFilter('123'); },
		function () { return $('div.dataTables_scrollBody').width() == 800; }
	);
	
	oTest.fnComplete();
} );