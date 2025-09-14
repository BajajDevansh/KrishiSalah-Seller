package com.codesmiths.KrishiSalah.apiCalling

import com.codesmiths.KrishiSalah.models.LoginRequest
import com.codesmiths.KrishiSalah.models.LoginResponse
import com.codesmiths.KrishiSalah.models.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService{
    @POST("user/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("user/register")
    suspend fun register(@Body registerRequest: SignUpRequest): LoginResponse

}