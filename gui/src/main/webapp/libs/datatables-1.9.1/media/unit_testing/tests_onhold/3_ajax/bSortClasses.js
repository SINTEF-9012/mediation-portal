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
oTest.fnStart( "bSortClasses" );

$(document).ready( function () {
	/* Check the default */
	$('#example').dataTable( {
		"sAjaxSource": "../../../examples/ajax/sources/arrays.txt"
	} );
	
	oTest.fnWaitTest( 
		"Sorting classes are applied by default",
		null,
		function () { return $('#example tbody tr:eq(0) td:eq(0)').hasClass('sorting_1'); }
	);
	
	oTest.fnWaitTest( 
		"Sorting classes are applied to all required cells",
		null,
		function () { return $('#example tbody tr:eq(7) td:eq(0)').hasClass('sorting_1'); }
	);
	
	oTest.fnWaitTest( 
		"Sorting classes are not applied to non-sorting columns",
		null,
		function () { return $('#example tbody tr:eq(0) td:eq(1)').hasClass('sorting_1') == false; }
	);
	
	oTest.fnWaitTest( 
		"Sorting multi-column - add column 1",
		function () { 
			oDispacher.click( $('#example thead th:eq(1)')[0], { 'shift': true } ); },
		function () {
			return $('#example tbody tr:eq(0) td:eq(0)').hasClass('sorting_1') &&
						 $('#example tbody tr:eq(0) td:eq(1)').hasClass('sorting_2');
		}
	);
	
	oTest.fnWaitTest( 
		"Sorting multi-column - add column 2",
		function () { 
			oDispacher.click( $('#example thead th:eq(2)')[0], { 'shift': true } ); },
		function () {
			return $('#example tbody tr:eq(0) td:eq(0)').hasClass('sorting_1') &&
						 $('#example tbody tr:eq(0) td:eq(1)').hasClass('sorting_2') &&
						 $('#example tbody tr:eq(0) td:eq(2)').hasClass('sorting_3');
		}
	);
	
	oTest.fnWaitTest( 
		"Sorting multi-column - add column 3",
		function () { 
			oDispacher.click( $('#example thead th:eq(3)')[0], { 'shift': true } );
		},
		function () {
			return $('#example tbody tr:eq(0) td:eq(0)').hasClass('sorting_1') &&
						 $('#example tbody tr:eq(0) td:eq(1)').hasClass('sorting_2') &&
						 $('#example tbody tr:eq(0) td:eq(2)').hasClass('sorting_3') &&
						 $('#example tbody tr:eq(0) td:eq(3)').hasClass('sorting_3');
		}
	);
	
	oTest.fnWaitTest( 
		"Remove sorting classes on single column sort",
		function () { 
			$('#example thead th:eq(4)').click();
		},
		function () {
			return $('#example tbody tr:eq(0) td:eq(0)').hasClass('sorting_1') == false &&
						 $('#example tbody tr:eq(0) td:eq(1)').hasClass('sorting_2') == false &&
						 $('#example tbody tr:eq(0) td:eq(2)').hasClass('sorting_3') == false &&
						 $('#example tbody tr:eq(0) td:eq(3)').hasClass('sorting_3') == false;
		}
	);
	
	oTest.fnWaitTest( 
		"Sorting class 1 was added",
		null,
		function () { return $('#example tbody tr:eq(1) td:eq(4)').hasClass('sorting_1'); }
	);
	
	
	/* Check can disable */
	oTest.fnWaitTest( 
		"Sorting classes can be disabled",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"bSortClasses": false
			} );
		},
		function () { return $('#example tbody tr:eq(0) td:eq(0)').hasClass('sorting_1') == false; }
	);
	
	oTest.fnWaitTest( 
		"Sorting classes disabled - add column 1 - no effect",
		function () { 
			oDispacher.click( $('#example thead th:eq(1)')[0], { 'shift': true } ); },
		function () {
			return $('#example tbody tr:eq(0) td:eq(0)').hasClass('sorting_1') == false &&
						 $('#example tbody tr:eq(0) td:eq(1)').hasClass('sorting_2') == false;
		}
	);
	
	oTest.fnWaitTest( 
		"Sorting classes disabled - add column 2 - no effect",
		function () { 
			oDispacher.click( $('#example thead th:eq(2)')[0], { 'shift': true } ); },
		function () {
			return $('#example tbody tr:eq(0) td:eq(0)').hasClass('sorting_1') == false &&
						 $('#example tbody tr:eq(0) td:eq(1)').hasClass('sorting_2') == false &&
						 $('#example tbody tr:eq(0) td:eq(2)').hasClass('sorting_3') == false;
		}
	);
	
	
	/* Enable makes no difference */
	oTest.fnWaitTest( 
		"Sorting classes enabled override",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"bSortClasses": true
			} );
		},
		function () { return $('#example tbody tr:eq(0) td:eq(0)').hasClass('sorting_1'); }
	);
	
	
	oTest.fnComplete();
} );