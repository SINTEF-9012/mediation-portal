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
              <li><a href="about.jsp"><i class="icon-star icon-white"></i> About</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container">
     

      <div class="page-header">
	<h1>Mediator Service<small> &mdash; Getting matching recommendations</small></h1>
      </div>

      <!-- View for the model repository -->
      <div class="container-fluid">
	<div class="row-fluid">
	  <div class="span4">
	    <!--Sidebar content-->
	    <form class="well">
	      <h3>Mediation Request</h3>
	      <!-- <div class="alert alert-info">
		   <button class="close" data-dismiss="alert">�</button>
		   <strong>Info!</strong> Specify the models and algorithms you need in your mediation</div> -->
	      <fieldset>
		<div class="controlup">
		  <label class="control-label">Source Model:</label>
		  <div class="controls docs-input-sizes">
		    <select class="span10" >
		      <option>Model 1</option>
		      <option>Model 2</option>
		      <option>Model 3</option>
		      <option>Model 4</option>
		      <option>Model 5</option>
		    </select>
		  </div>
		</div>
		<div class="controlup">
		  <label class="control-label">Target Model:</label>
		  <div class="controls docs-input-sizes">
		    <select class="span10">
		      <option>Model 1</option>
		      <option>Model 2</option>
		      <option>Model 3</option>
		      <option>Model 4</option>
		      <option>Model 5</option>
		    </select>
		  </div>
		</div>
		<div class="controlup">
		  <label class="control-label">Algorithm:</label>
		  <div class="controls docs-input-sizes">
		    <select class="span10">
		      <option>Syntactic Match</option>
		      <option>Random Match</option>
		      <option>Similarity Flooding</option>
		    </select>
		  </div>
		</div>
		<div class="form-actions">
		  <button type="submit" class="btn btn-primary"><i class="icon-play icon-white"></i> Search for Matches</button>
		</div>
	      </fieldset>
	    </form>
	  </div>
	  <div class="span8">
	    <h3>Resulting Matches</h3>
	    <table class="table table-striped">  
	      <thead>  
		<tr>  
		  <th>Source Element</th>  
		  <th>Target Element</th>  
	 	  <th>Similarity</th>
	 	  <th>Confirmed</th>  
		</tr>  
	      </thead>  
	      <tbody>  
		<tr>  
		  <td>OTA XSD</td>  
		  <td>Rammohan </td>  
		  <td>10.56</td>  
		  <td>No</td>
		</tr>  
		<tr>  
		  <td>002</td>  
		  <td>Smita</td>  
		  <td>56.45</td>  
		  <td>No</td>  
		</tr>  
		<tr>  
		  <td>003</td>  
		  <td>Rabindranath</td>  
		  <td>97.45</td>  
		  <td>Yes</td>  
		</tr>  
	      </tbody>  
	    </table>
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
    <script src="bootstrap/js/jquery.js"></script>
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
    <script scr="javascripts/jquery-1.7.2.min.js"></script>

    <script>
      $(function () {
      $('#myTab a:last').tab('show');
      })
    </script>

  </body>
</html>
