package com.employeedb.employeedatabase.ui.components.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RecentActivities() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Recent Activities",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        ActivityItem("SJ", "Sarah Johnson", "Checked in", "09:05 AM")
        ActivityItem("PB", "Prince Bawa", "Leave approved", "08:45 AM")
        ActivityItem("SM", "Sania Mehra", "Profile Updated", "09:05 AM")
        ActivityItem("TK", "Taranjeet Kaur", "Checked out", "05:45 PM")
    }
}

@Composable
fun ActivityItem(
    initials: String,
    name: String,
    action: String,
    time: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFF1E5EFF), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                initials,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(name, fontWeight = FontWeight.Bold)
            Text(action, fontSize = 12.sp, color = Color.Gray)
        }
        Text(time, fontSize = 12.sp, color = Color.Gray)
    }
}