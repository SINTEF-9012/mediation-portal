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
 * each individual row. This is the object format used for the settings 
 * aoData array.
 *  @namespace
 */
DataTable.models.oRow = {
	/**
	 * TR element for the row
	 *  @type node
	 *  @default null
	 */
	"nTr": null,

	/**
	 * Data object from the original data source for the row. This is either
	 * an array if using the traditional form of DataTables, or an object if
	 * using mDataProp options. The exact type will depend on the passed in
	 * data from the data source, or will be an array if using DOM a data 
	 * source.
	 *  @type array|object
	 *  @default []
	 */
	"_aData": [],

	/**
	 * Sorting data cache - this array is ostensibly the same length as the
	 * number of columns (although each index is generated only as it is 
	 * needed), and holds the data that is used for sorting each column in the
	 * row. We do this cache generation at the start of the sort in order that
	 * the formatting of the sort data need be done only once for each cell
	 * per sort. This array should not be read from or written to by anything
	 * other than the master sorting methods.
	 *  @type array
	 *  @default []
	 *  @private
	 */
	"_aSortData": [],

	/**
	 * Array of TD elements that are cached for hidden rows, so they can be
	 * reinserted into the table if a column is made visible again (or to act
	 * as a store if a column is made hidden). Only hidden columns have a 
	 * reference in the array. For non-hidden columns the value is either
	 * undefined or null.
	 *  @type array nodes
	 *  @default []
	 *  @private
	 */
	"_anHidden": [],

	/**
	 * Cache of the class name that DataTables has applied to the row, so we
	 * can quickly look at this variable rather than needing to do a DOM check
	 * on className for the nTr property.
	 *  @type string
	 *  @default <i>Empty string</i>
	 *  @private
	 */
	"_sRowStripe": ""
};
