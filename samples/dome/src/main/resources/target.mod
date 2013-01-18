  
  package ota {
  
  	data type Date
  
    class Source  {
      requestorID: RequestorID 
    }
    
    data type IDType
    
    class GuestCount  {
      ageQualifyingCode: String 
      count: CountType 
      age: AgeType 
    }
  
    class Criterion  {
      roomStayCandidates: RoomStayCandidates 
      stayDateRange: StayDateRange 
      ratePlanCandidates: RatePlanCandidates 
      hotelRef: HotelRef [*]
    }
  
    class MealsIncluded  {
      dinner: Boolean 
      breakfast: Boolean 
      lunch: Boolean 
      mealPlanCodes: String 
    }
  
    data type RoomTypeCodeType
  
    class AvailRequestSegment  {
      availReqType: String 
      hotelSearchCriteria: HotelSearchCriteria 
    }
  
    class RoomStayCandidate  {
      rPH: Integer 
      roomTypeCode: RoomTypeCodeType 
      roomViewCode: String 
      roomClassificationCode: String 
      roomCategory: String 
      guestCounts: GuestCounts 
      ratePlanCandidateRPH: Integer 
    }
    
    class RatePlanCandidate  {
      ratePlanCode: RatePlanCodeType 
      ratePlanType: String 
      rPH: Integer 
      mealsIncluded: MealsIncluded 
    }
    
    class GuestCounts  {
      isPerRoom: Boolean 
      guestCount: GuestCount [*]
    }
    
    class POS  {
      source: Source 
    }
    
    class OTAHotelAvailRQ  {
      echoToken: EchoTokenType 
      availRequestSegments: AvailRequestSegments 
      pOS: POS 
      primaryLangID: String 
    }
    
    class StayDateRange  {
      end: Date 
      start: Date 
    }
    
    data type HotelCityCodeType
    
    class RatePlanCandidates  {
      ratePlanCandidate: RatePlanCandidate [*]
    }
    
    class HotelRef  {
      areaID: AreaIDType 
      hotelCityCode: HotelCityCodeType 
      hotelCode: HotelCodeType 
    }
    
    data type HotelCodeType
    
    data type EchoTokenType
    
    data type RatePlanCodeType
    
    class HotelSearchCriteria  {
      criterion: Criterion [*]
      availableOnlyIndicator: Boolean 
    }
    
    data type CountType
    
    data type AgeType
    
    class AvailRequestSegments  {
      availRequestSegment: AvailRequestSegment [*]
    }
    
    class RequestorID  {
      iD: IDType 
      messagePassword: MessagePasswordType 
    }
    
    data type AreaIDType
    
    class RoomStayCandidates  {
      roomStayCandidate: RoomStayCandidate [*]
    }

    data type MessagePasswordType

  }