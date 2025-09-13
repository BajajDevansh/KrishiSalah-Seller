package com.codesmiths.KrishiSalah.models

data class ProductRequest(
    val name: String,
    val category: String,
    val description: String,
    val quantity: Int,
    val currentQuantity: Int,
    val unit: String,
    val pricePerUnit: Double,
    val images: List<String>,
    val location:Location,
    val availability: Boolean,

)
