<%-- /** * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved. *
* This library is free software; you can redistribute it and/or modify
it under * the terms of the GNU Lesser General Public License as
published by the Free * Software Foundation; either version 2.1 of the
License, or (at your option) * any later version. * * This library is
distributed in the hope that it will be useful, but WITHOUT * ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
License for more * details. */ --%> <%@ taglib
uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />

<html>
<head>
<meta charset="utf-8">
<title>Mediation Portlet - SINTEF ICT</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Le style -->
<link
	href="http://54.247.114.191/net.modelbased.mediation.gui-0.0.1-SNAPSHOT/libs/bootstrap/css/bootstrap.css"
	rel="stylesheet" />
<link
	href="http://54.247.114.191/net.modelbased.mediation.gui-0.0.1-SNAPSHOT/css/mediation-portal.css"
	rel="stylesheet">
<link
	href="http://54.247.114.191/net.modelbased.mediation.gui-0.0.1-SNAPSHOT/libs/bootstrap/css/bootstrap-responsive.css"
	rel="stylesheet" />
<link
	href="http://54.247.114.191/net.modelbased.mediation.gui-0.0.1-SNAPSHOT/libs/bootstrap/css/DT_bootstrap.css"
	rel="stylesheet" />
<link
	href="http://54.247.114.191/net.modelbased.mediation.gui-0.0.1-SNAPSHOT/libs/datatables-1.9.1/media/css/demo_page.css"
	rel="stylesheet" />

<!-- Le fav and touch icons -->
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="http://54.247.114.191/net.modelbased.mediation.gui-0.0.1-SNAPSHOT/libs/bootstrap/img/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="http://54.247.114.191/net.modelbased.mediation.gui-0.0.1-SNAPSHOT/libs/bootstrap/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="http://54.247.114.191/net.modelbased.mediation.gui-0.0.1-SNAPSHOT/libs/bootstrap/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="http://54.247.114.191/net.modelbased.mediation.gui-0.0.1-SNAPSHOT/libs/bootstrap/ico/apple-touch-icon-57-precomposed.png">
</head>
<body>

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
				<button class="btn btn-primary" onclick="notifyMappingComplete();">Mapping
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

	<script
		src="http://54.247.114.191/net.modelbased.mediation.gui-0.0.1-SNAPSHOT/libs/jquery/jquery-1.8.1.min.js"></script>
	<script
		src="http://54.247.114.191/net.modelbased.mediation.gui-0.0.1-SNAPSHOT/libs/datatables-1.9.1/media/js/jquery.dataTables.js"></script>

	<script
		src="http://54.247.114.191/net.modelbased.mediation.gui-0.0.1-SNAPSHOT/libs/bootstrap/js/bootstrap-transition.js"></script>
	<script
		src="http://54.247.114.191/net.modelbased.mediation.gui-0.0.1-SNAPSHOT/libs/bootstrap/js/bootstrap-alert.js"></script>
	<script
		src="http://54.247.114.191/net.modelbased.mediation.gui-0.0.1-SNAPSHOT/libs/bootstrap/js/bootstrap-modal.js"></script>
	<script
		src="http://54.247.114.191/net.modelbased.mediation.gui-0.0.1-SNAPSHOT/libs/bootstrap/js/bootstrap-dropdown.js"></script>
	<script
		src="http://54.247.114.191/net.modelbased.mediation.gui-0.0.1-SNAPSHOT/libs/bootstrap/js/bootstrap-scrollspy.js"></script>
	<script
		src="http://54.247.114.191/net.modelbased.mediation.gui-0.0.1-SNAPSHOT/libs/bootstrap/js/bootstrap-tab.js"></script>
	<script
		src="http://54.247.114.191/net.modelbased.mediation.gui-0.0.1-SNAPSHOT/libs/bootstrap/js/bootstrap-tooltip.js"></script>
	<script
		src="http://54.247.114.191/net.modelbased.mediation.gui-0.0.1-SNAPSHOT/libs/bootstrap/js/bootstrap-popover.js"></script>
	'
	<script
		src="http://54.247.114.191/net.modelbased.mediation.gui-0.0.1-SNAPSHOT/libs/bootstrap/js/bootstrap-button.js"></script>
	<script
		src="http://54.247.114.191/net.modelbased.mediation.gui-0.0.1-SNAPSHOT/libs/bootstrap/js/bootstrap-collapse.js"></script>
	<script
		src="http://54.247.114.191/net.modelbased.mediation.gui-0.0.1-SNAPSHOT/libs/bootstrap/js/bootstrap-carousel.js"></script>
	<script
		src="http://54.247.114.191/net.modelbased.mediation.gui-0.0.1-SNAPSHOT/libs/bootstrap/js/bootstrap-typeahead.js"></script>
	<script
		src="http://54.247.114.191/net.modelbased.mediation.gui-0.0.1-SNAPSHOT/libs/bootstrap/js/DT_bootstrap.js"></script>

	<script type="text/javascript"
		src="http://54.247.114.191/net.modelbased.mediation.gui-0.0.1-SNAPSHOT/javascripts/mediation-portal-api.js"></script>
</body>

</html>