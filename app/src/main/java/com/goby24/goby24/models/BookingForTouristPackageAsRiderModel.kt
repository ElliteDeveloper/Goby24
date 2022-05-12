package com.goby24.goby24.models

class BookingForTouristPackageAsRiderModel {
    var id:Int = 0
    var user = UserForTouristPackageAsRiderModel()
    var currency = ""
    var price = ""
    var booking_date = ""
    var arrival_date = ""
    var status = ""
    var is_rider_rated = false
    var is_traveller_rated = false
    var seats:Int = 1
    var base_fare = ""
    var additional_charges = ""
    var tax = ""
    var total_fare = ""
    var refund_status = ""
    var reason_for_cancellation = ""
    var cancel_by = ""
    var is_payment_settled = false
    var tourist_package = TouristPackageForTouristPackageAsRiderModel()
}