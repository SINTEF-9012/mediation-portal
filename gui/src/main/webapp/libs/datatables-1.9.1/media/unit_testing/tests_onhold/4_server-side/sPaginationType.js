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
oTest.fnStart( "sPaginationType" );

$(document).ready( function () {
	/* Check the default */
	var oTable = $('#example').dataTable( {
		"bServerSide": true,
		"sAjaxSource": "../../../examples/server_side/scripts/server_processing.php"
	} );
	var oSettings = oTable.fnSettings();
	
	oTest.fnWaitTest( 
		"Check two button paging is the default",
		null,
		function () { return oSettings.sPaginationType == "two_button"; }
	);
	
	oTest.fnWaitTest( 
		"Check class is applied",
		null,
		function () { return $('#example_paginate').hasClass('paging_two_button'); }
	);
	
	oTest.fnWaitTest( 
		"Two A elements are in the wrapper",
		null,
		function () { return $('#example_paginate a').length == 2; }
	);
	
	oTest.fnWaitTest( 
		"We have the previous button",
		null,
		function () { return document.getElementById('example_previous'); }
	);
	
	oTest.fnWaitTest( 
		"We have the next button",
		null,
		function () { return document.getElementById('example_next'); }
	);
	
	oTest.fnWaitTest( 
		"Previous button is disabled",
		null,
		function () { return $('#example_previous').hasClass('paginate_disabled_previous'); }
	);
	
	oTest.fnWaitTest( 
		"Next button is enabled",
		null,
		function () { return $('#example_next').hasClass('paginate_enabled_next'); }
	);
	
	/* Don't test paging - that's done by the zero config test script. */
	
	
	/* Two buttons paging */
	var bComplete = false;
	oTest.fnWaitTest( 
		"Can enabled full numbers paging",
		function () {
			oSession.fnRestore();
			oTable = $('#example').dataTable( {
				"bServerSide": true,
		"sAjaxSource": "../../../examples/server_side/scripts/server_processing.php",
				"sPaginationType": "full_numbers",
				"fnInitComplete": function () {
					setTimeout( function () {
						bComplete = true;
					}, 500 );
				}
			} );
			oSettings = oTable.fnSettings();
		},
		function () {
			if ( bComplete )
				return oSettings.sPaginationType == "full_numbers";
			else
				return false;
		}
	);
	
	oTest.fnWaitTest( 
		"Check full numbers class is applied",
		null,
		function () { return $('#example_paginate').hasClass('paging_full_numbers'); }
	);
	
	
	var nFirst, nPrevious, nNext, nLast;
	oTest.fnWaitTest( 
		"Jump to last page",
		function () {
			nFirst = $('div.dataTables_paginate a.first');
			nPrevious = $('div.dataTables_paginate a.previous');
			nNext = $('div.dataTables_paginate a.next');
			nLast = $('div.dataTables_paginate a.last');
			nLast.click();
		},
		function () {
			return document.getElementById('example_info').innerHTML == "Showing 51 to 57 of 57 entries";
		}
	);
	
	oTest.fnWaitTest( 
		"Go to two pages previous",
		function () {
			nPrevious.click();
			nPrevious.click();
		},
		function () {
			return document.getElementById('example_info').innerHTML == "Showing 31 to 40 of 57 entries";
		}
	);
	
	oTest.fnWaitTest( 
		"Next (second last) page",
		function () {
			nNext.click();
		},
		function () {
			return document.getElementById('example_info').innerHTML == "Showing 41 to 50 of 57 entries";
		}
	);
	
	oTest.fnWaitTest( 
		"Jump to first page",
		function () {
			nFirst.click();
		},
		function () {
			return document.getElementById('example_info').innerHTML == "Showing 1 to 10 of 57 entries";
		}
	);
	
	
	oTest.fnComplete();
} );