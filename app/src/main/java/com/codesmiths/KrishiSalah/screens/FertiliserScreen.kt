package com.codesmiths.KrishiSalah.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.codesmiths.KrishiSalah.viewModels.UserViewModel

@Composable
fun FertilizerPredictionScreen(viewModel: UserViewModel) {
    var soilData by remember { mutableStateOf<SoilData?>(null) }
    var weatherData by remember { mutableStateOf<WeatherData?>(null) }
    var showPredictionDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var predictedFertilizer by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "🌱 Fertilizer Prediction",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        // Step 1: Fetch Soil Data
        if (soilData == null) {
            Button(onClick = {
                soilData = viewModel.getSoilData()
            }) {
                Text("Get Soil Data from Sensors")
            }
        } else {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Soil Data", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text("Nitrogen (N): ${soilData!!.n}")
                    Text("Phosphorus (P): ${soilData!!.p}")
                    Text("Potassium (K): ${soilData!!.k}")
                    Text("Moisture: ${soilData!!.moisture}%")
                }
            }

            // Step 2: Fetch Weather Data
            if (weatherData == null) {
                Button(onClick = {
                    weatherData = viewModel.getWeatherData()
                }) {
                    Text("Get Weather Data")
                }
            } else {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Weather Data", fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Text("Temperature: ${weatherData!!.temperature} °C")
                        Text("Moisture: ${weatherData!!.moisture} ")
                    }
                }

                // Step 3: Prediction Button
                Button(onClick = { showPredictionDialog = true }) {
                    Text("Get Fertilizer Prediction")
                }
            }
        }
    }

    // Step 4: Crop Name Dialog
    if (showPredictionDialog) {
        var cropName by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showPredictionDialog = false },
            title = { Text("Enter Crop Name") },
            text = {
                Column {
                    OutlinedTextField(
                        value = cropName,
                        onValueChange = { cropName = it },
                        label = { Text("Crop Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (cropName.isNotBlank()) {
                        isLoading = true
                        // simulate API call
                        viewModel.predictFertilizer()
                        predictedFertilizer = viewModel.fertiliserResponse.value?.predicted_fertilizer
                        showPredictionDialog = false
                        isLoading = false
                    }
                }) {
                    Text("Submit")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPredictionDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Step 5: Loader
    if (isLoading) {
        Dialog(onDismissRequest = {}) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.White, shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }

    // Step 6: Prediction Result Dialog
    predictedFertilizer?.let { fertilizer ->
        AlertDialog(
            onDismissRequest = { predictedFertilizer = null },
            title = { Text("✅ Predicted Fertilizer") },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        fertilizer,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF008324)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { /* navigate to shop screen */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008324))
                ) {
                    Text("Buy Now", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { predictedFertilizer = null }) {
                    Text("Close")
                }
            }
        )
    }
}



data class SoilData(val n: Int, val p: Int, val k: Int, val moisture: Int)
data class WeatherData(val temperature: Double, val moisture: Double)