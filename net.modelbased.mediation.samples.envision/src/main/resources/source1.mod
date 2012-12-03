#  SOS Xsd
package tns {  

	class ObservationType {   
		metadata: Any  
		samplingTime: Any  
		resultTime: Any  
		procedure: Any  
		resultQuality: Any  
		observedProperty: Any  
		featureOfInterest: Any  
		parameter: Any  
		result: Any 
	} 
	
	class Schema {  
		Observation: tns.ObservationType 
	} 

}