package com.codesmiths.KrishiSalah

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codesmiths.KrishiSalah.screens.AddListingPage
import com.codesmiths.KrishiSalah.screens.FertilizerPredictionScreen
import com.codesmiths.KrishiSalah.screens.HomeScreen
import com.codesmiths.KrishiSalah.screens.LeafDiseaseDetectionScreen

import com.codesmiths.KrishiSalah.screens.ListingsScreen
import com.codesmiths.KrishiSalah.screens.LoginScreen
import com.codesmiths.KrishiSalah.screens.OrderScreen
import com.codesmiths.KrishiSalah.screens.SignUpScreen
import com.codesmiths.KrishiSalah.screens.WelcomeScreen

import com.codesmiths.KrishiSalah.ui.theme.KrishiSalahTheme
import com.codesmiths.KrishiSalah.viewModels.UserViewModel

class MainActivity : ComponentActivity() {
    lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val factory = UserViewModelFactory(applicationContext)
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
        setContent {

            KrishiSalahTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(Modifier.padding(innerPadding),viewModel)
                }
            }
        }
    }
}


class UserViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
fun Navigation(modifier: Modifier= Modifier,viewModel: UserViewModel){

    val navController=rememberNavController()

    NavHost(navController = navController, startDestination = "welcome"){
        composable("welcome") {
            WelcomeScreen(navController)
        }
        composable("login"){
            LoginScreen(navController,viewModel)
        }
        composable("signup"){
            SignUpScreen(navController,viewModel)
        }
        composable("home"){
            HomeScreen(navController)
        }
        composable("listings"){
            ListingsScreen(navController,viewModel)
        }
        composable("add_listings") {
            AddListingPage(viewModel, navController)
        }
        composable("orders") {
            OrderScreen(navController)
        }
        composable("disease_detection") {
            LeafDiseaseDetectionScreen(viewModel)
        }
        composable("fertiliser_advisory") {
            FertilizerPredictionScreen(viewModel)
        }
    }
}