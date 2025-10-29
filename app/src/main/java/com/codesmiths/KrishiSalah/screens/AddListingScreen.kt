package com.codesmiths.KrishiSalah.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.io.ByteArrayOutputStream
import android.util.Base64
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.codesmiths.KrishiSalah.R
import com.codesmiths.KrishiSalah.models.Location
import com.codesmiths.KrishiSalah.models.ProductRequest
import com.codesmiths.KrishiSalah.viewModels.UserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddListingPage(
    viewModel: UserViewModel,
    navController: NavController
) {
    val name = remember { mutableStateOf("") }
    val category = remember { mutableStateOf("Grains") }
    val description = remember { mutableStateOf("") }
    val quantity = remember { mutableStateOf("") }
    val unit = remember { mutableStateOf("kg") }
    val pricePerUnit = remember { mutableStateOf("") }
    val images = remember { mutableStateListOf<String>() }

    val village = remember { mutableStateOf("") }
    val district = remember { mutableStateOf("") }
    val state = remember { mutableStateOf("") }
    val pincode = remember { mutableStateOf("") }

    val categories = listOf("Grains","Vegetables","Fruits","Dairy","Poultry","Livestock","Other")
    val units = listOf("kg","litre","dozen","quintal","piece")

    var expandedCategory by remember { mutableStateOf(false) }
    var expandedUnit by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(

        drawerState=drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.fillMaxWidth(0.5f)) {
                Spacer(modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars))
                Text("More Options")
                NavigationDrawerItem(
                    label = { Text("Item 1") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
                NavigationDrawerItem(
                    label = { Text("Item 2") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.side_app_drawer),
                                    contentDescription = "Side app drawer",
                                    modifier = Modifier.size(28.dp)
                                )

                            }
                            Text(
                                "Add Listings", fontSize = 21.sp,
                                fontWeight = FontWeight.Bold
                            )
                            IconButton(
                                onClick = {/*TODO*/ }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.outline_notifications_24),
                                    contentDescription = "Notifications",
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Name
                OutlinedTextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                // Category Dropdown
                ExposedDropdownMenuBox(
                    expanded = expandedCategory,
                    onExpandedChange = { expandedCategory = !expandedCategory }
                ) {
                    OutlinedTextField(
                        value = category.value,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Category") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCategory) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedCategory,
                        onDismissRequest = { expandedCategory = false }
                    ) {
                        categories.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    category.value = option
                                    expandedCategory = false
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Description
                OutlinedTextField(
                    value = description.value,
                    onValueChange = { description.value = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 4
                )

                Spacer(Modifier.height(12.dp))

                // Quantity + Unit
                Row(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = quantity.value,
                        onValueChange = { quantity.value = it },
                        label = { Text("Qty") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Spacer(Modifier.width(12.dp))

                    ExposedDropdownMenuBox(
                        expanded = expandedUnit,
                        onExpandedChange = { expandedUnit = !expandedUnit }
                    ) {
                        OutlinedTextField(
                            value = unit.value,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Unit") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedUnit) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(0.5f)
                                .weight(1f)
                        )
                        ExposedDropdownMenu(
                            expanded = expandedUnit,
                            onDismissRequest = { expandedUnit = false }
                        ) {
                            units.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        unit.value = option
                                        expandedUnit = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Price per Unit
                OutlinedTextField(
                    value = pricePerUnit.value,
                    onValueChange = { pricePerUnit.value = it },
                    label = { Text("Price per Unit") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                Spacer(Modifier.height(12.dp))

                // Images Picker Placeholder
                Text("Images", style = MaterialTheme.typography.titleMedium)
                MultiImageCapture(images)

                Spacer(Modifier.height(12.dp))

                // Location Fields
                Text("Location", style = MaterialTheme.typography.titleMedium)
                OutlinedTextField(
                    value = village.value,
                    onValueChange = { village.value = it },
                    label = { Text("Village") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = district.value,
                    onValueChange = { district.value = it },
                    label = { Text("District") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = state.value,
                    onValueChange = { state.value = it },
                    label = { Text("State") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = pincode.value,
                    onValueChange = { pincode.value = it },
                    label = { Text("Pincode") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(Modifier.height(20.dp))

                // Submit Button
                Button(
                    onClick = {
                        viewModel.addListing(
                            ProductRequest(
                                name = name.value,
                                category = category.value,
                                description = description.value,
                                quantity = quantity.value.toInt(),
                                unit = unit.value,
                                pricePerUnit = pricePerUnit.value.toDouble(),
                                images = images,
                                location = Location(
                                    village = village.value,
                                    district = district.value,
                                    state = state.value,
                                    pincode = pincode.value
                                ),
                                currentQuantity = quantity.value.toInt(),
                                availability = true

                            )
                        )
                        if(viewModel.postProductState.value.isSuccess) {
                            navController.navigate("listings")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008324)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Submit", color = Color.White)
                }
            }
        }
    }
}
@Composable
fun MultiImageCapture(
    images: SnapshotStateList<String> // list of Base64 images
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            val base64 = bitmapToBase64(bitmap)
            images.add(base64) // store in list
        }
    }

    Column {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Show captured images
            images.forEach { base64 ->
                val byteArray = Base64.decode(base64, Base64.DEFAULT)
                val bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

                Image(
                    bitmap = bmp.asImageBitmap(),
                    contentDescription = "Captured Image",
                    modifier = Modifier
                        .size(70.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            // Add photo button
            IconButton(
                onClick = { launcher.launch(null) },
                modifier = Modifier
                    .size(70.dp)
                    .background(Color(0xFF008324), RoundedCornerShape(8.dp))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Take Photo",
                    tint = Color.White
                )
            }
        }
    }
}
fun bitmapToBase64(bitmap: Bitmap): String {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
    val byteArray = outputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}
