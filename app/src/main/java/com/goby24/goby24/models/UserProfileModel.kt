package com.goby24.goby24.models

class UserProfileModel {
    var id:Int = 0
    var is_rider:Boolean = false
    var mobile_no:String = ""
    var email:String = ""
    var first_name:String = ""
    var last_name:String = ""
    var profile_pic:String = ""
    var country:String = ""
    var city:String = ""
    var dob:String = ""
    var gender:String = ""
    var wallet_balance:Double = 0.0
    var joining_date:String = ""
    var rider_rating:String = "0.0"
    var about:String = ""
    var auth_provider:String = ""
    var is_email_verified:Boolean = false
    var covid_vaccine_status:String = ""
    var is_mobile_no_verified:Boolean = false
    var is_identity_verified:Boolean = false
    var identity_document:String = ""
    var identity_document_type:String = ""
    var is_vehicle_verified:Boolean = false
    lateinit var travel_preferences:TravelPreferencesModel
    var user_level:Int = 0
    lateinit var vehicle_info:VehicleInfoModel
}