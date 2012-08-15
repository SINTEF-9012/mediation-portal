## The Model Repository ##

The model repository is available at <http://my-portal/mediation/repositories/models>, where `my-portal` stands for your instance of the mediation portal. The operations available are:

 - `. (GET)` return the list of models stored in the model repository. 

 - `. (POST)` add a new model in the model repository.The content of the model should be placed in the following JSON document:
		
		{ 	"name": "my-model", 
			"content": "this is my data model"
		}

 - `./my-model (GET)` return the model identified by `my-model`. This invocation does not require any additional JSON content.

 - `./my-model (DELETE)` delete the model identified by `my-model`. This invocation does not require any additional JSON content

 - `./my-model (PUT)` replace the model identified by `my-model` by the given content
