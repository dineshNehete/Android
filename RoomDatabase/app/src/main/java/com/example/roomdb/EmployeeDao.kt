package com.example.roomdb

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface EmployeeDao {

    @Insert
    suspend fun insert(employeeEntity: EmployeeEntity)

    @Update
    suspend fun update(employeeEntity: EmployeeEntity)

    @Delete
    suspend fun delete(employeeEntity: EmployeeEntity)

    //You don't need to make that call in an asynchronous manner since it works that way already under the hood. If you needed only the List<Entity> object (no LiveData) then it would be better if you make that function suspendable to call it from a coroutine or another suspend function.

    // Flow is part of coroutine class which is used to hold the values that change at runtime, because it automatically emits values as live update, you just need to collect the data
    // because we are using flow theres no need to tell the recycler view that the data is been updated or added
    @Query("SELECT * FROM  `employee-table`")
    fun fetchAllEmployees(): Flow<List<EmployeeEntity>>

    @Query("SELECT * FROM  `employee-table` WHERE id = :id")
    fun fetchEmployeeById(id: Int): Flow<EmployeeEntity>


}