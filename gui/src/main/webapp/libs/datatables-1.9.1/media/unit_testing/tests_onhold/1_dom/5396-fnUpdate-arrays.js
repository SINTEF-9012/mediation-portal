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
oTest.fnStart( "5396 - fnUpdate with 2D arrays for a single row" );

$(document).ready( function () {
	$('#example thead tr').append( '<th>6</th>' );
	$('#example thead tr').append( '<th>7</th>' );
	$('#example thead tr').append( '<th>8</th>' );
	$('#example thead tr').append( '<th>9</th>' );
	$('#example thead tr').append( '<th>10</th>' );
	
	var aDataSet = [
    [
        "1",
        "í™?ê¸¸ë?™",
        "1154315",
        "etc1",
        [
            [ "test1@daum.net", "2011-03-04" ],
            [ "test1@naver.com", "2009-07-06" ],
            [ "test4@naver.com", ",hide" ],
            [ "test5?@naver.com", "" ]
        ],
        "2011-03-04",
        "show"
    ],
    [
        "2",
        "í™?ê¸¸ìˆœ",
        "2154315",
        "etc2",
        [
            [ "test2@daum.net", "2009-09-26" ],
            [ "test2@naver.com", "2009-05-21,hide" ], 
            [ "lsb@naver.com", "2010-03-05" ],
            [ "lsb3@naver.com", ",hide" ],
            [ "sooboklee9@daum.net", "2010-03-05" ]
        ],
        "2010-03-05",
        "show"
    ]
]
	
    var oTable = $('#example').dataTable({
        "aaData": aDataSet,
        "aoColumns": [
          { "mDataProp": "0"},
          { "mDataProp": "1"},
          { "mDataProp": "2"},
          { "mDataProp": "3"},
          { "mDataProp": "4.0.0"},
          { "mDataProp": "4.0.1"},
          { "mDataProp": "4.1.0"},
          { "mDataProp": "4.1.1"},
          { "mDataProp": "5"},
          { "mDataProp": "6"}
        ]
    });
	
	
	oTest.fnTest( 
		"Initialisation",
		null,
		function () {
			return $('#example tbody tr:eq(0) td:eq(0)').html() == '1';
		}
	);
	
	oTest.fnTest( 
		"Update row",
		function () {
      $('#example').dataTable().fnUpdate( [
          "0",
          "í™?ê¸¸ìˆœ",
          "2154315",
          "etc2",
          [
              [ "test2@daum.net", "2009-09-26" ],
              [ "test2@naver.com", "2009-05-21,hide" ], 
              [ "lsb@naver.com", "2010-03-05" ],
              [ "lsb3@naver.com", ",hide" ],
              [ "sooboklee9@daum.net", "2010-03-05" ]
          ],
          "2010-03-05",
          "show"
      ], 1 );
		},
		function () {
			return $('#example tbody tr:eq(0) td:eq(0)').html() == '0';
		}
	);
	
	oTest.fnTest( 
		"Original row preserved",
		null,
		function () {
			return $('#example tbody tr:eq(1) td:eq(0)').html() == '1';
		}
	);
	
	
	
	oTest.fnComplete();
} );