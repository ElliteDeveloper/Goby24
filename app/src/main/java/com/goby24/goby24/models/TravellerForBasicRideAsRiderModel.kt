package com.goby24.goby24.models

class TravellerForBasicRideAsRiderModel() {
    var id:Int = 0
    var last_login:String = ""
    var is_rider:Boolean = false
    var mobile_no:String=""
    var email:String = ""
    var first_name:String = ""
    var last_name:String = ""
    var profile_pic:String = ""
    var country:String=""
    var city:String = ""
    var dob:String = ""
    var gender:String = ""
    var joining_date:String = ""
    var rider_rating:String = ""
    var about:String=""
    var auth_provider:String = ""
    var is_active:Boolean = false
    var is_email_verified:Boolean = false
    var is_mobile_no_verified:Boolean = false
    var is_identity_verified:Boolean = false
    var identity_document:String = ""
    var identity_document_type:String = ""
    var is_vehicle_verified:Boolean = false
    var covid_vaccine_status:String = ""
    var is_payment_method_verified:Boolean = false
    var firebase_device_id:String=""
    var travel_preferences:TravelPreferencesModel = TravelPreferencesModel()
    var is_smoking_allowed=false
    var is_pet_allowed=false
    var user_level=0
    var average_rating_as_rider = ""
    var average_rating_as_traveller=""
    var seats_booked:Int=0
    var is_rider_rated = false
    var is_traveller_rated = false
}