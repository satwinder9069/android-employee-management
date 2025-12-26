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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.employeedb.employeedatabase.R

@Composable
fun UpcomingLeaves() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.calendar),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Upcoming Leaves",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        UpcomingItem("AT", "Alex Turner", "Dec 24-26", "Vacation")
        UpcomingItem("LA", "Lisa Anderson", "Jan 2-4", "Personal")
        UpcomingItem("JM", "James Miller", "Jan 10-12", "SickLeave")
    }

}

@Composable
fun UpcomingItem(
    initials: String,
    name: String,
    timePeriod: String,
    type: String
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFFFD5CD), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    initials,
                    color = Color(0xFFFC6646),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(name, fontWeight = FontWeight.Bold)
                Text(timePeriod, fontSize = 12.sp, color = Color.Gray)
            }

            Box(
                modifier = Modifier
                    .size(width = 80.dp, height = 25.dp)
                    .background(Color(0xFFFCD3C8), RoundedCornerShape(16.dp)),

                contentAlignment = Alignment.Center
            ) {
                Text(type, fontSize = 12.sp, color = Color(0xFFFC6646))
            }
        }
    }
}