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
oTest.fnStart( "fnServerData for Ajax sourced data" );

$(document).ready( function () {
	var mPass;
	
	oTest.fnTest( 
		"Argument length",
		function () {
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"fnServerData": function () {
					mPass = arguments.length;
				}
			} );
		},
		function () { return mPass == 4; }
	);
	
	oTest.fnTest( 
		"Url",
		function () {
			$('#example').dataTable( {
				"bDestroy": true,
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"fnServerData": function (sUrl, aoData, fnCallback, oSettings) {
					mPass = sUrl == "../../../examples/ajax/sources/arrays.txt";
				}
			} );
		},
		function () { return mPass; }
	);
	
	oTest.fnTest( 
		"Data array",
		function () {
			$('#example').dataTable( {
				"bDestroy": true,
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"fnServerData": function (sUrl, aoData, fnCallback, oSettings) {
					mPass = aoData.length==0;
				}
			} );
		},
		function () { return mPass; }
	);
	
	oTest.fnTest( 
		"Callback function",
		function () {
			$('#example').dataTable( {
				"bDestroy": true,
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"fnServerData": function (sUrl, aoData, fnCallback, oSettings) {
					mPass = typeof fnCallback == 'function';
				}
			} );
		},
		function () { return mPass; }
	);
	
	
	oTest.fnComplete();
} );