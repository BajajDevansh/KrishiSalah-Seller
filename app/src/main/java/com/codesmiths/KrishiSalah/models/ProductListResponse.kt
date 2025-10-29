package com.codesmiths.KrishiSalah.models

data class ProductListResponse(
    val success: Boolean,
    val message: String,
    val ProductList: List<Products>
)
