package com.employeedb.employeedatabase.data.repository

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser: FirebaseUser?
    val isUserLoggedIn: Boolean

    suspend fun signUp(
        email: String,
        password: String,
        displayName: String
    ): Result<FirebaseUser>

    suspend fun signIn(
        email: String,
        password: String
    ): Result<FirebaseUser>

    suspend fun sendPasswordResetEmail(email: String): Result<Unit>

    suspend fun sendEmailVerification(): Result<Unit>

    fun signOut()
}