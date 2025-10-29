package com.codesmiths.KrishiSalah.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.codesmiths.KrishiSalah.R
import kotlinx.coroutines.launch

@Composable
fun OrderScreen(
    navController: NavController
){
    val bottomItems = listOf(
        BottomNavItem("Home", R.drawable.outline_home_24, "home"),
        BottomNavItem("Listings", R.drawable.outline_view_list_24, "listings"),
        BottomNavItem("Orders", R.drawable.outline_draft_orders_24, "orders"),
        BottomNavItem("Advisory", R.drawable.outline_mobile_chat_24, "advisory")
    )
    var selectedBottomItem by rememberSaveable { mutableStateOf("Orders") }
    val drawerState= rememberDrawerState(DrawerValue.Closed)
    val scope= rememberCoroutineScope()
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
            modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
            topBar = {
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
                        "Orders Received", fontSize = 21.sp,
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
        ) {
            Modifier.padding(it)
        }
    }
}

@Composable
fun OrderItem(
    img:Int,
    name:String,
    variety:String,
    onclick:()-> Unit
){
    ElevatedCard(
        onClick = onclick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(img),
                contentDescription = name,
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.padding(top=10.dp),
                verticalArrangement = Arrangement.Center,

                ) {
                Text(name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(variety, fontSize = 16.sp, color = Color.Gray)
            }
        }
    }
}
