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
oTest.fnStart( "fnDeleteRow" );

$(document).ready( function () {
	/* Check the default */
	var oTable = $('#example').dataTable();
	var oSettings = oTable.fnSettings();
	
	oTest.fnTest( 
		"Check that the default data is sane",
		null,
		function () { return oSettings.asDataSearch.join(' ').match(/4.0/g).length == 3; }
	);
	
	oTest.fnTest( 
		"Remove the first data row, and check that hte search data has been updated",
		function () { oTable.fnDeleteRow( 0 ); },
		function () { return oSettings.asDataSearch.join(' ').match(/4.0/g).length == 2; }
	);
	
	oTest.fnTest( 
		"Check that the info element has been updated",
		null,
		function () { return $('#example_info').html() == "Showing 1 to 10 of 56 entries"; }
	);
	
	
	
	oTest.fnComplete();
} );