package com.employeedb.employeedatabase.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.employeedb.employeedatabase.ui.screens.attendance.AttendanceScreen
import com.employeedb.employeedatabase.ui.screens.auth.LoginScreen
import com.employeedb.employeedatabase.ui.screens.auth.SignUpScreen
import com.employeedb.employeedatabase.ui.screens.dashboard.DashboardScreen
import com.employeedb.employeedatabase.ui.screens.detail.DetailScreen
import com.employeedb.employeedatabase.ui.screens.employee.EmployeeFormScreen
import com.employeedb.employeedatabase.ui.screens.employee.EmployeeScreen
import com.employeedb.employeedatabase.ui.screens.settings.SettingsScreen
import com.employeedb.employeedatabase.viewmodel.AuthViewModel
import com.employeedb.employeedatabase.viewmodel.EmployeeViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    viewModel: EmployeeViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

    val startDestination = if (isLoggedIn) {
        Screen.DashboardScreen.route
    } else {
        Screen.LoginScreen.route
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            route = Screen.LoginScreen.route
        ) {
            LoginScreen(
                onNavigateToSignUp = {
                    navController.navigate(Screen.SignUpScreen.route)
                },
                onNavigateToDashboard = {
                    navController.navigate(Screen.DashboardScreen.route) {
                        popUpTo(Screen.LoginScreen.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.SignUpScreen.route
        ) {
            SignUpScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onNavigateToDashboard = {
                    navController.navigate(Screen.DashboardScreen.route) {
                        popUpTo(Screen.SignUpScreen.route) { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = Screen.DashboardScreen.route
        ) {
            DashboardScreen(
                viewModel = viewModel,
                navController = navController,
                dashboardViewModel = hiltViewModel(),
                onSignOut = {
                    authViewModel.signOut()
                    navController.navigate(Screen.LoginScreen.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.EmployeeListScreen.route,
        ) {
            EmployeeScreen(
                viewModel = viewModel,
                onAddClick = { navController.navigate(Screen.EmployeeForm.route) },
                navController
            )
        }

        composable(
            route = Screen.EmployeeForm.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val employeeId =
                backStackEntry.arguments
                    ?.getString("id")
                    ?.toLong()

            EmployeeFormScreen(
                navController = navController,
                employeeId = employeeId
            )
        }

        composable(
            route = Screen.DetailScreen.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backstackEntry ->
            val employeeId = backstackEntry.arguments?.getLong("id") ?: return@composable
            DetailScreen(
                navController = navController,
                employeeId = employeeId,
                attendanceViewModel = hiltViewModel()
            )
        }

        composable(
            route = Screen.AttendanceScreen.route
        ) {
            AttendanceScreen(navController, viewModel = hiltViewModel())
        }

        composable(
            route = Screen.SettingsScreen.route
        ) {
            SettingsScreen(navController = navController, authViewModel = authViewModel)
        }
    }
}