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


$.extend( DataTable.ext.aTypes, [
	/*
	 * Function: -
	 * Purpose:  Check to see if a string is numeric
	 * Returns:  string:'numeric' or null
	 * Inputs:   mixed:sText - string to check
	 */
	function ( sData )
	{
		/* Allow zero length strings as a number */
		if ( typeof sData === 'number' )
		{
			return 'numeric';
		}
		else if ( typeof sData !== 'string' )
		{
			return null;
		}
		
		var sValidFirstChars = "0123456789-";
		var sValidChars = "0123456789.";
		var Char;
		var bDecimal = false;
		
		/* Check for a valid first char (no period and allow negatives) */
		Char = sData.charAt(0); 
		if (sValidFirstChars.indexOf(Char) == -1) 
		{
			return null;
		}
		
		/* Check all the other characters are valid */
		for ( var i=1 ; i<sData.length ; i++ ) 
		{
			Char = sData.charAt(i); 
			if (sValidChars.indexOf(Char) == -1) 
			{
				return null;
			}
			
			/* Only allowed one decimal place... */
			if ( Char == "." )
			{
				if ( bDecimal )
				{
					return null;
				}
				bDecimal = true;
			}
		}
		
		return 'numeric';
	},
	
	/*
	 * Function: -
	 * Purpose:  Check to see if a string is actually a formatted date
	 * Returns:  string:'date' or null
	 * Inputs:   string:sText - string to check
	 */
	function ( sData )
	{
		var iParse = Date.parse(sData);
		if ( (iParse !== null && !isNaN(iParse)) || (typeof sData === 'string' && sData.length === 0) )
		{
			return 'date';
		}
		return null;
	},
	
	/*
	 * Function: -
	 * Purpose:  Check to see if a string should be treated as an HTML string
	 * Returns:  string:'html' or null
	 * Inputs:   string:sText - string to check
	 */
	function ( sData )
	{
		if ( typeof sData === 'string' && sData.indexOf('<') != -1 && sData.indexOf('>') != -1 )
		{
			return 'html';
		}
		return null;
	}
] );

