package com.employeedb.employeedatabase.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.employeedb.employeedatabase.model.Employee
import com.employeedb.employeedatabase.viewmodel.EmployeeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateEmployeeScreen(
    viewModel: EmployeeViewModel,
    empId: Int,
    onBack: () -> Unit
) {
    val employee = viewModel.selectedEmployee.collectAsState().value

    LaunchedEffect(empId) {
        viewModel.getEmpById(empId)
    }

    if (employee == null) {
        Text("Loading...", modifier = Modifier.padding(16.dp))
        return
    }

    var name by remember { mutableStateOf(employee.name) }
    var email by remember { mutableStateOf(employee.email) }
    var department by remember { mutableStateOf(employee.department) }
    var role by remember { mutableStateOf(employee.role) }
    var leave by remember { mutableStateOf(employee.onLeave) }
    var salary by remember { mutableStateOf(employee.salary.toString()) }

    var nameTouched by remember { mutableStateOf(false) }
    var departmentTouched by remember { mutableStateOf(false) }
    var roleTouched by remember { mutableStateOf(false) }
    var salaryTouched by remember { mutableStateOf(false) }

    val isNameValid = name.isNotBlank()
    val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isDepartmentValid = department.isNotBlank()
    val isRoleValid = role.isNotBlank()
    val isSalaryValid = salary.toDoubleOrNull()?.let { it >= 0 } ?: false

    val isFormValid = isNameValid && isEmailValid && isDepartmentValid && isRoleValid && isSalaryValid

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Update Employee")
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedField(
                value = name,
                onValueChange = { name = it; nameTouched = true },
                label = "Name",
                touched = nameTouched,
                isValid = isNameValid,
                errorMsg = "Name cannot be empty"
            )
            OutlinedTextField(
                value = email,
                onValueChange = {},
                label = { Text("Email") },
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedField(
                value = department,
                onValueChange = { department = it; departmentTouched = true },
                label = "Department",
                touched = departmentTouched,
                isValid = isDepartmentValid,
                errorMsg = "Department cannot be empty"
            )
            OutlinedField(
                value = role,
                onValueChange = { role = it; roleTouched = true },
                label = "Role",
                touched = roleTouched,
                isValid = isRoleValid,
                errorMsg = "Role cannot be empty"
            )
            OutlinedField(
                value = salary,
                onValueChange = { salary = it; salaryTouched = true },
                label = "Salary",
                touched = salaryTouched,
                isValid = isSalaryValid,
                errorMsg = "Enter a valid salary",
                keyboardType = KeyboardType.Number
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "On Leave",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = leave,
                    onCheckedChange = { leave = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    viewModel.updateEmp(
                        Employee(
                            id = empId,
                            name = name,
                            email = email,
                            role = role,
                            department = department,
                            onLeave = leave,
                            salary = salary.toDouble(),
                            joinDate = employee.joinDate
                        )
                    )
                    onBack()
                },
                enabled = isFormValid,
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) {
                Text("Update Employee")
            }
        }
    }
}