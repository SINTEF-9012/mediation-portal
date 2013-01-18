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
oTest.fnStart( "sScrollX / Y" );


$(document).ready( function () {
	// Force some x scrolling
	$('body').css('white-space', 'nowrap');
	$('#container').css('width', '400px');

	var oTable = $('#example').dataTable( {
		"sScrollX": "100%",
		"sScrollY": "200px",
		"bPaginate": false
	} );
	
	oTest.fnWaitTest( 
		"Header follows x-scrolling",
		function () { $('div.dataTables_scrollBody').scrollLeft(20); },
		function () { return $('div.dataTables_scrollHead').scrollLeft() == 20; }
	);
	
	oTest.fnWaitTest( 
		"Footer follows x-scrolling",
		null,
		function () { return $('div.dataTables_scrollFoot').scrollLeft() == 20; }
	);
	
	oTest.fnWaitTest( 
		"y-scrolling has no effect on header",
		function () { $('div.dataTables_scrollBody').scrollTop(20); },
		function () { return $('div.dataTables_scrollHead').scrollLeft() == 20; }
	);
	
	oTest.fnWaitTest( 
		"Filtering results in sets y-scroll back to 0",
		function () { oTable.fnFilter('1') },
		function () { return $('div.dataTables_scrollBody').scrollTop() == 0; }
	);
	
	oTest.fnWaitTest( 
		"Filtering has no effect on x-scroll",
		null,
		function () { return $('div.dataTables_scrollBody').scrollLeft() == 20; }
	);
	
	oTest.fnWaitTest( 
		"Full x-scroll has header track all the way with it",
		function () {
			$('div.dataTables_scrollBody').scrollLeft(
				$('#example').width() - $('div.dataTables_scrollBody')[0].clientWidth
			);
		},
		function () { return $('div.dataTables_scrollBody').scrollLeft() == $('div.dataTables_scrollHead').scrollLeft(); }
	);
	
	oTest.fnTest( 
		"Footer also tracked all the way",
		null,
		function () { return $('div.dataTables_scrollBody').scrollLeft() == $('div.dataTables_scrollFoot').scrollLeft(); }
	);
	
	oTest.fnComplete();
} );