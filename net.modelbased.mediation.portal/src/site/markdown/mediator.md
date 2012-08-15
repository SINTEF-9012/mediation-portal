## The Mediator Service

The **mediator service** is the central component of the SINTEF Mediation Portal. It lets the user select two data models in the model repository and trigger a mediation that computes the mapping between them, and finally stores the resulting mapping into the mapping repository.

The mediator service is available at the address <http://my-portal/mediator> where `my-portal` stands for the running instance of the mediation portal you intend to use.

The REST API of the mediator service provide a single operation:

 * `. (POST)` trigger the mediation between the two models specified in the HTTP request. The content of the request should be formatted as the following JSON document:

		{
			"source": "my-first-model",
			"target": "my-second-model",
			"algorithm": "xsd-syntax-match"
		} 

	In this simple example, `source` and `target` should contain the UID of models that exists in the model repository. The last field, namely `algorithm` specifies the algorithm that one wants to use to calculate the mapping.

	As a response, the mediator service will provide the UID of the mapping that was generated and stored in the mapping repository. You may then retrieve any information about the resulting mapping directly from the mapping
	repository.

