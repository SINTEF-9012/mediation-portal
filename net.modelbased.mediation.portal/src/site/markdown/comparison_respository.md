## The Comparison Respository

The comparison repository stores comparison between mappings. This service help evaluating the effectiveness of mediation algorithms by comparing the resulting mapping with mapping that are known to be correct. Such correct mapping, so called *oracles*, can be stored in the mapping repository and retriived by the comparator service. 

The comparison respository is available at <http://my-portal/mediation/repositories/comparisons> where `my-portal` is the instance or the Mediation portal you intend to use. This page describes how to use the comparison respository through its REST interface.

The REST API of the comparison repository includes the following operations:

 - `. (GET)` returns all the comparisons stored in the comparisons repository.

 - `. (POST)` push a new comparison in the respository. The comparison is expected to be formatted as the following JSON document, where `tp` stands for "true positives", `tn` for "true negative", `fp` for "false positive", and `fn` for "false negative".

		{	"oracle": "my-oracle-id",
			"mapping": "my-mapping-id",
			"tp": "45",
			"tn": "34",
			"fp": "10",
			"fn": "7"	}

 - `./my-oracle-id (GET)` returns all the comparisons with the given oracle (expected to be a mapping stored in the mapping repository).

 - `./my-oracle-id (PUT)` add a list of new comparisons with the given oracle (expected to be a mapping stored in the mapping repository). The list of new comparisons should be formatted as the following JSON snippet:

		[ 
			{	"oracle": "my-oracle-id",
				"mapping": "my-mapping-01",
				"tp": "45",
				"tn": "34",
				"fp": "10",
				"fn": "7"	},
			{	"oracle": "my-oracle-id",
				"mapping": "my-mapping-02",
				"tp": "56",
				"tn": "78",
				"fp": "10",
				"fn": "98"	}
		]

 - `./my-oracle-id/my-mapping-id (GET)` returns the comparison between the oracle whose UID is `my-oracle-id` and the mapping whose UID is `my-mapping-id`. 

 - `./my-oracle-id/my-mapping-id (PUT)` update the comparison between the oracle whose UID is `my-oracle-id` and the mapping whose UID is `my-mapping-id`. The comparison is expected to be formatted as the following JSON document:

		{	"oracle": "my-oracle-id",
			"mapping": "my-mapping-id",
			"tp": "45",
			"tn": "34",
			"fp": "10",
			"fn": "7"	}

 - `./my-oracle-id/my-mapping-id/stats (GET)` returns statistics including, the precision, the recall, the accuracy and the f-measure. 
