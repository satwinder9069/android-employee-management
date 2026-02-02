package com.employeedb.employeedatabase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.employeedb.employeedatabase.data.model.AuthResult
import com.employeedb.employeedatabase.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseAuthRepo : AuthRepository
) : ViewModel() {
    private val _authState = MutableStateFlow<AuthResult<FirebaseUser>> (AuthResult.Idle)
    val authState : StateFlow<AuthResult<FirebaseUser>> = _authState.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(firebaseAuthRepo.isUserLoggedIn)
    val isLoggedIn : StateFlow<Boolean> = _isLoggedIn

    val currentUser: FirebaseUser?
        get() = firebaseAuthRepo.currentUser

    init {
        checkAuthState()
    }

    private fun checkAuthState() {
        _isLoggedIn.value = firebaseAuthRepo.isUserLoggedIn
    }

    fun signUp(email: String, password: String, displayName: String) {
        viewModelScope.launch {
            _authState.value = AuthResult.Loading

            val result = firebaseAuthRepo.signUp(email, password, displayName)

            _authState.value = result.fold(
                onSuccess = { user ->
                    _isLoggedIn.value = true
                    AuthResult.Success(user)
                },
                onFailure = { exception ->
                    AuthResult.Error(exception.message ?: "Sign up failed")
                }
            )
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthResult.Loading

            val result = firebaseAuthRepo.signIn(email, password)

            _authState.value =result.fold(
                onSuccess = { user->
                    _isLoggedIn.value = true
                    AuthResult.Success(user)

                },
                onFailure = { exception ->
                    AuthResult.Error(exception.message ?: "Sign in failed")
                }
            )
        }
    }

    fun sendPasswordResetEmail(email: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val result = firebaseAuthRepo.sendPasswordResetEmail(email)

            result.fold(
                onSuccess = { user ->
                    onResult(true, "Password reset email sent")
                },
                onFailure = { exception ->
                    onResult(false, exception.message ?: "Failed to end email")
                }
            )
        }
    }

    fun sendEmailVerification(onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val result = firebaseAuthRepo.sendEmailVerification()

            result.fold(
                onSuccess = { user ->
                    onResult(true, "Verification email sent")
                },
                onFailure = { exception ->
                    onResult(false, exception.message ?: "Failed to send verification")
                }
            )
        }
    }

    fun signOut() {
        firebaseAuthRepo.signOut()
        _isLoggedIn.value = false
        _authState.value = AuthResult.Idle
    }

    fun resetAuthState() {
        _authState.value = AuthResult.Idle
    }

    private fun getErrorMessage(exception: Throwable) : String {
        return when {
            exception.message?.contains("email address is already in use") == true ->
                "This email is already registered"
            exception.message?.contains("email address is badly formatted") == true ->
                "Invalid email format"
            exception.message?.contains("password is invalid") == true ->
                "Invalid email or password"
            exception.message?.contains("no user record") == true ->
                "No account found with this email"
            exception.message?.contains("network error") == true ->
                "Network error. Please check your connection"
            else ->
                exception.message ?: "Authentication failed"
        }
    }
}