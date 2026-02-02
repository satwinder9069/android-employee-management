package com.employeedb.employeedatabase.di

import com.employeedb.employeedatabase.data.repository.AttendanceRepository
import com.employeedb.employeedatabase.data.repository.AttendanceRepositoryImpl
import com.employeedb.employeedatabase.data.repository.AuthRepository
import com.employeedb.employeedatabase.data.repository.AuthRepositoryImpl
import com.employeedb.employeedatabase.data.repository.EmployeeRepository
import com.employeedb.employeedatabase.data.repository.EmployeeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindEmployeeRepository (
        impl: EmployeeRepositoryImpl
    ) : EmployeeRepository

    @Binds
    abstract fun provideAttendanceRepository (
        impl: AttendanceRepositoryImpl
    ) : AttendanceRepository

    @Binds
    abstract fun authFirebaseRepository (
        impl: AuthRepositoryImpl
    ) : AuthRepository
}