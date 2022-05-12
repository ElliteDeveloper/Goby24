package com.goby24.goby24.models

class RideDataForTouristPackageAsTravellerModel {
    var id:Int = 0
    var user:UserForTouristPackageAsTravellerModel = UserForTouristPackageAsTravellerModel()
    var currency:String = ""
    var provider:ProviderForTouristPackageAsTravellerModel = ProviderForTouristPackageAsTravellerModel()
    var price:String = ""
    var booking_date:String = ""
    var arrival_date:String = ""
    var status:String = ""
    var is_rider_rated = false
    var is_traveller_rated = false
    var seats:String=""
    var base_fare:String=""
    var additional_charges:String=""
    var tax:String=""
    var total_fare:String=""
    var refund_status:String=""
    var reason_for_cancellation:String=""
    var cancel_by:String=""
    var is_payment_settled:Boolean = false
    var tourist_package:TouristPackageForTouristPackageAsTravellerModel = TouristPackageForTouristPackageAsTravellerModel()
}