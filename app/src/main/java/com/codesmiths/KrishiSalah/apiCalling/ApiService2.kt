package com.codesmiths.KrishiSalah.apiCalling

import com.codesmiths.krishisalah_buyer.models.FertiliserRequest
import com.codesmiths.krishisalah_buyer.models.FertiliserResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService2 {
    @POST("predict")
    suspend fun predictFertilizer(@Body request: FertiliserRequest): FertiliserResponse
}