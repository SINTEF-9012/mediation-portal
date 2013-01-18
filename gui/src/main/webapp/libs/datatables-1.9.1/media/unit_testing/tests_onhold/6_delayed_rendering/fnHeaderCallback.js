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
oTest.fnStart( "fnHeaderCallback" );

$(document).ready( function () {
	/* Check the default */
	var oTable = $('#example').dataTable( {
		"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
		"bDeferRender": true
	} );
	var oSettings = oTable.fnSettings();
	var mPass, bInit;
	
	oTest.fnWaitTest( 
		"Default should be null",
		null,
		function () { return oSettings.fnHeaderCallback == null; }
	);
	
	
	oTest.fnWaitTest( 
		"Five arguments passed",
		function () {
			oSession.fnRestore();
			
			mPass = -1;
			bInit = false;
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"bDeferRender": true,
				"fnHeaderCallback": function ( ) {
					mPass = arguments.length;
				},
				"fnInitComplete": function () {
					bInit = true;
				}
			} );
		},
		function () { return mPass == 5 && bInit; }
	);
	
	
	/* The header callback is called once for the init and then when the data is added */
	oTest.fnWaitTest( 
		"fnHeaderCallback called once per draw",
		function () {
			oSession.fnRestore();
			
			mPass = 0;
			bInit = false;
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"bDeferRender": true,
				"fnHeaderCallback": function ( nHead, aasData, iStart, iEnd, aiDisplay ) {
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
		"fnRowCallback called on paging (i.e. another draw)",
		function () { $('#example_next').click(); },
		function () { return mPass == 3; }
	);
	
	
	oTest.fnWaitTest( 
		"fnRowCallback allows us to alter row information",
		function () {
			oSession.fnRestore();
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"bDeferRender": true,
				"fnHeaderCallback": function ( nHead, aasData, iStart, iEnd, aiDisplay ) {
					nHead.getElementsByTagName('th')[0].innerHTML = "Displaying "+(iEnd-iStart)+" records";
				}
			} );
		},
		function () { return $('#example thead th:eq(0)').html() == "Displaying 10 records"; }
	);
	
	
	oTest.fnWaitTest( 
		"iStart correct on first page",
		function () {
			oSession.fnRestore();
			
			mPass = true;
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"bDeferRender": true,
				"fnHeaderCallback": function ( nHead, aasData, iStart, iEnd, aiDisplay ) {
					if ( iStart != 0 )
					{
						mPass = false;
					}
				}
			} );
		},
		function () { return mPass; }
	);
	
	
	oTest.fnWaitTest( 
		"iStart correct on second page",
		function () {
			oSession.fnRestore();
			
			mPass = false;
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"bDeferRender": true,
				"fnHeaderCallback": function ( nHead, aasData, iStart, iEnd, aiDisplay ) {
					if ( iStart == 10 )
					{
						mPass = true;
					}
				},
				"fnInitComplete": function () {
					$('#example_next').click();
				}
			} );
		},
		function () { return mPass; }
	);
	
	
	oTest.fnWaitTest( 
		"iEnd correct on second page",
		function () {
			oSession.fnRestore();
			
			mPass = false;
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"bDeferRender": true,
				"fnHeaderCallback": function ( nHead, aasData, iStart, iEnd, aiDisplay ) {
					if ( iEnd == 20 )
					{
						mPass = true;
					}
				},
				"fnInitComplete": function () {
					$('#example_next').click();
				}
			} );
		},
		function () { return mPass; }
	);
	
	
	oTest.fnWaitTest( 
		"aiDisplay length is full data when not filtered",
		function () {
			oSession.fnRestore();
			
			mPass = false;
			$('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"bDeferRender": true,
				"fnHeaderCallback": function ( nHead, aasData, iStart, iEnd, aiDisplay ) {
					if ( aiDisplay.length == 57 )
					{
						mPass = true;
					}
				}
			} );
		},
		function () { return mPass; }
	);
	
	oTest.fnWaitTest( 
		"aiDisplay length is 9 when filtering on 'Mozilla'",
		function () {
			oSession.fnRestore();
			
			mPass = false;
			oTable = $('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"bDeferRender": true,
				"fnHeaderCallback": function ( nHead, aasData, iStart, iEnd, aiDisplay ) {
					if ( aiDisplay.length == 9 )
					{
						mPass = true;
					}
				}
			} );
			oTable.fnFilter( "Mozilla" );
		},
		function () { return mPass; }
	);
	
	
	
	oTest.fnComplete();
} );