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
var giTotalTestCount = 0;
var giActiveModule = 0;
var giModuleTests;
var giStartTime;
var giTest;
var gbStop = false;
var gtoTest;

function fnTestStart ( sTestInfo )
{
	gaoTest[ giActiveModule ].iTests++;
	document.getElementById('test_info').innerHTML += 
		(giActiveModule+1)+'.'+(giModuleTests+1)+'. '+sTestInfo+'... ';
	document.getElementById('test_number').innerHTML = giTotalTestCount+1;
	giModuleTests++;
	giTotalTestCount++;
	
	/* Set a timer to catch stalled script */
	gtoTest = setTimeout( function () {
		fnMessage( '<span class="error">WARNING - test script stalled. Likely a JS error</span>' );
		gbStop = true;
	}, 3000 );
}

function fnTestResult ( bResult )
{
	clearTimeout( gtoTest );
	if ( bResult )
	{
		fnMessage( 'Passed' );
	}
	else
	{
		fnMessage( '<span class="error">FAILED</span>' );
		gbStop = true;
		fnEnd( false );
	}
}

function fnUnitStart( iTest )
{
	if ( !gbStop )
	{
		giModuleTests = 0;
		window.parent.test_arena.location.href = 
			(iTest==0?"":"../")+'templates/'+gaoTest[iTest].sTemplate+'.php?scripts='+gaoTest[iTest].sTest;
		giTest = iTest;
	}
}

function fnStartMessage( sMessage )
{
	fnMessage( '<br><b>'+gaoTest[giTest].sGroup+' - '+sMessage+'</b>' );
}

function fnMessage( sMessage )
{
	var nInfo = document.getElementById('test_info');
	nInfo.innerHTML += sMessage+'<br>';
	nInfo.scrollTop = nInfo.scrollHeight;
}

function fnUnitComplete()
{
	if ( giActiveModule < gaoTest.length - 1 )
	{
		fnUnitStart( ++giActiveModule );
	}
	else
	{
		fnEnd( true );
	}
}

function fnEnd( bSuccess )
{ 
	var iEndTime = new Date().getTime();
	var sTime = '<br>This test run took '+parseInt((iEndTime-giStartTime)/1000, 10)+
			' second(s) to complete.';
	
	if ( bSuccess )
	{
		$('#test_running').html( 'Tests complete. '+giTotalTestCount+' tests were run.'+sTime );
	}
	else
	{
		$('#test_running').html( 'Unit tests failed at test '+giTotalTestCount+'.'+sTime );
	}
}

$(document).ready( function () {
	giStartTime = new Date().getTime();
	fnUnitStart( giActiveModule );
} );