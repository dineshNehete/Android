package com.example.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee-table")
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",          // if we don't override the name of the column then internally it will be stored as the variable name
    @ColumnInfo(name = "email-id") // internally the attribute would be stored by the name email-id, however we will use email
    val email: String = ""

)
