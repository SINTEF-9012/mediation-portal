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
oTest.fnStart( "aoColumns.sWidth" );

/* NOTE - we need to disable the auto width for the majority of these test in order to preform 
 * these tests as the auto width will convert the width to a px value. We can do 'non-exact' tests
 * with auto width enabled however to ensure it scales columns as required
 */

$(document).ready( function () {
	/* Check the default */
	var oTable = $('#example').dataTable( {
		"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
		"bDeferRender": true,
		"bAutoWidth": false,
		"aoColumns": [
			null,
			{ "sWidth": '40%' },
			null,
			null,
			null
		]
	} );
	var oSettings = oTable.fnSettings();
	
	oTest.fnWaitTest( 
		"With auto width disabled the width for one column is appled",
		null,
		function () { return $('#example thead th:eq(1)')[0].style.width == "40%"; }
	);
	
	oTest.fnWaitTest( 
		"With auto width disabled the width for one column is appled",
		function () {
			oSession.fnRestore();
			oTable = $('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"bDeferRender": true,
				"bAutoWidth": false,
				"aoColumns": [
					null,
					null,
					{ "sWidth": '20%' },
					{ "sWidth": '30%' },
					null
				]
			} );
		},
		function () {
			var bReturn =
				$('#example thead th:eq(2)')[0].style.width == "20%" &&
				$('#example thead th:eq(3)')[0].style.width == "30%";
			return bReturn;
		}
	);
	
	
	oTest.fnWaitTest( 
		"With auto width, it will make the smallest column the largest with percentage width given",
		function () {
			oSession.fnRestore();
			oTable = $('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"bDeferRender": true,
				"aoColumns": [
					null,
					null,
					null,
					{ "sWidth": '40%' },
					null
				]
			} );
		},
		function () {
			var anThs = $('#example thead th');
			var a0 = anThs[0].offsetWidth;
			var a1 = anThs[1].offsetWidth;
			var a2 = anThs[2].offsetWidth;
			var a3 = anThs[3].offsetWidth;
			var a4 = anThs[4].offsetWidth;
			
			if ( a3>a0 && a3>a1 && a3>a2 && a3>a4 )
				return true;
			else
				return false;
		}
	);
	
	
	oTest.fnComplete();
} );