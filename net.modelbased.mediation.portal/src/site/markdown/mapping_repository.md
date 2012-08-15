## The Mapping Repository ##

The mapping repository is available at <http://my-portal/mediation/repositories/mappings>, where `my-portal` stands for the instance of the mediation portal that you would like to use. Mappings are relationships between model elements. They are represented as a set of tuples (String, String, ...) where the first field is the identifier of a model element in the source model, and the second field is the identifier of a model element in the target model. 

The REST API of the mapping repository includes the following operations:

- `. (GET)` return the list of mappings stored in the mapping repository

- `. (POST)` add a new mapping in the repository. The mapping should be formatted as the following JSON document. 
	
		{ 	"uid": "my-mapping-uid",
	   	 	"capacity": "25",
			"status": "READY",
			"entries": [
				{	"source": "x",
					"target": "y",
					"degree": "0.67909",
					"origin": "random match",
					"isValidated": "false"
				}
			]
		}

- `./my-mapping-id (GET)` returns the mapping with the id `my-mapping-id` as a JSON document.

- `./my-mapping-id (DELETE)` delete the mapping with id `my-mapping-id`. 

- `./my-mapping-id/asXML (GET)` return the mapping with ID `my-mapping-id` as an XML document.

- `./my-mapping-id/status (GET)`  return the status of the mapping with ID `my-mapping-id`

- `./my-mapping-id/status (PUT)`  update the status of the mapping with ID `my-mapping-id`. The value of the status is expected to be a literal String object passed within the HTTP request.

- `./my-mapping-id/content (GET)` return the content of the mapping with ID `my-mapping-id`

- `./my-mapping-id/content (PUT)` update the content of the mapping with ID `my-mappin- id`. The content are expected to be a list of entries, as shown in the following JSON snippet:

		[
			{	"source": "x",
				"target": "y",
				"degree": "0.67909",
				"origin": "random match",
				"isValidated": "false"
			}
		]

- `./my-mapping-id/content (DELETE)` clean the mapping with ID `my-mapping-id`. The mapping will thus be empty.

- `./my-mapping-id/content/source-element (GET)` return the list of target elements associated with the source element whose ID is `source-element`.

- `./my-mapping-id/content/source-element (DELETE)` delete all the associations that have `source-element` as a source element

- `./my-mapping-id/content/source-element/target-element (GET)` return the detailed information attached to the association between `source-element` and `target-element`

- `./my-mapping-id/content/source-element/target-element (DELETE)`  delete the association between `source-element` and `target-element`
