package com.goby24.goby24.models

class TouristPackageOfferModel {
    var id:Int = 0
    var booked_seats:Int = 0
    var bookings:ArrayList<BookingForTouristPackageAsRiderModel> = ArrayList()
    var city = ""
    var country = ""
    var currency = ""
    var date_from = ""
    var date_to = ""
    var is_full = false
    var is_guide = false
    var new_tourist_spot = ""
    var no_of_days:Int = 0
    var no_of_tourists:Int = 0
    var package_banner = ""
    var package_brief = ""
    var package_cover = ""
    var package_name = ""
    var package_price = ""
    var package_status = ""
    var payment_status = false
    var provider:ProviderForTouristPackageOfferModel = ProviderForTouristPackageOfferModel()
    var reason_for_cancellation = ""
    var refund_status = ""
    var remaining_seats = 0
    var spot:SpotInTouristPackageModel = SpotInTouristPackageModel()
    var time_from = ""
    var time_to = ""
    var travellers:ArrayList<TravellerForTouristPackageOfferModel> = ArrayList()
    var vehicle_info:VehicleInfoModel = VehicleInfoModel()
}