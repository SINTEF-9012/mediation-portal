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
oTest.fnStart( "2746 - Stable sorting" );

$(document).ready( function () {
	$('#example').dataTable();
	
	oTest.fnTest( 
		"Initial sort",
		null,
		function () {
			var ret =
				$('#example tbody tr:eq(0) td:eq(0)').html() == 'Gecko' &&
				$('#example tbody tr:eq(1) td:eq(0)').html() == 'Gecko' &&
				$('#example tbody tr:eq(0) td:eq(1)').html() == 'Firefox 1.0' &&
				$('#example tbody tr:eq(1) td:eq(1)').html() == 'Firefox 1.5' &&
				$('#example tbody tr:eq(2) td:eq(1)').html() == 'Firefox 2.0';
			return ret;
		}
	);
	
	oTest.fnTest( 
		"Reserve initial sort",
		function () {
			$('#example thead th:eq(0)').click();
		},
		function () {
			var ret =
				$('#example tbody tr:eq(0) td:eq(0)').html() == 'Webkit' &&
				$('#example tbody tr:eq(1) td:eq(0)').html() == 'Webkit' &&
				$('#example tbody tr:eq(0) td:eq(1)').html() == 'Safari 1.2' &&
				$('#example tbody tr:eq(1) td:eq(1)').html() == 'Safari 1.3' &&
				$('#example tbody tr:eq(2) td:eq(1)').html() == 'Safari 2.0';
			return ret;
		}
	);
	
	oTest.fnTest( 
		"Reserve to go back to initial sort sort",
		function () {
			$('#example thead th:eq(0)').click();
		},
		function () {
			var ret =
				$('#example tbody tr:eq(0) td:eq(0)').html() == 'Gecko' &&
				$('#example tbody tr:eq(1) td:eq(0)').html() == 'Gecko' &&
				$('#example tbody tr:eq(0) td:eq(1)').html() == 'Firefox 1.0' &&
				$('#example tbody tr:eq(1) td:eq(1)').html() == 'Firefox 1.5' &&
				$('#example tbody tr:eq(2) td:eq(1)').html() == 'Firefox 2.0';
			return ret;
		}
	);
	
	oTest.fnTest( 
		"Reserve initial sort again",
		function () {
			$('#example thead th:eq(0)').click();
		},
		function () {
			var ret =
				$('#example tbody tr:eq(0) td:eq(0)').html() == 'Webkit' &&
				$('#example tbody tr:eq(1) td:eq(0)').html() == 'Webkit' &&
				$('#example tbody tr:eq(0) td:eq(1)').html() == 'Safari 1.2' &&
				$('#example tbody tr:eq(1) td:eq(1)').html() == 'Safari 1.3' &&
				$('#example tbody tr:eq(2) td:eq(1)').html() == 'Safari 2.0';
			return ret;
		}
	);
	
	oTest.fnTest( 
		"And once more back to the initial sort",
		function () {
			$('#example thead th:eq(0)').click();
		},
		function () {
			var ret =
				$('#example tbody tr:eq(0) td:eq(0)').html() == 'Gecko' &&
				$('#example tbody tr:eq(1) td:eq(0)').html() == 'Gecko' &&
				$('#example tbody tr:eq(0) td:eq(1)').html() == 'Firefox 1.0' &&
				$('#example tbody tr:eq(1) td:eq(1)').html() == 'Firefox 1.5' &&
				$('#example tbody tr:eq(2) td:eq(1)').html() == 'Firefox 2.0';
			return ret;
		}
	);
	
	oTest.fnTest( 
		"Sort on second column",
		function () {
			$('#example thead th:eq(1)').click();
		},
		function () {
			var ret =
				$('#example tbody tr:eq(0) td:eq(0)').html() == 'Other browsers' &&
				$('#example tbody tr:eq(1) td:eq(0)').html() == 'Trident' &&
				$('#example tbody tr:eq(0) td:eq(1)').html() == 'All others' &&
				$('#example tbody tr:eq(1) td:eq(1)').html() == 'AOL browser (AOL desktop)' &&
				$('#example tbody tr:eq(2) td:eq(1)').html() == 'Camino 1.0';
			return ret;
		}
	);
	
	oTest.fnTest( 
		"Reserve sort on second column",
		function () {
			$('#example thead th:eq(1)').click();
		},
		function () {
			var ret =
				$('#example tbody tr:eq(0) td:eq(0)').html() == 'Gecko' &&
				$('#example tbody tr:eq(1) td:eq(0)').html() == 'Webkit' &&
				$('#example tbody tr:eq(0) td:eq(1)').html() == 'Seamonkey 1.1' &&
				$('#example tbody tr:eq(1) td:eq(1)').html() == 'Safari 3.0' &&
				$('#example tbody tr:eq(2) td:eq(1)').html() == 'Safari 2.0';
			return ret;
		}
	);
	
	oTest.fnTest( 
		"And back to asc sorting on second column",
		function () {
			$('#example thead th:eq(1)').click();
		},
		function () {
			var ret =
				$('#example tbody tr:eq(0) td:eq(0)').html() == 'Other browsers' &&
				$('#example tbody tr:eq(1) td:eq(0)').html() == 'Trident' &&
				$('#example tbody tr:eq(0) td:eq(1)').html() == 'All others' &&
				$('#example tbody tr:eq(1) td:eq(1)').html() == 'AOL browser (AOL desktop)' &&
				$('#example tbody tr:eq(2) td:eq(1)').html() == 'Camino 1.0';
			return ret;
		}
	);
	
	oTest.fnTest( 
		"Sort on third column, having now sorted on second",
		function () {
			$('#example thead th:eq(2)').click();
		},
		function () {
			var ret =
				$('#example tbody tr:eq(0) td:eq(0)').html() == 'Other browsers' &&
				$('#example tbody tr:eq(1) td:eq(0)').html() == 'Misc' &&
				$('#example tbody tr:eq(0) td:eq(1)').html() == 'All others' &&
				$('#example tbody tr:eq(1) td:eq(1)').html() == 'Dillo 0.8' &&
				$('#example tbody tr:eq(2) td:eq(1)').html() == 'NetFront 3.1';
			return ret;
		}
	);
	
	oTest.fnTest( 
		"Reserve sort on third column",
		function () {
			$('#example thead th:eq(2)').click();
		},
		function () {
			var ret =
				$('#example tbody tr:eq(0) td:eq(0)').html() == 'Misc' &&
				$('#example tbody tr:eq(1) td:eq(0)').html() == 'Trident' &&
				$('#example tbody tr:eq(0) td:eq(1)').html() == 'IE Mobile' &&
				$('#example tbody tr:eq(1) td:eq(1)').html() == 'Internet Explorer 7' &&
				$('#example tbody tr:eq(2) td:eq(1)').html() == 'AOL browser (AOL desktop)';
			return ret;
		}
	);
	
	oTest.fnTest( 
		"Return sorting on third column to asc",
		function () {
			$('#example thead th:eq(2)').click();
		},
		function () {
			var ret =
				$('#example tbody tr:eq(0) td:eq(0)').html() == 'Other browsers' &&
				$('#example tbody tr:eq(1) td:eq(0)').html() == 'Misc' &&
				$('#example tbody tr:eq(0) td:eq(1)').html() == 'All others' &&
				$('#example tbody tr:eq(1) td:eq(1)').html() == 'Dillo 0.8' &&
				$('#example tbody tr:eq(2) td:eq(1)').html() == 'NetFront 3.1';
			return ret;
		}
	);
	
	oTest.fnTest( 
		"Sort on first column having sorted on second then third columns",
		function () {
			$('#example thead th:eq(0)').click();
		},
		function () {
			var ret =
				$('#example tbody tr:eq(0) td:eq(0)').html() == 'Gecko' &&
				$('#example tbody tr:eq(1) td:eq(0)').html() == 'Gecko' &&
				$('#example tbody tr:eq(0) td:eq(1)').html() == 'Epiphany 2.20' &&
				$('#example tbody tr:eq(1) td:eq(1)').html() == 'Camino 1.0' &&
				$('#example tbody tr:eq(2) td:eq(1)').html() == 'Camino 1.5';
			return ret;
		}
	);
	
	
	oTest.fnComplete();
} );