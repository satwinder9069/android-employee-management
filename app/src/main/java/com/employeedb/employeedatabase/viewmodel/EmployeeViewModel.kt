package com.employeedb.employeedatabase.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.employeedb.employeedatabase.data.repository.EmployeeRepository
import com.employeedb.employeedatabase.model.Employee
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel @Inject constructor(
    private val repo: EmployeeRepository
) : ViewModel() {

    private val _employees = MutableStateFlow<List<Employee>>(emptyList())
    val employees: StateFlow<List<Employee>> = _employees

    private val _selectedEmployee = MutableStateFlow<Employee?>(null)
    val selectedEmployee: StateFlow<Employee?> = _selectedEmployee

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedDepartment = MutableStateFlow("All")
    val selectedDepartment = _selectedDepartment.asStateFlow()

    val filteredEmployees: StateFlow<List<Employee>> =
        combine(_employees, _searchQuery, _selectedDepartment) { employees, query, department ->
            employees.filter { employee ->
                val matchesSearch =
                    query.isBlank() ||
                            employee.name.contains(query, ignoreCase = true)
                val matchesDepartment =
                    department == "All" || employee.department == department
                matchesSearch && matchesDepartment

            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    init {
        getAllEmployees()
    }

    fun getAllEmployees() {
        viewModelScope.launch {
            repo.getEmployees().collect { list ->
                _employees.value = list
            }
        }
    }

    fun getEmpById(id: Int) {
        viewModelScope.launch {
            val emp = repo.getEmpById(id)
            _selectedEmployee.value = emp
        }
    }

    fun addEmp(emp: Employee) {
        viewModelScope.launch {
            repo.addEmp(emp)
        }
    }

    fun updateEmp(emp: Employee) {
        viewModelScope.launch {
            repo.updateEmp(emp)
        }
    }

    fun deleteEmp(emp: Employee) {
        viewModelScope.launch {
            repo.deleteEmp(emp)
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onDepartmentSelected(department: String) {
        _selectedDepartment.value = department
    }

    fun observeFilteredEmployees() {
        viewModelScope.launch {
            repo.searchAndFilter(
                _searchQuery.value,
                _selectedDepartment.value
            ).collect { list ->
                _employees.value = list
            }
        }
    }

}