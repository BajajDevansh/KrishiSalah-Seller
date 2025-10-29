package com.codesmiths.krishisalah_buyer.models

data class FertiliserRequest(
    val Temparature:Double,
    val Humidity:Double,
    val Moisture:Double,
    val Soil_Type:String,
    val Crop_Type:String,
    val Nitrogen: Int,
    val Potassium: Int,
    val Phosphorous: Int
)
data class FertiliserResponse(
    val predicted_fertilizer:String
)
