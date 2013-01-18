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
oTest.fnStart( "fnInfoCallback checks" );

$(document).ready( function () {
	var mPass;
	
	$('#example').dataTable();
	
	/* Basic checks */
	oTest.fnTest( 
		"null by default",
		null,
		function () { return $('#example').dataTable().fnSettings().oLanguage.fnInfoCallback == null; }
	);
	
	oTest.fnTest( 
		"Agrument length",
		function () {
			$('#example').dataTable( {
				"bDestroy": true,
				"fnInfoCallback": function( oS, iStart, iEnd, iMax, iTotal, sPre ) {
					mPass = arguments.length;
					return sPre;
				}
			} );
		},
		function () { return mPass == 6; }
	);
	
	oTest.fnTest( 
		"Settings first",
		function () {
			$('#example').dataTable( {
				"bDestroy": true,
				"fnInfoCallback": function( oS, iStart, iEnd, iMax, iTotal, sPre ) {
					mPass = (oS == $('#example').dataTable().fnSettings()) ? true : false;
					return sPre;
				}
			} );
		},
		function () { return mPass; }
	);
	
	oTest.fnTest( 
		"Start arg",
		function () {
			$('#example').dataTable( {
				"bDestroy": true,
				"fnInfoCallback": function( oS, iStart, iEnd, iMax, iTotal, sPre ) {
					return iStart;
				}
			} );
		},
		function () { return $('#example_info').html() == "1"; }
	);
	
	oTest.fnTest( 
		"End arg",
		function () {
			$('#example').dataTable( {
				"bDestroy": true,
				"fnInfoCallback": function( oS, iStart, iEnd, iMax, iTotal, sPre ) {
					return iEnd;
				}
			} );
		},
		function () { return $('#example_info').html() == "10"; }
	);
	
	oTest.fnTest( 
		"Max arg",
		function () {
			$('#example').dataTable( {
				"bDestroy": true,
				"fnInfoCallback": function( oS, iStart, iEnd, iMax, iTotal, sPre ) {
					return iMax;
				}
			} );
		},
		function () { return $('#example_info').html() == "57"; }
	);
	
	oTest.fnTest( 
		"Max arg - filter",
		function () {
			$('#example').dataTable().fnFilter("1.0");
		},
		function () { return $('#example_info').html() == "57"; }
	);
	
	oTest.fnTest( 
		"Total arg",
		function () {
			$('#example').dataTable( {
				"bDestroy": true,
				"fnInfoCallback": function( oS, iStart, iEnd, iMax, iTotal, sPre ) {
					return iTotal;
				}
			} );
		},
		function () { return $('#example_info').html() == "57"; }
	);
	
	oTest.fnTest( 
		"Total arg - filter",
		function () {
			$('#example').dataTable().fnFilter("1.0");
		},
		function () { return $('#example_info').html() == "3"; }
	);
	
	
	
	oTest.fnComplete();
} );