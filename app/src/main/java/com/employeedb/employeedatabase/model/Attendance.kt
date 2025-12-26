package com.employeedb.employeedatabase.model

// using it later
data class Attendance(
    val date: String,
    val inTime: String,
    val outTime: String,
    val totalHours: String,
    val status: AttendanceStatus
)

enum class AttendanceStatus {
    PRESENT, LATE, LEAVE
}