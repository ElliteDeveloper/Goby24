package com.goby24.goby24.models

class RideDataForRideRequestAsRiderModel {
    var id:Int = 0
    var date:String = ""
    var fare:String = ""
    var paymentMethod:String = ""
    var requestStatus:String = ""
    var route:ArrayList<String> = ArrayList()
    var time:String = ""
    var traveller:TravellerForRideRequestAsRiderModel = TravellerForRideRequestAsRiderModel()
    var rider:RiderForRideRequestAsRiderModel = RiderForRideRequestAsRiderModel()
    var country:String=""
    var journey_from:String=""
    var journey_to:String=""
    var journey_date:String=""
    var pickup_time:String=""
    var seats:String=""
    var budget:String=""
    var additional_charges:String=""
    var payment_method:String=""
    var currency:String = ""
    var request_status:String = ""
    var payment_status:Boolean = false
    var refund_status:String = ""
    var remark:String = ""
    var is_rider_rated = false
    var is_traveller_rated = false
    var timestamp:String=""
    var reason_for_cancellation:String=""
    var cancel_by:String=""
    var is_payment_settled:String=""
    var user:Int = 0
}