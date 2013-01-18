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
oTest.fnStart( "Destroy with hidden columns" );

$(document).ready( function () {
	var mTest;
	
	
	$('#example').dataTable( {
		"bServerSide": true,
		"sAjaxSource": "../../../examples/server_side/scripts/server_processing.php",
		"aoColumnDefs": [ 
			{ "bSearchable": false, "bVisible": false, "aTargets": [ 2 ] },
			{ "bVisible": false, "aTargets": [ 3 ] }
		],
		"fnInitComplete": function () {
			this.fnDestroy();
		}
	} );
	
	oTest.fnWaitTest( 
		"Check that the number of columns in table is correct",
		null,
		function () { return $('#example tbody tr:eq(0) td').length == 5; }
	);
	
	
	oTest.fnTest( 
		"And with scrolling",
		function () {
			$('#example').dataTable( {
				"bServerSide": true,
				"sAjaxSource": "../../../examples/server_side/scripts/server_processing.php",
				"sScrollY": 200,
				"aoColumnDefs": [ 
					{ "bSearchable": false, "bVisible": false, "aTargets": [ 2 ] },
					{ "bVisible": false, "aTargets": [ 3 ] }
				],
				"fnInitComplete": function () {
					this.fnDestroy();
				}
			} );
		},
		function () { return $('#example tbody tr:eq(0) td').length == 5; }
	);
	
	oTest.fnComplete();
} );