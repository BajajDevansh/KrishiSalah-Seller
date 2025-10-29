package com.codesmiths.KrishiSalah.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.codesmiths.KrishiSalah.R
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedBottomItem by rememberSaveable { mutableStateOf("Home") }

    val bottomItems = listOf(
        BottomNavItem("Home", R.drawable.outline_home_24, "home"),
        BottomNavItem("Listings", R.drawable.outline_view_list_24, "listings"),
        BottomNavItem("Orders", R.drawable.outline_draft_orders_24, "orders"),
        BottomNavItem("Advisory", R.drawable.outline_mobile_chat_24, "advisory")
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                onItemSelected = { /* handle drawer clicks */ },
                onClose = { scope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopBar(onMenuClick = { scope.launch { drawerState.open() } })
            },
            bottomBar = {
                NavigationBar(
                    containerColor = Color.White,
                    tonalElevation = 4.dp
                ) {
                    bottomItems.forEach { item ->
                        NavigationBarItem(
                            selected = selectedBottomItem == item.label,
                            onClick = {
                                selectedBottomItem = item.label
                                if (item.route.isNotEmpty()) {
                                    navController.navigate(item.route)
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(item.icon),
                                    contentDescription = item.label
                                )
                            },
                            label = { Text(item.label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF008324),
                                selectedTextColor = Color(0xFF008324),
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray
                            )
                        )
                    }
                }
            }
        ) { padding ->
            DashboardContent(
                navController = navController,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Composable
fun TopBar(onMenuClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp).
            windowInsetsPadding(WindowInsets.statusBars),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onMenuClick) {
            Icon(
                painter = painterResource(R.drawable.side_app_drawer),
                contentDescription = "Menu",
                modifier = Modifier.size(28.dp)
            )
        }
        Text(
            "Dashboard",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        IconButton(onClick = { /* notifications */ }) {
            Icon(
                painter = painterResource(R.drawable.outline_notifications_24),
                contentDescription = "Notifications",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun AppDrawer(onItemSelected: (String) -> Unit, onClose: () -> Unit) {
    ModalDrawerSheet(modifier = Modifier.fillMaxWidth(0.7f)) {
        Spacer(modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars))
        Text(
            "More Options",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleMedium
        )
        NavigationDrawerItem(
            label = { Text("Profile") },
            selected = false,
            onClick = { onItemSelected("Profile"); onClose() },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") }
        )
        NavigationDrawerItem(
            label = { Text("Settings") },
            selected = false,
            onClick = { onItemSelected("Settings"); onClose() },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") }
        )
    }
}

@Composable
fun DashboardContent(navController: NavController, modifier: Modifier = Modifier) {
    val featureItems = listOf(
        Triple(R.drawable.fc1, "My Listings", "listings"),
        Triple(R.drawable.fc2, "Pending Orders", "orders"),
        Triple(R.drawable.fc4, "Fertiliser Advisory", "fertiliser_advisory"),
        Triple(R.drawable.fc3, "Disease Detection", "disease_detection")
    )
    val summaryItems = listOf("Active Listings" to "2", "Recent Orders" to "1")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Quick Actions", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))

        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.height(340.dp)) {
            items(featureItems.size) { index ->
                val (icon, label, route) = featureItems[index]
                FeatureCard(iconRes = icon, title = label) {
                    if (route.isNotEmpty()) navController.navigate(route)
                }
            }
        }

        Text(
            "Summary",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
        )
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(summaryItems.size) { index ->
                val (label, value) = summaryItems[index]
                SummaryCard(label = label, value = value, modifier = Modifier.padding(8.dp))
            }
        }
    }
}
@Composable
fun FeatureCard(
    iconRes: Int,
    title: String,
    onClick: () -> Unit) {
    ElevatedCard( modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(0.5f)
        .height(150.dp),
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) ) {
        Column( modifier = Modifier .fillMaxWidth()
            .padding(16.dp), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally ){
            Image( painter = painterResource(iconRes),
                contentDescription = title, modifier = Modifier.size(60.dp) )
            Spacer(modifier = Modifier.height(8.dp))
            Text( text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold )
        }
    }
}
@Composable
fun SummaryCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card( modifier = modifier
        .height(80.dp)
        .clip(RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) ) {
        Column( modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween ) {
            Text( text = label, fontSize = 13.sp, color = Color.Gray )
    Text( text = value,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF0B3D0B) )
        }
    }
}
data class BottomNavItem(val label: String, val icon: Int, val route: String)
