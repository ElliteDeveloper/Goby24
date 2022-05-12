package com.goby24.goby24.models

class RideDataForBasicRideAsRiderModel {
    var id:Int = 0
    var route:ArrayList<String> = ArrayList()
    var date:String = ""
    var time:String = ""
    var rideStatus:String = ""
    var distance:String = ""
    var journeyTime:String = ""
    var fare:String = ""
    var currency:String = ""
    var payment_method:String = ""
    var book_instantly:String = ""
    var rider:RiderForBasicRideHistoryAsRiderModel = RiderForBasicRideHistoryAsRiderModel()
    var booking_ids:ArrayList<Int> = ArrayList()
    var travellers:ArrayList<TravellerForBasicRideAsRiderModel> = ArrayList()
}