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
 * Generate the node required for the processing node
 *  @param {object} oSettings dataTables settings object
 *  @returns {node} Processing element
 *  @memberof DataTable#oApi
 */
function _fnFeatureHtmlProcessing ( oSettings )
{
	var nProcessing = document.createElement( 'div' );
	
	if ( !oSettings.aanFeatures.r )
	{
		nProcessing.id = oSettings.sTableId+'_processing';
	}
	nProcessing.innerHTML = oSettings.oLanguage.sProcessing;
	nProcessing.className = oSettings.oClasses.sProcessing;
	oSettings.nTable.parentNode.insertBefore( nProcessing, oSettings.nTable );
	
	return nProcessing;
}


/**
 * Display or hide the processing indicator
 *  @param {object} oSettings dataTables settings object
 *  @param {bool} bShow Show the processing indicator (true) or not (false)
 *  @memberof DataTable#oApi
 */
function _fnProcessingDisplay ( oSettings, bShow )
{
	if ( oSettings.oFeatures.bProcessing )
	{
		var an = oSettings.aanFeatures.r;
		for ( var i=0, iLen=an.length ; i<iLen ; i++ )
		{
			an[i].style.visibility = bShow ? "visible" : "hidden";
		}
	}

	$(oSettings.oInstance).trigger('processing', [oSettings, bShow]);
}

