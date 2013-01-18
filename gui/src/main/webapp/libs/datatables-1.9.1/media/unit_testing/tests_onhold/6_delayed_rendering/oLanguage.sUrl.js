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
oTest.fnStart( "oLanguage.sUrl" );

/* Note that we only test the internal storage of language information pulled form a file here
 * as the other language tests will check it goes into the DOM correctly
 */

$(document).ready( function () {
	/* Check the default */
	var oTable = $('#example').dataTable( {
		"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
		"bDeferRender": true
	} );
	var oSettings = oTable.fnSettings();
	
	oTest.fnTest( 
		"sUrl is blank by default",
		null,
		function () { return oSettings.oLanguage.sUrl == ""; }
	);
	
	
	oTest.fnWaitTest( 
		"Loading of German file loads language information",
		function () {
			oSession.fnRestore();
			oTable = $('#example').dataTable( {
				"sAjaxSource": "../../../examples/ajax/sources/arrays.txt",
				"bDeferRender": true,
				"oLanguage": {
					"sUrl": "../../../examples/examples_support/de_DE.txt"
				}
			} );
			oSettings = oTable.fnSettings();
		},
		function () {
			var bReturn = 
				oSettings.oLanguage.sProcessing == "Bitte warten..." &&
				oSettings.oLanguage.sLengthMenu == "_MENU_ Einträge anzeigen" &&
				oSettings.oLanguage.sZeroRecords == "Keine Einträge vorhanden." &&
				oSettings.oLanguage.sInfo == "_START_ bis _END_ von _TOTAL_ Einträgen" &&
				oSettings.oLanguage.sInfoEmpty == "0 bis 0 von 0 Einträgen" &&
				oSettings.oLanguage.sInfoFiltered == "(gefiltert von _MAX_  Einträgen)" &&
				oSettings.oLanguage.sInfoPostFix == "" &&
				oSettings.oLanguage.sSearch == "Suchen" &&
				oSettings.oLanguage.oPaginate.sFirst == "Erster" &&
				oSettings.oLanguage.oPaginate.sPrevious == "Zurück" &&
				oSettings.oLanguage.oPaginate.sNext == "Nächster" &&
				oSettings.oLanguage.oPaginate.sLast == "Letzter";
				
			return bReturn;
		}
	);
	
	/* One DOM check just to ensure that they go into the DOM */
	oTest.fnTest(
		"Loaded language goes into the DOM",
		null,
		function () { return document.getElementById('example_info').innerHTML = "1 bis 10 von 57 Einträgen"; }
	);
	
	
	oTest.fnComplete();
} );