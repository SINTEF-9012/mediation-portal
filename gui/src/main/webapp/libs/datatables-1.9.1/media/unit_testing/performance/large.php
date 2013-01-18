<?php
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
  /* MySQL connection */
	include( $_SERVER['DOCUMENT_ROOT']."/datatables/mysql.php" ); /* ;-) */
	
	$gaSql['link'] =  mysql_pconnect( $gaSql['server'], $gaSql['user'], $gaSql['password']  ) or
		die( 'Could not open connection to server' );
	
	mysql_select_db( $gaSql['db'], $gaSql['link'] ) or 
		die( 'Could not select database '. $gaSql['db'] );

?><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link rel="shortcut icon" type="image/ico" href="http://www.datatables.net/favicon.ico" />
		
		<title>DataTables example</title>
		<style type="text/css" title="currentStyle">
			@import "../../css/demo_page.css";
			@import "../../css/demo_table.css";
		</style>
		<script type="text/javascript" language="javascript" src="../../js/jquery.js"></script>
		<script type="text/javascript" language="javascript" src="../../js/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf-8">
			$(document).ready(function() {
				var oTable = $('#example').dataTable();
				var iStart = new Date().getTime();
				
				//if ( typeof console != 'undefined' ) {
				//	console.profile();
				//}
				for ( var i=0 ; i<10 ; i++ )
				{
					var oTable = $('#example').dataTable({"bDestroy": true});
				}
				//if ( typeof console != 'undefined' ) {
				//	console.profileEnd();
				//}
				
				//oTable.fnSort( [[ 1, 'asc' ]] );
				//oTable.fnSort( [[ 1, 'asc' ]] );
				//oTable.fnSort( [[ 2, 'asc' ]] );
				//oTable.fnSort( [[ 1, 'asc' ]] );
				//oTable.fnSort( [[ 2, 'asc' ]] );
				
				var iEnd = new Date().getTime();
				document.getElementById('output').innerHTML = "Test took "+(iEnd-iStart)+" mS";
			} );
		</script>
	</head>
	<body id="dt_example">
		<div id="container">
			<div class="full_width big">
				<i>DataTables</i> performance test - draw
			</div>
			<div id="output"></div>

			<div id="demo">
<table cellpadding="0" cellspacing="0" border="0" class="display" id="example">
	<thead>
		<tr>
			<th>id</th>
			<th>name</th>
			<th>phone</th>
			<th>email</th>
			<th>city</th>
			<th>zip</th>
			<th>state</th>
			<th>country</th>
			<th>zip2</th>
		</tr>
	</thead>
	<tbody>
<?php
	$sQuery = "
		SELECT *
		FROM   testData
		LIMIT  2000
	";
	$rResult = mysql_query( $sQuery, $gaSql['link'] ) or die(mysql_error());
	while ( $aRow = mysql_fetch_array( $rResult ) )
	{
		echo '<tr>';
		echo '<td><a href="1">'.$aRow['id'].'</a></td>';
		echo '<td>'.$aRow['name'].'</td>';
		echo '<td>'.$aRow['phone'].'</td>';
		echo '<td>'.$aRow['email'].'</td>';
		echo '<td>'.$aRow['city'].'</td>';
		echo '<td>'.$aRow['zip'].'</td>';
		echo '<td>'.$aRow['state'].'</td>';
		echo '<td>'.$aRow['country'].'</td>';
		echo '<td>'.$aRow['zip2'].'</td>';
		echo '</tr>';
	}
?>
	</tbody>
</table>
			</div>
			<div class="spacer"></div>
			
			<div id="footer" style="text-align:center;">
				<span style="font-size:10px;">
					DataTables &copy; Allan Jardine 2008-2009.
				</span>
			</div>
		</div>
	</body>
</html>