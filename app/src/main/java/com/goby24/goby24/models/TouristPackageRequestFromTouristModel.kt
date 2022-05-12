package com.goby24.goby24.models

class TouristPackageRequestFromTouristModel {
    var id:Int = 0
    var additional_charges = ""
    var cancel_by = ""
    var currency = ""
    var description = ""
    var is_paid = false
    var is_payment_settled = false
    var is_rider_rated = false
    var is_traveller_rated = false
    var journey_end_date = ""
    var journey_from = ""
    var journey_start_date = ""
    var journey_to = ""
    var new_tourist_spot = ""
    var no_of_passengers = ""
    var package_budget = ""
    var reason_for_cancellation = ""
    var refund_status = ""
    var request_status = ""
    var rider:RiderForTouristPackageRequestFromTouristModel = RiderForTouristPackageRequestFromTouristModel()
    var touristspot:SpotInTouristPackageModel = SpotInTouristPackageModel()
    var user:UserForTouristPackageRequestFromTouristModel = UserForTouristPackageRequestFromTouristModel()
}