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
// DATA_TEMPLATE: dymanic_table
oTest.fnStart( "2530 - Check width's when dealing with empty strings" );


$(document).ready( function () {
	$('#example').dataTable( {
		"aaData": [
			['','Internet Explorer 4.0','Win 95+','4','X'],
			['','Internet Explorer 5.0','Win 95+','5','C']
		],
		"aoColumns": [
			{ "sTitle": "", "sWidth": "40px" },
			{ "sTitle": "Browser" },
			{ "sTitle": "Platform" },
			{ "sTitle": "Version", "sClass": "center" },
			{ "sTitle": "Grade", "sClass": "center" }
		]
	} );
	
	/* Basic checks */
	oTest.fnTest( 
		"Check calculated widths",
		null,
		function () { return $('#example tbody tr td:eq(0)').width() < 100; }
	);
	
	
	oTest.fnComplete();
} );