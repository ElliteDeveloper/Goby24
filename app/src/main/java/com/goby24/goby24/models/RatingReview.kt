package com.goby24.goby24.models

import com.goby24.goby24.R

class RatingReview(strName:String, strLevel:String, strReview:String) {
    var mDrawableID:Int = R.drawable.dummy_avatar
    var mStrName:String = strName
    var mStrLevel:String = strLevel
    var mStrReview:String = strReview
    var m_fBookride:Float = 5.0F
    var m_fProfessionalism:Float = 1.0F
    var m_fDriving:Float = 2.0F
    var m_fComfort:Float = 3.0F
    var m_fCleanliness:Float = 4.0F
    var m_fMusic:Float = 4.0F
    var m_fCarSmell:Float = 5.0F
    var m_fConversation:Float = 1.0F
    var m_fPickup:Float = 2.0F
}