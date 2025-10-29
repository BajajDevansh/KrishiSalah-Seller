package com.codesmiths.KrishiSalah.apiCalling

import com.codesmiths.KrishiSalah.models.DiseaseResponse
import com.codesmiths.KrishiSalah.models.LoginRequest
import com.codesmiths.KrishiSalah.models.LoginResponse
import com.codesmiths.KrishiSalah.models.ProductListResponse
import com.codesmiths.KrishiSalah.models.ProductRequest
import com.codesmiths.KrishiSalah.models.ProductResponse
import com.codesmiths.KrishiSalah.models.SignUpRequest
import okhttp3.MultipartBody

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart

import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService{
    @POST("user/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("user/register")
    suspend fun register(@Body registerRequest: SignUpRequest): LoginResponse

    @POST("user/productpost")
    suspend fun postProduct(@Body productRequest: ProductRequest): ProductResponse

    @GET("user/getProductListByUser")
    suspend fun getProducts(): ProductListResponse

    @Multipart
    @POST("plant/detect")
    suspend fun detectDisease(
        @Part image: MultipartBody.Part
    ): DiseaseResponse
}