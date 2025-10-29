package com.codesmiths.KrishiSalah.models

data class DiseaseResponse(
    val cloudinaryUrl: String,
    val prediction: List<Prediction>
)
data class Prediction(
    val label: String,
    val score: Double
)