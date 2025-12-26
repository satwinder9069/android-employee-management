package com.employeedb.employeedatabase.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.employeedb.employeedatabase.R
import com.employeedb.employeedatabase.navigation.Screen
import com.employeedb.employeedatabase.ui.components.dashboard.AttendanceOverview
import com.employeedb.employeedatabase.ui.components.details.ContactInfo
import com.employeedb.employeedatabase.ui.components.details.EmployeeDetailHeader
import com.employeedb.employeedatabase.ui.components.details.EmploymentDetails
import com.employeedb.employeedatabase.ui.components.details.InfoCard
import com.employeedb.employeedatabase.ui.components.details.RecentAttendanceSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, employeeId: Int) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Back",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_back),
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1E5EFF)
                )
            )
        },
        bottomBar = {}
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    EmployeeDetailHeader(
                        "SK",
                        "Satwinder Kaur",
                        "Software Development",
                        "Engineering",
                        modifier = Modifier
                            .height(140.dp)
                    )


                    Spacer(modifier = Modifier.height(100.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .offset(y = 110.dp)
                            .zIndex(1f),

                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        InfoCard(
                            painterResource(R.drawable.clock),
                            Color(0xFF66BB88),
                            "Attendance",
                            "96%",
                            modifier = Modifier
                        )
                        InfoCard(
                            painterResource(R.drawable.calendar),
                            Color(0xFF1E5EFF),
                            "Leaves Taken",
                            "8",
                            modifier = Modifier

                        )
                        InfoCard(
                            painterResource(R.drawable.performance_badge),
                            Color(0xFFD9514F),
                            "Performance",
                            "4.5",
                            modifier = Modifier
                        )
                    }
                }
            }


            item {
                Spacer(modifier = Modifier.height(110.dp))
            }
            item {
                ContactInfo("satwinderkaur@gmail.com")
            }
            item {
                EmploymentDetails("EMP-001", "Jan 15, 2025", "Engineering", "John Smith")
            }
            item {
                RecentAttendanceSection()
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { navController.navigate(Screen.UpdateEmployee.createRoute(employeeId)) },
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1E5EFF)
                        )
                    ) {
                        Text("Edit Details")
                    }
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("View Documents")
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}
