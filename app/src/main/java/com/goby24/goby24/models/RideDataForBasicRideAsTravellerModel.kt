package com.goby24.goby24.models

class RideDataForBasicRideAsTravellerModel {
    var id:Int = 0
    var additional_charges = ""
    var base_fare = ""
    var book_instantly:String = ""
    var booking:Int = 0
    var cancel_by = ""
    var currency = ""
    var date:String = ""
    var distance:String = ""
    var fare:Int = 0
    var is_payment_settled = false
    var is_rider_rated = false
    var is_traveller_rated = false
    var journeyTime:String = ""
    var payment_method:String = ""
    var payment_status = false
    var reason_for_cancellation = ""
    var refund_status = ""
    var rideStatus:String = ""
    var rider:RiderForBasicRideHistoryAsTravellerModel = RiderForBasicRideHistoryAsTravellerModel()
    var rider_confirm = false
    var route:ArrayList<String> = ArrayList()
    var route_id:Int = 0
    var route_obj:RouteObjModel = RouteObjModel()
    var seats:Int = 0
    var tax:String = ""
    var time:String = ""
    var total_fare:String = ""
    var user:Int = 0
}