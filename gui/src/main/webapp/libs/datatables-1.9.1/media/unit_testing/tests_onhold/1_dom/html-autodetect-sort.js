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
// DATA_TEMPLATE: html_table
oTest.fnStart( "HTML auto detect" );

$(document).ready( function () {
	var oTable = $('#example').dataTable();
	
	oTest.fnTest( 
		"Initial sort",
		null,
		function () {
			var ret =
				$('#example tbody tr:eq(0) td:eq(0)').html() == '1' &&
				$('#example tbody tr:eq(1) td:eq(0)').html() == '2' &&
				$('#example tbody tr:eq(2) td:eq(0)').html() == '3';
			return ret;
		}
	);
	
	oTest.fnTest( 
		"HTML sort",
		function () { $('#example thead th:eq(1)').click() },
		function () {
			var ret =
				$('#example tbody tr:eq(0) td:eq(0)').html() == '2' &&
				$('#example tbody tr:eq(1) td:eq(0)').html() == '1' &&
				$('#example tbody tr:eq(2) td:eq(0)').html() == '4';
			return ret;
		}
	);
	
	oTest.fnTest( 
		"HTML reverse sort",
		function () { $('#example thead th:eq(1)').click() },
		function () {
			var ret =
				$('#example tbody tr:eq(0) td:eq(0)').html() == '3' &&
				$('#example tbody tr:eq(1) td:eq(0)').html() == '4' &&
				$('#example tbody tr:eq(2) td:eq(0)').html() == '1';
			return ret;
		}
	);
	
	oTest.fnTest( 
		"Numeric sort",
		function () { $('#example thead th:eq(0)').click() },
		function () {
			var ret =
				$('#example tbody tr:eq(0) td:eq(0)').html() == '1' &&
				$('#example tbody tr:eq(1) td:eq(0)').html() == '2' &&
				$('#example tbody tr:eq(2) td:eq(0)').html() == '3';
			return ret;
		}
	);
	
	
	oTest.fnComplete();
} );