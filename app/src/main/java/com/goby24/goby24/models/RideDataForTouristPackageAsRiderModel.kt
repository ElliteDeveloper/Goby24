package com.goby24.goby24.models

class RideDataForTouristPackageAsRiderModel {
    var id:Int = 0
    var provider:ProviderForTouristPackageAsRiderModel = ProviderForTouristPackageAsRiderModel()
    var travellers:ArrayList<TravellerForTouristPackageAsRiderModel> = ArrayList()
    var bookings:ArrayList<BookingForTouristPackageAsRiderModel> = ArrayList()
    var vehicle_info:VehicleForTouristPackageAsRiderModel = VehicleForTouristPackageAsRiderModel()
    var booked_seats:Int = 0
    var remaining_seats:Int = 0
    var new_tourist_spot = ""
    var is_guide = false
    var package_name = ""
    var package_banner = ""
    var package_cover = ""
    var package_brief = ""
    var package_price = ""
    var no_of_days:Int = 0
    var date_from = ""
    var date_to = ""
    var time_from = ""
    var city = ""
    var time_to = ""
    var no_of_tourist:Int = 0
    var country = ""
    var currency = ""
    var payment_status = false
    var refund_status = ""
    var package_status = ""
    var reason_for_cancellation = ""
    var is_full = false
    var spot:SpotInTouristPackageModel = SpotInTouristPackageModel()
}