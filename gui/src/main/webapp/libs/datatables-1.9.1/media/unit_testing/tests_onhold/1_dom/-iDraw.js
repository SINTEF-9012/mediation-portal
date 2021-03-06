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
oTest.fnStart( "iDraw - check that iDraw increments for each draw" );


$(document).ready( function () {
	var oTable = $('#example').dataTable();
	var oSettings = oTable.fnSettings();
	
	oTest.fnTest( 
		"After first draw, iDraw is 1",
		null,
		function () { return oSettings.iDraw == 1; }
	);
	
	oTest.fnTest( 
		"After second draw, iDraw is 2",
		function () { oTable.fnDraw() },
		function () { return oSettings.iDraw == 2; }
	);
	
	oTest.fnTest( 
		"After sort",
		function () { oTable.fnSort([[1,'asc']]) },
		function () { return oSettings.iDraw == 3; }
	);
	
	oTest.fnTest( 
		"After filter",
		function () { oTable.fnFilter('gecko') },
		function () { return oSettings.iDraw == 4; }
	);
	
	oTest.fnTest( 
		"After another filter",
		function () { oTable.fnFilter('gec') },
		function () { return oSettings.iDraw == 5; }
	);
	
	
	oTest.fnComplete();
} );