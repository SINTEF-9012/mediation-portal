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
oTest.fnStart( "fnRowCallback" );

/* Note - fnRowCallback MUST return the first arguments (modified or not) */

$(document).ready( function () {
	/* Check the default */
	var oTable = $('#example').dataTable( {
		"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
		"bDeferRender": true
	} );
	var oSettings = oTable.fnSettings();
	var mPass;
	
	oTest.fnWaitTest( 
		"Default should be null",
		null,
		function () { return oSettings.fnRowCallback == null; }
	);
	
	
	oTest.fnWaitTest( 
		"Four arguments passed",
		function () {
			oSession.fnRestore();
			
			mPass = -1;
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"bDeferRender": true,
				"fnRowCallback": function ( nTr ) {
					mPass = arguments.length;
					return nTr;
				}
			} );
		},
		function () { return mPass == 4; }
	);
	
	
	oTest.fnWaitTest( 
		"fnRowCallback called once for each drawn row",
		function () {
			oSession.fnRestore();
			
			mPass = 0;
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"bDeferRender": true,
				"fnRowCallback": function ( nTr, asData, iDrawIndex, iDataIndex ) {
					mPass++;
					return nTr;
				}
			} );
		},
		function () { return mPass == 10; }
	);
	
	oTest.fnWaitTest( 
		"fnRowCallback allows us to alter row information",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"bDeferRender": true,
				"fnRowCallback": function ( nTr, asData, iDrawIndex, iDataIndex ) {
					$(nTr).addClass('unit_test');
					return nTr;
				}
			} );
		},
		function () { return $('#example tbody tr:eq(1)').hasClass('unit_test'); }
	);
	
	oTest.fnWaitTest( 
		"Data array has length matching columns",
		function () {
			oSession.fnRestore();
			
			mPass = true;
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"bDeferRender": true,
				"fnRowCallback": function ( nTr, asData, iDrawIndex, iDataIndex ) {
					if ( asData.length != 5 )
						mPass = false;
					return nTr;
				}
			} );
		},
		function () { return mPass; }
	);
	
	oTest.fnWaitTest( 
		"Data array has length matching columns",
		function () {
			oSession.fnRestore();
			
			mPass = true;
			var iCount = 0;
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"bDeferRender": true,
				"fnRowCallback": function ( nTr, asData, iDrawIndex, iDataIndex ) {
					if ( iCount != iDrawIndex )
						mPass = false;
					iCount++;
					return nTr;
				}
			} );
		},
		function () { return mPass; }
	);
	
	
	
	oTest.fnComplete();
} );