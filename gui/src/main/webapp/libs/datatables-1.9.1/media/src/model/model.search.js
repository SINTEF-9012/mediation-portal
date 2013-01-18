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



/**
 * Template object for the way in which DataTables holds information about
 * search information for the global filter and individual column filters.
 *  @namespace
 */
DataTable.models.oSearch = {
	/**
	 * Flag to indicate if the filtering should be case insensitive or not
	 *  @type boolean
	 *  @default true
	 */
	"bCaseInsensitive": true,

	/**
	 * Applied search term
	 *  @type string
	 *  @default <i>Empty string</i>
	 */
	"sSearch": "",

	/**
	 * Flag to indicate if the search term should be interpreted as a
	 * regular expression (true) or not (false) and therefore and special
	 * regex characters escaped.
	 *  @type boolean
	 *  @default false
	 */
	"bRegex": false,

	/**
	 * Flag to indicate if DataTables is to use its smart filtering or not.
	 *  @type boolean
	 *  @default true
	 */
	"bSmart": true
};

