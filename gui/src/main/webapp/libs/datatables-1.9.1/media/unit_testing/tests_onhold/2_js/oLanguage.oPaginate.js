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
oTest.fnStart( "oLanguage.oPaginate" );

/* Note that the paging language information only has relevence in full numbers */

$(document).ready( function () {
	/* Check the default */
	var oTable = $('#example').dataTable( {
		"aaData": gaaData,
		"sPaginationType": "full_numbers"
	} );
	var oSettings = oTable.fnSettings();
	
	oTest.fnTest( 
		"oLanguage.oPaginate defaults",
		null,
		function () {
			var bReturn = 
				oSettings.oLanguage.oPaginate.sFirst == "First" &&
				oSettings.oLanguage.oPaginate.sPrevious == "Previous" &&
				oSettings.oLanguage.oPaginate.sNext == "Next" &&
				oSettings.oLanguage.oPaginate.sLast == "Last";
			return bReturn;
		}
	);
	
	oTest.fnTest( 
		"oLanguage.oPaginate defaults are in the DOM",
		null,
		function () {
			var bReturn = 
				$('#example_paginate .first').html() == "First" &&
				$('#example_paginate .previous').html() == "Previous" &&
				$('#example_paginate .next').html() == "Next" &&
				$('#example_paginate .last').html() == "Last";
			return bReturn;
		}
	);
	
	
	oTest.fnTest( 
		"oLanguage.oPaginate can be defined",
		function () {
			oSession.fnRestore();
			oTable = $('#example').dataTable( {
				"aaData": gaaData,
				"sPaginationType": "full_numbers",
				"oLanguage": {
					"oPaginate": {
						"sFirst":    "unit1",
						"sPrevious": "test2",
						"sNext":     "unit3",
						"sLast":     "test4"
					}
				}
			} );
			oSettings = oTable.fnSettings();
		},
		function () {
			var bReturn = 
				oSettings.oLanguage.oPaginate.sFirst == "unit1" &&
				oSettings.oLanguage.oPaginate.sPrevious == "test2" &&
				oSettings.oLanguage.oPaginate.sNext == "unit3" &&
				oSettings.oLanguage.oPaginate.sLast == "test4";
			return bReturn;
		}
	);
	
	oTest.fnTest( 
		"oLanguage.oPaginate definitions are in the DOM",
		null,
		function () {
			var bReturn = 
				$('#example_paginate .first').html() == "unit1" &&
				$('#example_paginate .previous').html() == "test2" &&
				$('#example_paginate .next').html() == "unit3" &&
				$('#example_paginate .last').html() == "test4";
			return bReturn;
		}
	);
	
	
	oTest.fnComplete();
} );