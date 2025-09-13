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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun HomeScreen(
    navController: NavController
) {
    Scaffold(
        Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.navigationBars),
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {/*TODO*/}
                ) {
                    Icon(
                        painter = painterResource(R.drawable.side_app_drawer),
                        contentDescription = "Side app drawer",
                        modifier = Modifier.size(28.dp)
                    )

                }
                Text("Dashboard", fontSize = 21.sp,
                    fontWeight = FontWeight.Bold)
                IconButton(
                    onClick = {/*TODO*/}
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_notifications_24),
                        contentDescription = "Notifications",
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }, bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomBarItem(
                    icon = R.drawable.outline_home_24,
                    label = "Home",
                    isSelected = true,
                    onClick = {}
                )
                BottomBarItem(
                    icon = R.drawable.outline_view_list_24,
                    label = "Your Listings",
                    isSelected = false,
                    onClick = {}
                )
                BottomBarItem(
                    icon = R.drawable.outline_draft_orders_24,
                    label = "Orders",
                    isSelected = false,
                    onClick = {}
                )
                BottomBarItem(
                    icon = R.drawable.outline_mobile_chat_24,
                    label = "Smart Advisory",
                    isSelected = false,
                    onClick = {}
                )
                BottomBarItem(
                    icon = R.drawable.outline_person_24,
                    label = "Profile",
                    isSelected = false,
                    onClick = {}
                )

            }
        }
    ) {
        val iconList = listOf(
            R.drawable.fc1,
            R.drawable.fc2,
            R.drawable.fc3,
            R.drawable.fc4
        )
        val labelList = listOf(
            "My Listings",
            "Pending Orders",
            "Crop Advisory",
            "Disease Detection"
        )
        val summaryList = listOf(
            "Active Listings",
            "Recent Orders"
        )
        val summaryValueList = listOf(
            "2",
            "1"
        )
        Column(
            modifier = Modifier
                .padding(it)
                .padding(10.dp)
                .padding(top = 20.dp)
        ){
            Text(
                "Quick Actions",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
            ) {
                items(labelList.size){
                    FeatureCard(
                        iconRes = iconList[it],
                        title = labelList[it],
                        onClick = {}
                    )
                }
            }
            Text(
                "Summary",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(summaryList.size){
                    SummaryCard(
                        label = summaryList[it],
                        value = summaryValueList[it],
                        modifier = Modifier.width(150.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun BottomBarItem(icon: Int,
                  label: String,
                  isSelected: Boolean,
                  onClick: () -> Unit){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(icon),
                contentDescription = label,
                modifier = Modifier.size(24.dp),
                tint = if (isSelected) {
                    Color.Black
                } else {
                    Color.Gray
                }
            )
        }
        Text(label, color = if (isSelected) {Color.Black} else {Color.Gray})
    }
}

@Composable
fun FeatureCard(iconRes: Int,
                title: String,
                onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(0.5f),
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(iconRes),
                contentDescription = title,
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SummaryCard(label: String,
                value: String,
                modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .height(80.dp)
            .clip(RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                fontSize = 13.sp,
                color = Color.Gray
            )
            Text(
                text = value,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0B3D0B)
            )
        }
    }
}