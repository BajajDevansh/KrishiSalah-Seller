package com.codesmiths.KrishiSalah.repository

import com.codesmiths.KrishiSalah.apiCalling.ApiService
import com.codesmiths.KrishiSalah.apiCalling.ApiService2
import com.codesmiths.KrishiSalah.apiCalling.TokenManager
import com.codesmiths.KrishiSalah.models.DiseaseResponse

import com.codesmiths.KrishiSalah.models.LoginRequest
import com.codesmiths.KrishiSalah.models.LoginResponse
import com.codesmiths.KrishiSalah.models.ProductListResponse

import com.codesmiths.KrishiSalah.models.ProductRequest
import com.codesmiths.KrishiSalah.models.ProductResponse
import com.codesmiths.KrishiSalah.models.SignUpRequest
import com.codesmiths.krishisalah_buyer.models.FertiliserRequest
import com.codesmiths.krishisalah_buyer.models.FertiliserResponse
import okhttp3.MultipartBody

class UserRepository(private val apiService: ApiService,
                     private val apiService2: ApiService2,
                     private val tokenManager: TokenManager) {
    suspend fun login(loginRequest: LoginRequest): LoginResponse{
        val response=apiService.login(loginRequest)
        tokenManager.saveTokens(response.AccessToken,response.RefreshToken)
        return response
    }
    suspend fun register(registerRequest: SignUpRequest): LoginResponse{
        val response=apiService.register(registerRequest)
        tokenManager.saveTokens(response.AccessToken,response.RefreshToken)
        return response
    }
    suspend fun postProduct(productRequest: ProductRequest): ProductResponse{
        return apiService.postProduct(productRequest)
    }
    suspend fun getProducts(): ProductListResponse{
        return apiService.getProducts()
    }
    suspend fun detectDisease(
        image: MultipartBody.Part
    ): DiseaseResponse{
        return apiService.detectDisease(image)
    }
    suspend fun predictFertilizer(request: FertiliserRequest):FertiliserResponse{
        return apiService2.predictFertilizer(request)
    }
}