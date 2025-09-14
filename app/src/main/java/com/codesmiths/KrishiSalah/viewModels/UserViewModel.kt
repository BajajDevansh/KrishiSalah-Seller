package com.codesmiths.KrishiSalah.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codesmiths.KrishiSalah.apiCalling.ApiClient
import com.codesmiths.KrishiSalah.apiCalling.TokenManager
import com.codesmiths.KrishiSalah.models.LoginRequest
import com.codesmiths.KrishiSalah.models.LoginResponse
import com.codesmiths.KrishiSalah.models.SignUpRequest
import com.codesmiths.KrishiSalah.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(context: Context): ViewModel() {
    private val tokenManager=TokenManager.getInstance(context)
    private val apiService= ApiClient.create(context)
    private val repository=UserRepository(apiService,tokenManager)
    private val _loginState= MutableStateFlow<Result<Boolean>>(Result.success(false))
    val loginState=_loginState.asStateFlow()
    private val _registerState= MutableStateFlow<Result<Boolean>>(Result.success(false))
    val registerState=_registerState.asStateFlow()
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
}