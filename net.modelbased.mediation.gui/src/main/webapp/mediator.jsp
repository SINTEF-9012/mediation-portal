<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Mediation Portal - SINTEF ICT</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le style -->
    <link href="bootstrap/css/bootstrap.css" rel="stylesheet"/>
    <style>
      body {
         padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
      .centered { vertical-align:middle; text-align:center; }
    </style>
    <link href="bootstrap/css/bootstrap-responsive.css" rel="stylesheet"/>
    <link href="bootstrap/css/DT_bootstrap.css" rel="stylesheet"/>
    <link href="DataTables-1.9.1/media/css/demo_page.css" rel="stylesheet"/>

    <!-- Le fav and touch icons -->
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
              <li><a href="index.jsp"><i class="icon-home icon-white"></i> Home</a></li>
              <li><a href="repositories.jsp"><i class="icon-book icon-white"></i> Repositories</a></li>
              <li class="active"><a href="mediator.jsp"><i class="icon-random icon-white"></i> Mediator</a></li>
              <li><a href="comparator.jsp"><i class="icon-signal icon-white"></i> Comparator</a></li>
              <li><a href="about.jsp"><i class="icon-user icon-white"></i> About</a></li>
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
	  <div class="span3">
	    <!--Sidebar content-->
	    <form class="well">
	      <h3>Mediation Request</h3>
	      <p class="help-block"><strong>Note:</strong> Use the form below to specify the mediation you need to initiate.</p>
	      <fieldset>
		<div class="controlup">
		  <label class="control-label">Source Model:</label>
		  <div class="controls docs-input-sizes">
		    <select class="span10" >
		      <option>samples-article</option>
		      <option>samples-document</option>
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
		      <option>samples-document</option>
		      <option>samples-article</option>
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
	  <div class="span9">
	    <h3>Resulting Matches</h3>
	    <p class="help-block"><strong>Note:</strong> Below are the resulting matches detected by the mediation framework.</p>
	    <table class="table table-striped" id="results">  
	      <thead>  
		<tr>  
		  <th>Source Element</th>  
		  <th>Target Element</th>  
	 	  <th>Similarity</th>
	 	  <th>Valid?</th>  
		</tr>  
	      </thead>  
	      <tbody>  
		<tr>  
		  <td>Author</td>  
		  <td>Author</td>  
		  <td>100.00</td>  
		  <td>
		    <div class="btn-group" data-toggle="buttons-radio">
		      <button class="btn btn-mini"><i class="icon-question-sign"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-up"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-down"></i></button>
		    </div>
		  </td>
		</tr>  
		<tr>  
		  <td>Author/firstName</td>  
		  <td>Author/family_name</td>  
		  <td>78.67</td>  
		  <td>
		    <div class="btn-group" data-toggle="buttons-radio">
		      <button class="btn btn-mini"><i class="icon-question-sign"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-up"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-down"></i></button>
		    </div>
		  </td>
		</tr>  
		<tr>  
		  <td>Author/surname</td>  
		  <td>Author/givenname</td>  
		  <td>87.50</td>  
		  <td>
		    <div class="btn-group" data-toggle="buttons-radio">
		      <button class="btn btn-mini"><i class="icon-question-sign"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-up"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-down"></i></button>
		    </div>
		  </td>
		</tr>  
		<tr>  
		  <td>Author/email</td>  
		  <td>Author/givenname</td>  
		  <td>43.75</td>  
		  <td>
		    <div class="btn-group" data-toggle="buttons-radio">
		      <button class="btn btn-mini"><i class="icon-question-sign"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-up"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-down"></i></button>
		    </div>
		  </td>
		</tr>  
		<tr>  
		  <td>Paragraph/body</td>  
		  <td>Element/body</td>  
		  <td>56.45</td>  
		  <td>
		    <div class="btn-group" data-toggle="buttons-radio">
		      <button class="btn btn-mini"><i class="icon-question-sign"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-up"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-down"></i></button>
		    </div>
		  </td>
		</tr>  
		<tr>  
		  <td>Paragraph/body</td>  
		  <td>Element/body</td>  
		  <td>56.45</td>  
		  <td>
		    <div class="btn-group" data-toggle="buttons-radio">
		      <button class="btn btn-mini"><i class="icon-question-sign"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-up"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-down"></i></button>
		    </div>
		  </td>
		</tr>  
		<tr>  
		  <td>Paragraph/body</td>  
		  <td>Element/body</td>  
		  <td>56.45</td>  
		  <td>
		    <div class="btn-group" data-toggle="buttons-radio">
		      <button class="btn btn-mini"><i class="icon-question-sign"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-up"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-down"></i></button>
		    </div>
		  </td>
		</tr>  
		<tr>  
		  <td>Paragraph/body</td>  
		  <td>Element/body</td>  
		  <td>56.45</td>  
		  <td>
		    <div class="btn-group" data-toggle="buttons-radio">
		      <button class="btn btn-mini"><i class="icon-question-sign"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-up"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-down"></i></button>
		    </div>
		  </td>
		</tr>  
		<tr>  
		  <td>Paragraph/body</td>  
		  <td>Element/body</td>  
		  <td>56.45</td>  
		  <td>
		    <div class="btn-group" data-toggle="buttons-radio">
		      <button class="btn btn-mini"><i class="icon-question-sign"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-up"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-down"></i></button>
		    </div>
		  </td>
		</tr>  
		<tr>  
		  <td>Paragraph/body</td>  
		  <td>Element/body</td>  
		  <td>56.45</td>  
		  <td>
		    <div class="btn-group" data-toggle="buttons-radio">
		      <button class="btn btn-mini"><i class="icon-question-sign"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-up"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-down"></i></button>
		    </div>
		  </td>
		</tr>  
		<tr>  
		  <td>Paragraph/body</td>  
		  <td>Element/body</td>  
		  <td>56.45</td>  
		  <td>
		    <div class="btn-group" data-toggle="buttons-radio">
		      <button class="btn btn-mini"><i class="icon-question-sign"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-up"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-down"></i></button>
		    </div>
		  </td>
		</tr>  
		<tr>  
		  <td>Paragraph/body</td>  
		  <td>Element/body</td>  
		  <td>56.45</td>  
		  <td>
		    <div class="btn-group" data-toggle="buttons-radio">
		      <button class="btn btn-mini"><i class="icon-question-sign"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-up"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-down"></i></button>
		    </div>
		  </td>
		</tr>  
		<tr>  
		  <td>Paragraph/body</td>  
		  <td>Element/body</td>  
		  <td>56.45</td>  
		  <td>
		    <div class="btn-group" data-toggle="buttons-radio">
		      <button class="btn btn-mini"><i class="icon-question-sign"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-up"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-down"></i></button>
		    </div>
		  </td>
		</tr>  
		<tr>  
		  <td>Paragraph/body</td>  
		  <td>Element/body</td>  
		  <td>56.45</td>  
		  <td>
		    <div class="btn-group" data-toggle="buttons-radio">
		      <button class="btn btn-mini"><i class="icon-question-sign"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-up"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-down"></i></button>
		    </div>
		  </td>
		</tr>  
		<tr>  
		  <td>Paragraph/body</td>  
		  <td>Element/body</td>  
		  <td>56.45</td>  
		  <td>
		    <div class="btn-group" data-toggle="buttons-radio">
		      <button class="btn btn-mini"><i class="icon-question-sign"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-up"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-down"></i></button>
		    </div>
		  </td>
		</tr>  
		<tr>  
		  <td>Paragraph/body</td>  
		  <td>Element/body</td>  
		  <td>56.45</td>  
		  <td>
		    <div class="btn-group" data-toggle="buttons-radio">
		      <button class="btn btn-mini"><i class="icon-question-sign"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-up"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-down"></i></button>
		    </div>
		  </td>
		</tr>  
		<tr>  
		  <td>Paragraph/body</td>  
		  <td>Element/body</td>  
		  <td>56.45</td>  
		  <td>
		    <div class="btn-group" data-toggle="buttons-radio">
		      <button class="btn btn-mini"><i class="icon-question-sign"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-up"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-down"></i></button>
		    </div>
		  </td>
		</tr>  
		<tr>  
		  <td>Paragraph/body</td>  
		  <td>Element/body</td>  
		  <td>56.45</td>  
		  <td>
		    <div class="btn-group" data-toggle="buttons-radio">
		      <button class="btn btn-mini"><i class="icon-question-sign"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-up"></i></button>
		      <button class="btn btn-mini"><i class="icon-thumbs-down"></i></button>
		    </div>
		  </td>
		</tr>  
	      </tbody>
	      <tfoot>  
		<tr>  
		  <th>Source Element</th>  
		  <th>Target Element</th>  
	 	  <th>Similarity</th>
	 	  <th>Valid?</th>
		</tr>  
	      </tfoot>  
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

    <script src="javascripts/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="./DataTables-1.9.1/media/js/jquery.dataTables.js"></script>

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
    <script src="bootstrap/js/DT_bootstrap.js"></script>

    <script type="text/javascript" src="javascripts/mediator.js"></script>

  </body>
</html>
