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

$.extend( DataTable.ext.oSort, {
	/*
	 * text sorting
	 */
	"string-pre": function ( a )
	{
		if ( typeof a != 'string' ) {
			a = (a !== null && a.toString) ? a.toString() : '';
		}
		return a.toLowerCase();
	},

	"string-asc": function ( x, y )
	{
		return ((x < y) ? -1 : ((x > y) ? 1 : 0));
	},
	
	"string-desc": function ( x, y )
	{
		return ((x < y) ? 1 : ((x > y) ? -1 : 0));
	},
	
	
	/*
	 * html sorting (ignore html tags)
	 */
	"html-pre": function ( a )
	{
		return a.replace( /<.*?>/g, "" ).toLowerCase();
	},
	
	"html-asc": function ( x, y )
	{
		return ((x < y) ? -1 : ((x > y) ? 1 : 0));
	},
	
	"html-desc": function ( x, y )
	{
		return ((x < y) ? 1 : ((x > y) ? -1 : 0));
	},
	
	
	/*
	 * date sorting
	 */
	"date-pre": function ( a )
	{
		var x = Date.parse( a );
		
		if ( isNaN(x) || x==="" )
		{
			x = Date.parse( "01/01/1970 00:00:00" );
		}
		return x;
	},

	"date-asc": function ( x, y )
	{
		return x - y;
	},
	
	"date-desc": function ( x, y )
	{
		return y - x;
	},
	
	
	/*
	 * numerical sorting
	 */
	"numeric-pre": function ( a )
	{
		return (a=="-" || a==="") ? 0 : a*1;
	},

	"numeric-asc": function ( x, y )
	{
		return x - y;
	},
	
	"numeric-desc": function ( x, y )
	{
		return y - x;
	}
} );
