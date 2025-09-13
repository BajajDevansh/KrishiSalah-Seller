package com.codesmiths.KrishiSalah

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.codesmiths.KrishiSalah.ui.theme.KrishiSalahTheme
//import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KrishiSalahTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   Navigation(
                       Modifier.padding(innerPadding)
                   )
                }
            }
        }
    }
}
@Composable
fun Navigation(modifier: Modifier= Modifier){
    val navController=rememberNavController()
    NavHost(navController = navController, startDestination = "welcome"){
        composable("welcome") {

        }
        composable("login"){

        }
        composable("signup"){

        }
        composable("home"){

        }
        composable("listings"){

        }
    }
}