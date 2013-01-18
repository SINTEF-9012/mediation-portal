## The Comparator Service

The **comparator service** let you compare of close are two given mappings, assuming one of them will be considered as correct (we later on name this correct mapping "*oracle*"). The comparator service aim at calculating overlap metrics such as number of true/false positives, and number of true/false negative. Given the ID of the oracle, and a list of mappings ID, the comparator service will evaluate how close are these mappings to the oracle, and populate the comparison respository accordingly.  In addition, you may retrieve higher level information such as precision, recall, accuracy, or f-measure from the comparison respository.


The comparator service is available at <http://my-portal/comparator> where `my-portal` stands for the running instance of the mediation portal that you intend to use.

The REST API of the comparator service is boiled down to one single operation:

 * `. (POST)` will trigger a series of evaluation against a given oracle. The content of this HTTP request should be formatted as the following JSON document:

		{
			"oracle": "my-oracle-uid",
			"toCompare": ["mapping1-uid", "mapping2-uid", "mapping3-uid"],
			"note": "This is a sample comparison request"
		}

	The effect of such a request is to trigger the evaluation of mapping to be compared, and for each of them, an comparison will be stored in the repository. For instance, following the above sample request, we can retrieve the detailed information of the comparison beteen our oracle and the mapping #1 at <http://my-portal/mediation/repostories/comparisons/my-oracle-id/mapping1-uid>