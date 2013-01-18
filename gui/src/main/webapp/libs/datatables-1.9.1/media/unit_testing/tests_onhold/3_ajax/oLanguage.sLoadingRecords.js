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
oTest.fnStart( "oLanguage.sLoadingRecords" );

$(document).ready( function () {
	var tmp = false;
	oTest.fnTest( 
		"Default loading text is 'Loading...'",
		function () {
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt"
			} );
			tmp = $('#example tbody tr td')[0].innerHTML == "Loading...";
		},
		function () { return tmp; }
	);
	
	oTest.fnTest(
		"Text can be overriden",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"oLanguage": {
					"sLoadingRecords": "unitest"
				},
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt"
			} );
			tmp = $('#example tbody tr td')[0].innerHTML == "unitest";
		},
		function () { return tmp; }
	);
	
	oTest.fnTest(
		"When sZeroRecords is given but sLoadingRecords is not, sZeroRecords is used",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"oLanguage": {
					"sZeroRecords": "unitest_sZeroRecords"
				},
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt"
			} );
			tmp = $('#example tbody tr td')[0].innerHTML == "unitest_sZeroRecords";
		},
		function () { return tmp; }
	);
	
	oTest.fnTest(
		"sLoadingRecords and sZeroRecords both given",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"oLanguage": {
					"sZeroRecords": "unitest_sZeroRecords2",
					"sLoadingRecords": "unitest2"
				},
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt"
			} );
			tmp = $('#example tbody tr td')[0].innerHTML == "unitest2";
		},
		function () { return tmp; }
	);
	
	
	oTest.fnComplete();
} );