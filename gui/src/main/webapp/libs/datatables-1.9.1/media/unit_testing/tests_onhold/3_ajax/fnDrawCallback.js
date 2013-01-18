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
oTest.fnStart( "fnDrawCallback" );

/* Fairly boring function compared to the others! */

$(document).ready( function () {
	/* Check the default */
	var oTable = $('#example').dataTable( {
		"sAjaxSource": "../../../examples/ajax/sources/arrays.txt"
	} );
	var oSettings = oTable.fnSettings();
	var mPass, bInit;
	
	oTest.fnWaitTest( 
		"Default should be null",
		null,
		function () { return oSettings.fnDrawCallback == null; }
	);
	
	
	oTest.fnWaitTest( 
		"One argument passed",
		function () {
			oSession.fnRestore();
			
			mPass = -1;
			bInit = false;
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"fnDrawCallback": function ( ) {
					mPass = arguments.length;
				},
				"fnInitComplete": function () {
					bInit = true;
				}
			} );
		},
		function () { return mPass == 1 && bInit; }
	);
	
	
	oTest.fnWaitTest( 
		"That one argument is the settings object",
		function () {
			oSession.fnRestore();
			
			bInit = false;
			oTable = $('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"fnDrawCallback": function ( oSettings ) {
					mPass = oSettings;
				},
				"fnInitComplete": function () {
					bInit = true;
				}
			} );
		},
		function () { return oTable.fnSettings() == mPass && bInit; }
	);
	
	
	/* The draw callback is called once for the init and then when the data is added */
	oTest.fnWaitTest( 
		"fnRowCallback called once on first draw",
		function () {
			oSession.fnRestore();
			
			mPass = 0;
			bInit = false;
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"fnDrawCallback": function ( ) {
					mPass++;
				},
				"fnInitComplete": function () {
					bInit = true;
				}
			} );
		},
		function () { return mPass == 2 && bInit; }
	);
	
	oTest.fnWaitTest( 
		"fnRowCallback called once on each draw there after as well",
		function () {
			$('#example_next').click();
			$('#example_next').click();
			$('#example_next').click();
		},
		function () { return mPass == 5; }
	);
	
	
	
	
	
	oTest.fnComplete();
} );