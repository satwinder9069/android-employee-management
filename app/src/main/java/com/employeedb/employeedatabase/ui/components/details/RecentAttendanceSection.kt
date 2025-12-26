package com.employeedb.employeedatabase.ui.components.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
fun RecentAttendanceSection() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFAFA)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.description),
                    contentDescription = null,
                    tint = Color(0xFF1E5EFF)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Recent Attendance",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            AttendanceRow(
                date = "Dec 15, 2025",
                inTime = "09:05 AM",
                outTime = "06:15 PM",
                total = "9h 10m",
                status = "Present"
            )

            AttendanceRow(
                date = "Dec 14, 2025",
                inTime = "08:58 AM",
                outTime = "06:05 PM",
                total = "9h 7m",
                status = "Present"
            )

            AttendanceRow(
                date = "Dec 13, 2025",
                inTime = "09:15 AM",
                outTime = "06:00 PM",
                total = "8h 45m",
                status = "Late"
            )

            AttendanceRow(
                date = "Dec 12, 2025",
                inTime = "09:00 AM",
                outTime = "06:10 PM",
                total = "9h 10m",
                status = "Present"
            )

            AttendanceRow(
                date = "Dec 11, 2025",
                inTime = "-",
                outTime = "-",
                total = "-",
                status = "Leave"
            )
        }
    }
}

@Composable
fun AttendanceRow(
    date: String,
    inTime: String,
    outTime: String,
    total: String,
    status: String
) {
    val barColor = when (status) {
        "Present" -> Color(0xFF00C853)
        "Late" -> Color(0xFFFF9100)
        "Leave" -> Color(0xFFFF1744)
        else -> Color.Gray
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(52.dp)
                .background(barColor, RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(date, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "In: $inTime   Out: $outTime   Total: $total",
                fontSize = 12.sp,
                color = Color.Gray
            )

        }

        AttendanceStatusChip(status)
    }

}

@Composable
fun AttendanceStatusChip(status: String) {
    val bgColor = when (status) {
        "Present" -> Color(0xFFE8F5E9)
        "Late" -> Color(0xFFFFF3E0)
        "Leave" -> Color(0xFFFFEBEE)
        else -> Color.LightGray
    }

    val textColor = when (status) {
        "Present" -> Color(0xFF2E7D32)
        "Late" -> Color(0xFFF57C00)
        "Leave" -> Color(0xFFC62828)
        else -> Color.DarkGray
    }

    Box(
        modifier = Modifier
            .background(bgColor, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = status,
            fontSize = 12.sp,
            color = textColor,
            fontWeight = FontWeight.Medium
        )
    }
}