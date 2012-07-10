# Mediation Framework #

This is a simple console test of the markdown plugin for doxia! Let's how
far we can go using it and markdown, maybe we can do some nice documentation
with it!


Mediating between to models, (XML schemas so far) is a 3-step process:

1. Publish the models that you want to mediate
2. Send a mediation request to the mediation portal
3. (Optional) Evaluate the resulting mapping against an existing mapping

Each of these three steps are available through REST services, and can thus be
called from any artifact communicate over HTTP (program, browser, etc.) 		
		

## Publishing Models ##

The mediation portal provides users with a model repository in which they can
publish models identified by a name. This model repository is available at
the following address:

	http://localhost:8080/mediation/repositories/models
	
To publish a new model in the repository, just send an HTTP POST request, to 
the repository, with the following JSON body. 

	{
		"name": "my-model",
		"content": "<?xml version=\"1.0\" encoding=\"UTF-8\"?> ..."
	}


A HTTP GET request sent at the following URL will let you retrieve your model: 	

<http://localhost:8080/mediation/repositories/models/my-model>

 	
## Running a Mediation ##

Triggering a mediation is done as well by sending specific request to the mediation
portal. The key point is that mediation are only available between models that 
have been previously published in the repository. Below is the sample request 
that can be send to the mediation-portal at <http://localhost:8080/mediator>:

	{ 
		"source": "model1", 
		"target": "model2", 
		"algorithm":"syntactic" 
	}  

Triggering a mediation will generate a mapping between the model elements of the 
two models elements. This mapping is identified by a unique ID, such as 
`7ba5cfd1-8b43-4292-892a-b32281ef4189` and is available in a specific repository
on the mediation portal 

<http://localhost:8080/mediation/repositories/mappings/7ba5cfd1-8b43-4292-892a-b32281ef4189>

## Evaluation of a Mapping ##

In case you are trying to evaluate the relevance of a given mediation algorithm
on a given case study, you may want to evaluate the performance (typically metrics
such as precision, recall and f-measure). This is possible using the comparator
service, which given two mappings (one being considered as the "oracle", or the
reference") will provide you with the relevant performance hints.

This service can be trigger by sending a POST request at 
<http://localhost:8080/comparator> with the following JSON body:

	{
		"oracle": "mapping1",
		"mapping": "mapping2",
		"note": "This is a test of the comparator service"
	}
	
## Creating you Own Complex Mediation Algorithm ##

Last but not least, the mediation portal let you create your own mediation algorithm
by combining existing ones as well as various predefined functions (including 
string matching, vectors distance, etc.). The framework let you create 5 type of 
alrgorithms that you will then be able to combine:

 - *Mediation*, which basically accepts two models, one mapping, and refine the mapping
 based on a specific analysis of the two given models.
 - *ModelProcessor*, process a single models and produces a alternate version of it. 
 Various common processing such pruning, relabelling, translating fall in this category.
 - *MappingAggregator*, which accepts as input a list of mapping and produce a single
 one using a specific algorithm.
 - *ModelAggregator*, which accepts as input a list of models and produce a single
 one based on specific criterions.
 
To create your own mediation algorithm, you will have to:

1. Create you a Scala class that extends some specific classes amd traits of the 
mediation framework
2. Register your algorithm in the mediation portal

Below is an example of such a composite mediation:
	
	import net.modelbased.mediation.library.algorithm._
	import net.modelbased.mediation.service.repository.mapping.data.Mapping
	import net.modelbased.mediation.service.repository.model.data.Model
	
	class SampleMediation extends Mediation {
	
	  // We import the standard library of mediation algorithms 
	  import net.modelbased.mediation.library.algorithm.Commons._
	
	  /*
	   * Here, we override the behaviour of the mediation, by running a syntactic
	   * match on one side, a semantic match on the other side, and finally merging the
	   * two results by selecting the most relevant entries. 
	   */
	  override def execute(in: Mapping, source: Model, target: Model) = {
	    val m1 = syntacticMatch(in, source, target)
	    val m2 = randomMatch(in, source, target)
	    val result = aggregateByMax(Set(m1, m2))
	  }
	
	}
	
