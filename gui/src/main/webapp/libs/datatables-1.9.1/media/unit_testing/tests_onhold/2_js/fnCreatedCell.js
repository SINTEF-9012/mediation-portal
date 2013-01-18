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
// DATA_TEMPLATE: js_data
oTest.fnStart( "fnCreatedCell tests" );

$(document).ready( function () {
	var tmp = 0;

	$('#example').dataTable( {
		"aaData": gaaData,
		"aoColumnDefs": [ {
			fnCreatedCell: function () {
				tmp++;
			},
			"aTargets": ["_all"]
		} ]
	} );
	
	oTest.fnTest( 
		"Cell created is called once for each cell on init",
		null,
		function () { return tmp===285; }
	);
	
	oTest.fnTest( 
		"Created isn't called back on other draws",
		function () { $('#example th:eq(1)').click(); },
		function () { return tmp===285; }
	);

	oTest.fnTest(
		"Four arguments for the function",
		function () { 
			oSession.fnRestore();
			tmp = true;

			$('#example').dataTable( {
				"aaData": gaaData,
				"aoColumnDefs": [ {
					fnCreatedRow: function () {
						if ( arguments.length !== 4 ) {
							tmp = false;
						}
					},
					"aTargets": ["_all"]
				} ]
			} );
		},
		function () { return tmp; }
	);

	oTest.fnTest(
		"First argument is a TD element",
		function () { 
			oSession.fnRestore();
			tmp = true;

			$('#example').dataTable( {
				"aaData": gaaData,
				"aoColumnDefs": [ {
					fnCreatedRow: function () {
						if ( arguments[0].nodeName !== "TD" ) {
							tmp = false;
						}
					},
					"aTargets": ["_all"]
				} ]
			} );
		},
		function () { return tmp; }
	);

	oTest.fnTest(
		"Second argument is the HTML value",
		function () { 
			oSession.fnRestore();
			tmp = true;

			$('#example').dataTable( {
				"aaData": gaaData,
				"aoColumnDefs": [ {
					fnCreatedRow: function () {
						if ( arguments[1] != $('td').html() ) {
							tmp = false;
						}
					},
					"aTargets": ["_all"]
				} ]
			} );
		},
		function () { return tmp; }
	);

	oTest.fnTest(
		"Third argument is the data array",
		function () { 
			oSession.fnRestore();
			tmp = true;

			$('#example').dataTable( {
				"aaData": gaaData,
				"aoColumnDefs": [ {
					fnCreatedRow: function () {
						if ( arguments[2].length !== 5 ) {
							tmp = false;
						}
					},
					"aTargets": ["_all"]
				} ]
			} );
		},
		function () { return tmp; }
	);

	oTest.fnTest(
		"Fourth argument is the data source for the row",
		function () { 
			oSession.fnRestore();
			tmp = true;

			$('#example').dataTable( {
				"aaData": gaaData,
				"aoColumnDefs": [ {
					fnCreatedRow: function () {
						if ( arguments[2] !== this.fnSettings().aoData[ arguments[2] ]._aData ) {
							tmp = false;
						}
					},
					"aTargets": ["_all"]
				} ]
			} );
		},
		function () { return tmp; }
	);

	oTest.fnTest(
		"Fifth argument is the the col index",
		function () { 
			oSession.fnRestore();
			tmp = true;

			$('#example').dataTable( {
				"aaData": gaaData,
				"aoColumnDefs": [ {
					fnCreatedRow: function () {
						if ( arguments[1] != $('td:eq('+arguments[4]+')', arguments[0].parentNode).html() ) {
							tmp = false;
						}
					},
					"aTargets": ["_all"]
				} ]
			} );
		},
		function () { return tmp; }
	);
	
	
	
	oTest.fnComplete();
} );