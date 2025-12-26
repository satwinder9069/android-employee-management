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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
fun AddEmployeeScreen(
    viewModel: EmployeeViewModel,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var department by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
    var salary by remember { mutableStateOf("") }
    var onLeave by remember { mutableStateOf(false) }

    var nameTouched by remember { mutableStateOf(false) }
    var emailTouched by remember { mutableStateOf(false) }
    var departmentTouched by remember { mutableStateOf(false) }
    var roleTouched by remember { mutableStateOf(false) }
    var salaryTouched by remember { mutableStateOf(false) }

    // validation
    val isNameValid = name.isNotBlank()
    val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isDepartmentValid = department.isNotBlank()
    val isRoleValid = role.isNotBlank()
    val isSalaryValid = salary.toDoubleOrNull()?.let { it >= 0 } ?: false

    val isFormValid =
        isNameValid && isEmailValid && isDepartmentValid && isRoleValid && isSalaryValid

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Employee") },
                navigationIcon = {
                    IconButton(
                        onClick = { onBack() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {
            OutlinedField(
                name,
                { name = it; nameTouched = true },
                "Name",
                nameTouched,
                isNameValid,
                "Name cannot be empty"
            )
            OutlinedField(
                email,
                { email = it; emailTouched = true },
                "Email",
                emailTouched,
                isEmailValid,
                "Invalid email format"
            )
            OutlinedField(
                department,
                { department = it; departmentTouched = true },
                "Department",
                departmentTouched,
                isDepartmentValid,
                "Department cannot be empty"
            )
            OutlinedField(
                role,
                { role = it; roleTouched = true },
                "Role",
                roleTouched,
                isRoleValid,
                "Role cannot be empty"
            )
            OutlinedField(
                salary,
                { salary = it; salaryTouched = true },
                "Salary",
                salaryTouched,
                isSalaryValid,
                "Enter a valid salary",
                KeyboardType.Number
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
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = onLeave,
                    onCheckedChange = { onLeave = it },
                    colors = androidx.compose.material3.SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
            Spacer(Modifier.weight(1f))
            Button(
                onClick = {
                    viewModel.addEmp(
                        Employee(
                            id = 0,
                            name = name,
                            email = email,
                            role = role.ifBlank { "Employee" },
                            salary = salary.toDouble(),
                            department = department,
                            onLeave = onLeave,
                            joinDate = System.currentTimeMillis()
                        )
                    )
                    onBack()
                },
                enabled = isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("Save Employee")
            }
        }
    }
}

@Composable
fun OutlinedField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    touched: Boolean,
    isValid: Boolean,
    errorMsg: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = !isValid && touched,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth()
    )
    if (!isValid && touched) {
        Text(errorMsg, color = Color.Red, style = MaterialTheme.typography.bodySmall)
    }
}

