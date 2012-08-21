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

      <!-- Main hero unit for a primary marketing message or call to action -->
      <div class="hero-unit">
        <h1>Mediation Portal <small>v0.1 - SINTEF ICT</small></h1>
        <p>This Mediation Portal, developped by SINTEF, let you manipulate data models in various format (XSD, MOF, etc.), generates mappings between data elements, and or compare the effectiveness of various data mediation algorithms.</p>
        <p><a class="btn btn-primary btn-large">Learn more &raquo;</a></p>
      </div>

      <!-- Example row of columns -->
      <div class="row">
	   <div class="span4">
          <h2>Repositories</h2>
          <p>The Mediation Portal comes with three main repository, which can be used to store data models, mappings between data models, or comparison of mappings</p>
          <p><a class="btn" href="repositories.jsp">Try now &raquo;</a></p>
        </div>
        <div class="span4">
          <h2>Mediator Service</h2>
           <p>Given two data models, the mediator service will help you find which element of both models are equivalent. The mediator service will help you try out various existing mediation algorithms provided.</p>
          <p><a class="btn" href="mediator.jsp">Try now &raquo;</a></p>
        </div>
        <div class="span4">
          <h2>Comparator Service</h2>
           <p>Obtaining statistics about the effectiveness of a given mediation algorithm is also possible through the use of the comparator service. Assuming, you already knows what is the proper mapping between two data models, the comparator service will provide you with statistics (precision, recall, f-measure) describing the effectiveness of a given mediation algorithm.</p>
          <p><a class="btn" href="#">Try now &raquo;</a></p>
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

  </body>
</html>
