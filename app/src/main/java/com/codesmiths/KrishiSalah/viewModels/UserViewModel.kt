package com.codesmiths.KrishiSalah.viewModels

import android.content.Context
import android.net.Uri

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codesmiths.KrishiSalah.apiCalling.ApiClient
import com.codesmiths.KrishiSalah.apiCalling.ApiClient2
import com.codesmiths.KrishiSalah.apiCalling.TokenManager
import com.codesmiths.KrishiSalah.models.DiseaseResponse
import com.codesmiths.KrishiSalah.models.LoginRequest
import com.codesmiths.KrishiSalah.models.LoginResponse
import com.codesmiths.KrishiSalah.models.Products
import com.codesmiths.KrishiSalah.models.ProductRequest
import com.codesmiths.KrishiSalah.models.ProductResponse
import com.codesmiths.KrishiSalah.models.SignUpRequest
import com.codesmiths.KrishiSalah.repository.UserRepository
import com.codesmiths.KrishiSalah.screens.SoilData
import com.codesmiths.KrishiSalah.screens.WeatherData
import com.codesmiths.krishisalah_buyer.models.FertiliserRequest
import com.codesmiths.krishisalah_buyer.models.FertiliserResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class UserViewModel(context: Context): ViewModel() {
    private val tokenManager=TokenManager.getInstance(context)
    private val apiService= ApiClient.create(context)
    private val apiService2=ApiClient2.create(context)
    private val repository=UserRepository(apiService,apiService2,tokenManager)

    private val _loginState= MutableStateFlow<Result<Boolean>>(Result.success(false))
    val loginState=_loginState.asStateFlow()

    private val _registerState= MutableStateFlow<Result<Boolean>>(Result.success(false))
    val registerState=_registerState.asStateFlow()

    private val _postProductState= MutableStateFlow<Result<Boolean>>(Result.success(false))
    val postProductState=_postProductState.asStateFlow()

    private val _getProductsList= MutableStateFlow<List<Products>>(emptyList())
    val getProductsList=_getProductsList.asStateFlow()

    private val _detectionState = MutableStateFlow<Result<DiseaseResponse>?>(null)
    val detectionState: StateFlow<Result<DiseaseResponse>?> = _detectionState

    private val _fertiliserResponse = MutableStateFlow<FertiliserResponse?>(null)
    val fertiliserResponse: StateFlow<FertiliserResponse?> = _fertiliserResponse

    fun login(loginRequest: LoginRequest){
        var response: LoginResponse
        viewModelScope.launch {
            response =repository.login(loginRequest)
            if (response.success){
                _loginState.value=Result.success(true)
            }else{
                _loginState.value=Result.failure(Exception("Login failed"))
            }
        }
    }
    fun signUp(signUpRequest: SignUpRequest){
        var response: LoginResponse
        viewModelScope.launch {
            response=repository.register(signUpRequest)
            if (response.success){
                _registerState.value=Result.success(true)
            }else {
                _registerState.value = Result.failure(Exception("Registration failed"))
            }
        }
    }
    fun addListing(productRequest: ProductRequest){
        var response: ProductResponse
        viewModelScope.launch {
            response = repository.postProduct(productRequest)
            if (response.success){
                _postProductState.value=Result.success(true)
            }else{
                _postProductState.value=Result.failure(Exception("Listing failed"))
            }
        }
    }
    fun getProducts(){
        viewModelScope.launch {
            _getProductsList.value=repository.getProducts().ProductList
        }
    }

    fun detectDisease(context: Context, uri: Uri) {
        viewModelScope.launch {
            try {
                val file = uriToFile(uri, context)
                val imagePart = prepareFilePart("image", file)

                val response = repository.detectDisease(imagePart)
                _detectionState.value = Result.success(response)
            } catch (e: Exception) {
                _detectionState.value = Result.failure(e)
            }
        }
    }

    private fun uriToFile(uri: Uri, context: Context): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val tempFile = File.createTempFile("leaf_", ".jpg", context.cacheDir)
        tempFile.outputStream().use { output ->
            inputStream?.copyTo(output)
        }
        return tempFile
    }

    private fun prepareFilePart(name: String, file: File): MultipartBody.Part {
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(name, file.name, requestFile)
    }
    fun clearDetectionState() {
        _detectionState.value = null
    }
    fun predictFertilizer(){
        viewModelScope.launch {
            _fertiliserResponse.value= repository.predictFertilizer(FertiliserRequest(
                31.0,62.0,49.0,"Black","Sugarcane",10,13,14
            ))
        }
    }
    fun getWeatherData(): WeatherData {
        return WeatherData(
            temperature = 31.0,
            moisture = 49.0
        )
    }
    fun getSoilData(): SoilData {
        return SoilData(
            n=10,
            k=13,
            p=14,
            moisture=49
        )
    }

}