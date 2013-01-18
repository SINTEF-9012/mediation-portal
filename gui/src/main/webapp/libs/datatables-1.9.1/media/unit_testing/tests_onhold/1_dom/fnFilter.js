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
oTest.fnStart( "fnFilter" );

$(document).ready( function () {
	/* Check the default */
	var oTable = $('#example').dataTable();
	oTable.fnFilter(1);
	
	oTest.fnTest( 
		"Filtering with a non-string input is valid",
		null,
		function () { return $('#example_info').html() == "Showing 1 to 10 of 32 entries (filtered from 57 total entries)"; }
	);
	
	oTest.fnComplete();
} );