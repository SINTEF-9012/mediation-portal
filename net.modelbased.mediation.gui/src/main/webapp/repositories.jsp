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
              <li class="active"><a href="repositories.jsp"><i class="icon-book icon-white"></i> Repositories</a></li>
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
	<h1>Repositories <small>&mdash; Searching through the portal data</small></h1>
      </div>

      <div class="container-fluid">

      <div class="tabbable" style="margin-bottom: 18px;">
        <ul class="nav nav-tabs">
          <li class="active"><a href="#tab1" data-toggle="tab">Models</a></li>
          <li><a href="#tab2" data-toggle="tab">Mappings</a></li>
          <li><a href="#tab3" data-toggle="tab">Comparisons</a></li>
        </ul>

        <div class="tab-content" style="padding-bottom: 9px; border-bottom: 1px solid #ddd;">
          <div class="tab-pane active" id="tab1">
	    <div class="container-fluid">
	      <div class="row">
		<div class="span3">
		  <!--Sidebar content-->
		  <form class="well">
		    <h3>Import Model</h3>
		    <p class="help-block"><strong>Note:</strong> Use the form below to import a model in the repository</p>
		    
		    <div class="control-group">
		      <label class="control-label">Model ID:</label>
		      <div class="controls docs-input-sizes">
			<input class="input-large" type="text" placeholder="a unique ID for the new model">
		      </div>
		    </div>
		    <div class="control-group">
		      <label class="control-label">Description</label>
		      <div class="controls">
			<textarea class="input-large" id="textarea" rows="3" placeholder="a short description"></textarea>
		      </div>
		    </div>
		    <div class="control-group">
		      <label class="control-label" for="fileInput">Source File:</label>
		      <div class="controls">
			<input class="input-file" id="fileInput" type="file">
		      </div>
		    </div>
		    <div class="control-group">
		      <label class="control-label">Content Type:</label>
		      <div class="controls">
			<label class="radio">
			  <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1"> XSD (XML schema)
			</label>
			<label class="radio">
			  <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2"> ECore (EMF MoF metamodel)
			</label>
			<label class="radio">
			  <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2"> MoF textual (SINTEF syntax)
			</label>
		      </div>
		    </div>
		    <div class="form-actions">
		      <button type="submit" class="btn btn-primary"><i class="icon-upload icon-white"></i> Import</button>
		    </div>
		  </form>
		</div>
		<div class="span8">
		    <div>
		      <h3>Existing Models</h3>
		      <p class="help-block"><strong>Note:</strong> Below is the list of data models currently stored in the model repository.</p>
		    </div>

		    <div>
		      <table class="table table-striped" id="models-table">  
			<thead>  
			  <tr>  
			    <th>Model ID</th>  
			    <th>Description</th>  
			    <th>Type</th>  
			  </tr>  
			</thead>  
			<tbody>  
			  <tr>  
			    <td>REMICS-OTA</td>  
			    <td>Open Travel Agency Official</td>  
			    <td>text/xsd</td>  
			  </tr>  
			  <tr>  
			    <td>REMICS-XBRL</td>  
			    <td>eXtensible Business Reporting Language</td>  
			    <td>text/xsd</td>  
			  </tr> 
			  <tr>  
			    <td>REMICS-DI</td>  
			    <td>Internal accounting model of DI Systemer</td>  
			    <td>text/xsd</td>  
			  </tr>   
			  <tr>  
			    <td>REMICS-DOME</td>  
			    <td>Internal travel booking model of DOME</td>  
			    <td>text/xsd</td>  
			  </tr>   
			  <tr>  
			    <td>samples-document</td>  
			    <td>A sample data model describing textual documents</td>  
			    <td>text/mof</td>  
			  </tr>  
			  <tr>  
			    <td>samples-article</td>  
			    <td>A sample data model describing scientific articles</td>  
			    <td>text/mof</td>  
			  </tr>  
			</tbody> 
			<tfoot>
			  <tr>  
			    <th>Model ID</th>  
			    <th>Content</th>  
			    <th>Type</th>  
			  </tr> 
			</tfoot>
		      </table>
		  </div>
		    </div>
	      </div>
	    </div>
          </div>
	  
	  <!-- MAPPINGS REPOSITORY VIEW -->
          <div class="tab-pane" id="tab2">
	    <table class="table" id="mappings-table">
	      <thead>
		<th>Mapping ID</th>
		<th>Source</th>
		<th>Target</th>
		<th>Status</th>
	      </thead>
	      <tbody>
		<tr>
		  <td>f81d4fae-7dec-11d0-a765-00a0c91e6bf6</td>
		  <td>my-model</td>
		  <td>my-other-model</td>
		  <td>COMPLETE</td>
		</tr>
		<tr>
		  <td>f81d4fae-7dec-11d0-a765-00a0c91e6bf6</td>
		  <td>my-model</td>
		  <td>my-other-model</td>
		  <td>COMPLETE</td>
		</tr>
		<tr>
		  <td>f81d4fae-7dec-11d0-a765-00a0c91e6bf6</td>
		  <td>my-model</td>
		  <td>my-other-model</td>
		  <td>COMPLETE</td>
		</tr>
	      </tbody>
	      <tfoot>
		<tr>
		  <th>Mapping ID</th>
		  <th>Source</th>
		  <th>Target</th>
		  <th>Status</th>
		</tr>
	      </tfoot>
	    </table>
          </div>

	  <!-- COMPARISON REPOSITORY VIEW -->
          <div class="tab-pane" id="tab3">
	    <table class="table table-striped" id="comparisons-table">
	      <thead>
		<th>Comparison ID</th>
		<th>Oracle</th>
		<th>Mapping</th>
		<th>Status</th>
	      </thead>
	      <tbody>
		<tr>
		  <td>f81d4fae-7dec-11d0-a765-00a0c91e6bf6</td>
		  <td>Mapping #1</td>
		  <td>Mapping #2</td>
		  <td>COMPLETE</td>
		</tr>
		<tr>
		  <td>f81d4fae-7dec-11d0-a765-00a0c91e6bf6</td>
		  <td>mapping #2</td>
		  <td>mapping #3</td>
		  <td>COMPLETE</td>
		</tr>
		<tr>
		  <td>f81d4fae-7dec-11d0-a765-00a0c91e6bf6</td>
		  <td>my-model</td>
		  <td>my-other-model</td>
		  <td>COMPLETE</td>
		</tr>
	      </tbody>
	      <tfoot>
		<tr>
		  <th>Mapping ID</th>
		  <th>Source</th>
		  <th>Target</th>
		  <th>Status</th>
		</tr>
	      </tfoot>
	    </table>
          </div>
        </div>
      </div> <!-- /tabbable -->
      </div>

      
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
    
    <script type="text/javascript" src="javascripts/repositories.js"></script>

  </body>
</html>
