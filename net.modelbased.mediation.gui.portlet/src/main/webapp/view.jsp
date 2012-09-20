<%-- /** * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved. *
* This library is free software; you can redistribute it and/or modify
it under * the terms of the GNU Lesser General Public License as
published by the Free * Software Foundation; either version 2.1 of the
License, or (at your option) * any later version. * * This library is
distributed in the hope that it will be useful, but WITHOUT * ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
License for more * details. */ --%> 

<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />

	<div class="container">
		<div class="page-header">
			<h1>
				Mediation Portlet<small> &mdash; Refining mediation
					recommendations</small>
			</h1>
		</div>


		<div class="row">

			<div class="span10">
				<h3>
					Resulting Matches <small><span id="mapping-id">No
							Mapping selected</span></small>
				</h3>
				<p class="help-block">
					<strong>Note:</strong> Below are the resulting matches detected by
					the mediation framework.
				</p>
			</div>
			<div></div>
			<div class="span2">
				<button class="btn btn-primary" onclick="doMappingComplete();">Mapping
					Complete</button>
			</div>
		</div>

		<div>
			<table class="table table-striped" id="results"></table>
		</div>

		<hr>

		<footer>
			<p>&copy; SINTEF IKT 2012</p>
			<p>
				<a
					href="https://github.com/SINTEF-9012/mediation-portal/issues?state=open">Report
					Bugs, Make Suggestions, etc.</a>
			</p>
		</footer>

	</div>
