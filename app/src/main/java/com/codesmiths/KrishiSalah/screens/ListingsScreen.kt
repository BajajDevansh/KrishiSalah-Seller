package com.codesmiths.KrishiSalah.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codesmiths.KrishiSalah.R

@Composable
@Preview(showBackground = true)
fun ListingsScreen(){
    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.navigationBars),
        topBar = {

        },
        bottomBar = {
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
                        isSelected = false,
                        onClick = {}
                    )
                    BottomBarItem(
                        icon = R.drawable.outline_view_list_24,
                        label = "Your Listings",
                        isSelected = true,
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
        },
        floatingActionButton = {

        }
    ) {
        Modifier.padding(it)
    }
}