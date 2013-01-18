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
// DATA_TEMPLATE: 6776
oTest.fnStart( "Actions on a scrolling table keep width" );


$(document).ready( function () {
	var oTable = $('#example').dataTable( {
        "bFilter": true,
        "bSort": true,
        "sScrollY": "100px",
        "bPaginate": false
	} );
	
	var iWidth = $('div.dataTables_wrapper').width();

	oTest.fnTest( 
		"First sort has no effect on width",
		function () { $('th:eq(1)').click(); },
		function () { return $('div.dataTables_wrapper').width() == iWidth; }
	);

	oTest.fnTest( 
		"Second sort has no effect on width",
		function () { $('th:eq(1)').click(); },
		function () { return $('div.dataTables_wrapper').width() == iWidth; }
	);

	oTest.fnTest( 
		"Third sort has no effect on width",
		function () { $('th:eq(2)').click(); },
		function () { return $('div.dataTables_wrapper').width() == iWidth; }
	);

	oTest.fnTest( 
		"Filter has no effect on width",
		function () { oTable.fnFilter('i'); },
		function () { return $('div.dataTables_wrapper').width() == iWidth; }
	);

	oTest.fnTest( 
		"Filter 2 has no effect on width",
		function () { oTable.fnFilter('in'); },
		function () { return $('div.dataTables_wrapper').width() == iWidth; }
	);

	oTest.fnTest( 
		"No result filter has header and body at same width",
		function () { oTable.fnFilter('xxx'); },
		function () { return $('#example').width() == $('div.dataTables_scrollHeadInner').width(); }
	);

	oTest.fnTest( 
		"Filter with no results has no effect on width",
		function () { oTable.fnFilter('xxx'); },
		function () { return $('div.dataTables_wrapper').width() == iWidth; }
	);

	oTest.fnTest( 
		"Filter with no results has table equal to wrapper width",
		function () { oTable.fnFilter('xxx'); },
		function () { return $('div.dataTables_wrapper').width() == $('#example').width(); }
	);
	
	oTest.fnComplete();
} );