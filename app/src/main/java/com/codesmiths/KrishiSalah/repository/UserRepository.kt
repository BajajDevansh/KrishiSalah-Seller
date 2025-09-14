package com.codesmiths.KrishiSalah.repository

import com.codesmiths.KrishiSalah.apiCalling.ApiService
import com.codesmiths.KrishiSalah.apiCalling.TokenManager
import com.codesmiths.KrishiSalah.models.LoginRequest
import com.codesmiths.KrishiSalah.models.LoginResponse
import com.codesmiths.KrishiSalah.models.SignUpRequest

class UserRepository(private val apiService: ApiService,
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
}