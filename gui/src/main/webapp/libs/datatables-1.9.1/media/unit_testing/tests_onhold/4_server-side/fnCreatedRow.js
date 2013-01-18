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
oTest.fnStart( "fnCreatedRow tests" );

$(document).ready( function () {
	var tmp = 0;
	var complete = false;

	$('#example').dataTable( {
		"bServerSide": true,
		"sAjaxSource": "../../../examples/server_side/scripts/server_processing.php",
		fnCreatedRow: function () {
			tmp++;
		}
	} );
	
	oTest.fnWaitTest( 
		"Row created is called once for each row on init",
		null,
		function () { return tmp===10; }
	);
	
	oTest.fnWaitTest( 
		"Created is called back on other draws",
		function () { $('#example th:eq(1)').click(); },
		function () { return tmp===20; }
	);

	oTest.fnWaitTest(
		"Three arguments for the function",
		function () { 
			oSession.fnRestore();
			tmp = true;
			complete = false;

			$('#example').dataTable( {
				"bServerSide": true,
				"sAjaxSource": "../../../examples/server_side/scripts/server_processing.php",
				fnCreatedRow: function () {
					if ( arguments.length !== 3 ) {
						tmp = false;
					}
				},
				fnInitComplete: function () {
					complete = true;
				}
			} );
		},
		function () { return (tmp && complete); }
	);

	oTest.fnWaitTest(
		"First argument is a TR element",
		function () { 
			oSession.fnRestore();
			tmp = true;
			complete = false;

			$('#example').dataTable( {
				"bServerSide": true,
				"sAjaxSource": "../../../examples/server_side/scripts/server_processing.php",
				fnCreatedRow: function () {
					if ( arguments[0].nodeName !== "TR" ) {
						tmp = false;
					}
				},
				fnInitComplete: function () {
					complete = true;
				}
			} );
		},
		function () { return (tmp && complete); }
	);

	oTest.fnWaitTest(
		"Second argument is an array with 5 elements",
		function () { 
			oSession.fnRestore();
			tmp = true;
			complete = false;

			$('#example').dataTable( {
				"bServerSide": true,
				"sAjaxSource": "../../../examples/server_side/scripts/server_processing.php",
				fnCreatedRow: function () {
					if ( arguments[1].length !== 5 ) {
						tmp = false;
					}
				},
				fnInitComplete: function () {
					complete = true;
				}
			} );
		},
		function () { return (tmp && complete); }
	);

	oTest.fnWaitTest(
		"Third argument is the data source for the row",
		function () { 
			oSession.fnRestore();
			tmp = true;
			complete = false;

			$('#example').dataTable( {
				"bServerSide": true,
				"sAjaxSource": "../../../examples/server_side/scripts/server_processing.php",
				fnCreatedRow: function () {
					if ( arguments[1] !== this.fnSettings().aoData[ arguments[2] ]._aData ) {
						tmp = false;
					}
				},
				fnInitComplete: function () {
					complete = true;
				}
			} );
		},
		function () { return (tmp && complete); }
	);

	oTest.fnWaitTest(
		"TR element is tied to the correct data",
		function () { 
			oSession.fnRestore();
			tmp = false;
			complete = false;

			$('#example').dataTable( {
				"bServerSide": true,
				"sAjaxSource": "../../../examples/server_side/scripts/server_processing.php",
				fnCreatedRow: function (tr, data, index) {
					if ( data[1] === "Firefox 1.0" ) {
						if ( $('td:eq(3)', tr).html() == "1.7" ) {
							tmp = true;
						}
					}
				},
				fnInitComplete: function () {
					complete = true;
				}
			} );
		},
		function () { return (tmp && complete); }
	);
	
	
	
	oTest.fnComplete();
} );