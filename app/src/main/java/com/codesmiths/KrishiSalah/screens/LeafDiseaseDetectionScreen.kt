package com.codesmiths.KrishiSalah.screens

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.codesmiths.KrishiSalah.viewModels.UserViewModel

@Composable
fun LeafDiseaseDetectionScreen(
    viewModel: UserViewModel,
) {
    val context = LocalContext.current
    val detectionState by viewModel.detectionState.collectAsState()

    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    var isDetecting by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .windowInsetsPadding(WindowInsets.statusBars),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "🌿 Leaf Disease Detection",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        if (selectedUri == null) {
            Box(
                modifier = Modifier
                    .size(220.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFE8EAF6)),
                contentAlignment = Alignment.Center
            ) {
                Text("No Image Selected", color = Color.Gray)
            }
        }
        // Select Image Button
        if (selectedUri == null && !isDetecting) {
            Button(onClick = { galleryLauncher.launch("image/*") }) {
                Text("Select Image from Gallery")
            }
        }

        // Show selected image
        selectedUri?.let {
            AsyncImage(
                model = it,
                contentDescription = "Selected Leaf",
                modifier = Modifier
                    .size(220.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )

            if (!isDetecting && detectionState == null) {
                Button(
                    onClick = {
                        isDetecting = true
                        viewModel.detectDisease(context, it)
                    }
                ) {
                    Text("Detect Disease")
                }
            }
        }

        // Loader while detection is running
        if (isDetecting && detectionState == null) {
            CircularProgressIndicator()
        }

        // Result after detection
        detectionState?.let { state ->
            isDetecting = false
            Spacer(modifier = Modifier.height(12.dp))
            if (state.isSuccess) {
                val result = state.getOrNull()
                result?.let {
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(20.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "✅ Disease Detected",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF1B5E20)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                it.prediction[0].label,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFD32F2F)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Cure & Medications Button
                    Button(
                        onClick = {
                            // TODO
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF008324) // app_green
                        )
                    ) {
                        Text("💊 Cure & Medications")
                    }
                }
            } else {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFFFFEBEE))
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "⚠️ Error",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFD32F2F)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            state.exceptionOrNull()?.message ?: "Unknown error",
                            color = Color.Black
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    selectedUri = null
                    viewModel.clearDetectionState()
                }
            ) {
                Text("Detect Another Disease")
            }
        }
    }
}


