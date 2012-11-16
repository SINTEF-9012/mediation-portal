  package dome {
  
  	data type DateTime
  	
  	data type Date
  
    class StayContainerGroup  {
      serviceGroupeCode: String 
      unitsNumber: String 
    }
    class ServiceGroup  {
      serviceGroupCode: String 
      serviceCode: String 
    }

    class Filter  {
      topElementCode: String 
      timeValue: DateTime [*]
      conditionToUse: String 
      sortingScheme: String 
      cancellationAction: String 
      id: String 
      dateValue: Date [*]
      elementCode: String 
      filterReference: String 
      stringValue: String [*]
    }

    class Distribution  {
      babiesNumber: String 
      vehiculeInformation: VehiculeInformation [*]
      childrenNumber: String 
      id: String 
      driverAge: String 
      adultNumber: String 
      unitNumber: String 
      elderNumber: String 
      childAge: String [*]
    }

    class BlockDistribution  {
      passengerId: String [*]
      id: String 
    }

    class HotelDisponibilityRequest  {
      propertyServiceCode: String [*]
      transactionId: String 
      initialDate: Date 
      serviceGroupCode: String 
      typeServiceCode: String 
      boardTypeCode: String [*]
      supplierCode: String 
      serviceCategoryCode: String [*]
      sessionID: String 
      featureModeCode: String [*]
      residentCodeDiscountRate: String 
      fieldFilter: Filter [*]
      aitaAirCarrierCode: String 
      changeCode: String [*]
      rateCode: String 
      serviceLocationCode: String 
      childrenNumber: String 
      maximumAmount: String 
      sortingConfiguration: Reservation [*]
      foodType: String 
      depthAlternativeLevel: String 
      confirmedServiceRequired: String 
      requireTranslation: String [*]
      groupSet: String [*]
      serviceLocationAverageValue: String 
      paginationIndex: String 
      serviceCode: String 
      minimumAmount: String 
      productSelection: ProductSelection 
      applicationMessageType: String 
      paymentType: String 
      adultNumber: String 
      serviceSubTypeCode: String 
      resultNumber: String 
      serviceName: String 
      serviceLocationValue: String 
      tourOperatorCode: String 
      distribution: Distribution [*]
      finalDate: Date 
      geographicAreaCode: String 
      groupService: ServiceGroup [*]
      checkCancellationCost: String 
    }

    class ProductSelection  {
      initialDate: Date 
      exitCode: String 
      serviceStayCode: String 
      rateCode: String 
      blockDistribution: BlockDistribution [*]
      itineraryCategoryCode: String 
      stayContainerGroup: StayContainerGroup [*]
      productCode: String 
      departureAirport: String 
    }

    class VehiculeInformation  {
      vehiculeRegistration: String 
      vehiculeType: String 
      vehiculeType2: String 
      id: String 
      vehiculeModel: String 
    }

    class Reservation  {
      sortingDirection: String 
      elementValue: String [*]
      usageCondition: String 
      elementCode: String 
    }
  }

