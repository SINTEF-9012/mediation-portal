<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Mediation Portal - SINTEF ICT</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
      body {
      padding-top: 60px;
      padding-bottom: 40px;
      }
    </style>
    <link href="bootstrap/css/bootstrap-responsive.css" rel="stylesheet">

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="bootstrap/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="bootstrap/img/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="bootstrap/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="bootstrap/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="bootstrap/ico/apple-touch-icon-57-precomposed.png">

  </head>


  <body>

    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="#">Mediation Portal</a>
          <div class="nav-collapse">
            <ul class="nav">
              <li class="active"><a href="index.jsp"><i class="icon-home icon-white"></i> Home</a></li>
              <li><a href="repositories.jsp"><i class="icon-book icon-white"></i> Repositories</a></li>
              <li><a href="mediator.jsp"><i class="icon-random icon-white"></i> Mediator</a></li>
              <li><a href="comparator.jsp"><i class="icon-signal icon-white"></i> Comparator</a></li>
              <li><a href="about.jsp"><i class="icon-user icon-white"></i> About</a></li>
          </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container">
      
      <div class="page-header">
	<h1>Comparator Service
	<small>&mdash; Evaluating the effectiveness of mediation algorithms</small></h1>
      </div>
      
      <!-- View for the model repository -->
      <div class="tab-pane active" id="models">
	<div class="container-fluid">
	  <div class="row-fluid">
	    <div class="span3">
	      <!--Sidebar content-->
	      <form class="well">
		<h2>Comparison Request</h2>
		<p class="help-block"><strong>Note:</strong> Use the form below to specify what mappings have to be evaluated against what oracle.</p>
		<fieldset>
		<div class="controlup">
		  <label class="control-label">Oracle Mapping:</label>
		  <div class="controls docs-input-sizes">
		    <select class="span12" >
		      <option>550e8400-e29b-41d4-a716-446655440000</option>
		      <option>f81d4fae-7dec-11d0-a765-00a0c91e6bf6</option>
		      <option>89ed71ce-5a66-4c10-a781-899f59614e25</option>
		      <option>523abe18-cb2c-406c-9893-6f61ccf9190a</option>
		      <option>7159ca3a-1d75-4d97-8908-e665e1b7d0f1</option>
		      <option>69fea696-eb56-4c23-8aee-5848ac41fc6a</option>
		      <option>ec50c6db-1e3e-420f-b416-9d2da5b5642a</option>
		      <option>29ffebd6-c51f-4981-8a5d-0f91b6e99b33</option>
		      <option>fab3ade3-34ce-4f5d-beed-7e009a69056a</option>
		    </select>
		  </div>
		</div>
		<div class="control-group">
		  <label class="control-label">Subject Mappings:</label>
		  <div class="controls docs-input-sizes">
		    <select multiple="multiple" class="span12" size="10">>
		      <option>550e8400-e29b-41d4-a716-446655440000</option>
		      <option>f81d4fae-7dec-11d0-a765-00a0c91e6bf6</option>
		      <option>89ed71ce-5a66-4c10-a781-899f59614e25</option>
		      <option>523abe18-cb2c-406c-9893-6f61ccf9190a</option>
		      <option>7159ca3a-1d75-4d97-8908-e665e1b7d0f1</option>
		      <option>69fea696-eb56-4c23-8aee-5848ac41fc6a</option>
		      <option>ec50c6db-1e3e-420f-b416-9d2da5b5642a</option>
		      <option>29ffebd6-c51f-4981-8a5d-0f91b6e99b33</option>
		      <option>fab3ade3-34ce-4f5d-beed-7e009a69056a</option>
		    </select>
		  </div>
		</div>
		<div class="form-actions">
		  <button type="submit" class="btn btn-primary">Launch Evaluation</button>
		</div>
		</fieldset>
	      </form>
	    </div>
	    <div class="span9">
	      <div id="chart-container"></div>
	    </div>
	  </div>
	</div>
	
      </div>
      
      <hr>
      
      <footer>
        <p>&copy; SINTEF IKT 2012</p>
      </footer>

    </div> <!-- /container -->

    <!-- Le javascript
	 ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="javascripts/jquery-1.7.2.min.js"></script>

    <script src="http://code.highcharts.com/highcharts.js"></script>
    <script src="http://code.highcharts.com/modules/exporting.js"></script>
    <script src="javascripts/charts.js"></script>
    
    <script src="bootstrap/js/bootstrap-transition.js"></script>
    <script src="bootstrap/js/bootstrap-alert.js"></script>
    <script src="bootstrap/js/bootstrap-modal.js"></script>
    <script src="bootstrap/js/bootstrap-dropdown.js"></script>
    <script src="bootstrap/js/bootstrap-scrollspy.js"></script>
    <script src="bootstrap/js/bootstrap-tab.js"></script>
    <script src="bootstrap/js/bootstrap-tooltip.js"></script>
    <script src="bootstrap/js/bootstrap-popover.js"></script>
    <script src="bootstrap/js/bootstrap-button.js"></script>
    <script src="bootstrap/js/bootstrap-collapse.js"></script>
    <script src="bootstrap/js/bootstrap-carousel.js"></script>
    <script src="bootstrap/js/bootstrap-typeahead.js"></script>

  </body>
</html>
