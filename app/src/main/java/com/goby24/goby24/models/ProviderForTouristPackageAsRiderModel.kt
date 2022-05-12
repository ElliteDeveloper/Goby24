package com.goby24.goby24.models

class ProviderForTouristPackageAsRiderModel() {
    var id:Int = 0
    var first_name:String = ""
    var last_name:String = ""
    var profile_pic:String = ""
    var dob:String = ""
    var rider_rating:String = ""
    var travel_preferences:TravelPreferencesModel = TravelPreferencesModel()
    var is_email_verified:Boolean = false
    var covid_vaccine_status:String = ""
    var is_mobile_no_verified:Boolean = false
    var joining_date:String = ""
}