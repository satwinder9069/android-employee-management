package com.employeedb.employeedatabase.di

import android.content.Context
import androidx.room.Room
import com.employeedb.employeedatabase.data.local.AppDatabase
import com.employeedb.employeedatabase.data.local.AttendanceDao
import com.employeedb.employeedatabase.data.local.EmployeeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "employee_db"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideEmployeeDao(db: AppDatabase): EmployeeDao = db.employeeDao()

    @Provides
    fun provideAttendanceDao(db: AppDatabase): AttendanceDao = db.attendanceDao()
}