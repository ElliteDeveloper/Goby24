package com.goby24.goby24.models

class RideDataForTouristPackageRequestAsRiderModel {
    var id:Int = 0
    var additional_charges:String=""
    var cancel_by:String=""
    var currency:String = ""
    var description:String = ""
    var is_paid = false
    var is_payment_settled:Boolean = false
    var is_rider_rated = false
    var is_traveller_rated = false
    var journey_end_date:String = ""
    var journey_from:String = ""
    var journey_start_date:String = ""
    var journey_to:String = ""
    var new_tourist_spot:String = ""
    var no_of_passengers:Int = 0
    var package_budget = ""
    var reason_for_cancellation = ""
    var refund_status = ""
    var request_status = ""
    var rider:RiderForTouristPackageRequestAsRiderModel = RiderForTouristPackageRequestAsRiderModel()
    var touristspot:TouristSpotForTouristPackageRequestAsRiderModel = TouristSpotForTouristPackageRequestAsRiderModel()
    var user:UserForTouristPackageRequestAsRiderModel = UserForTouristPackageRequestAsRiderModel()
}