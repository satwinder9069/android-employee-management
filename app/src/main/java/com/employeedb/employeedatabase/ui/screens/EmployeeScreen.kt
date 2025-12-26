package com.employeedb.employeedatabase.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.employeedb.employeedatabase.model.Employee
import com.employeedb.employeedatabase.ui.components.BottomNavigationBar
import com.employeedb.employeedatabase.ui.components.employees.EmployeeCountWithFilter
import com.employeedb.employeedatabase.ui.components.employees.EmployeeItem
import com.employeedb.employeedatabase.ui.components.employees.EmployeesHeader
import com.employeedb.employeedatabase.ui.components.employees.FilterTabs
import com.employeedb.employeedatabase.viewmodel.EmployeeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeScreen(
    viewModel : EmployeeViewModel = hiltViewModel(),
    onAddClick : () -> Unit,
    navController: NavHostController
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredEmployees by viewModel.filteredEmployees.collectAsState()
    val selectedDepartment by viewModel.selectedDepartment.collectAsState()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Employee"
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }

    ) {paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmployeesHeader(searchQuery = searchQuery, onSearchChange = viewModel::onSearchQueryChange)
            FilterTabs(
                selectedTab = selectedDepartment,
                onTabSelected = viewModel::onDepartmentSelected
            )

            EmployeeCountWithFilter(
                count = filteredEmployees.size
            ) {  /* open bottom sheet or handle filter click */}

            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(filteredEmployees) { employee ->
                    EmployeeItem(employee, navController = navController )
                }
            }
        }
    }
}