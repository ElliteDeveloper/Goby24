package com.goby24.goby24.models

class ProviderInTouristPackageModel() {
    var id:Int = 0
    var password:String=""
    var last_login:String=""
    var is_superuser = false
    var is_rider = false
    var mobile_no = ""
    var email = ""
    var first_name = ""
    var last_name = ""
    var profile_pic = ""
    var country = ""
    var city = ""
    var dob = ""
    var gender = ""
    var joining_date = ""
    var rider_rating = ""
    var about = ""
    var auth_provider = ""
    var is_staff = false
    var is_active = false
    var is_email_verified = false
    var is_mobile_no_verified = false
    var is_identity_verified = false
    var identity_document = ""
    var identity_document_type = ""
    var is_vehicle_verified = false
    var covid_vaccine_status = ""
    var is_payment_method_verified = false
    var firebase_id = ""
    var travel_preferences = TravelPreferencesModel()
    var is_smoking_allowed = false
    var is_pet_allowed = false
    var user_level:Int = 0
    var average_rating_as_rider = ""
    var average_rating_as_traveller = ""
    var groups = ArrayList<GroupInTouristPackage>()
    var user_permissions = ArrayList<UserPermissionsInTouristPackageModel>()
}