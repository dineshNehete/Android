package com.example.roomdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.roomdb.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)



        val employeeDao = (application as EmployeeApp).db.employeeDao()
        binding?.btnAdd?.setOnClickListener {
            // TODO call addRecord with employeeDao
            addRecord(employeeDao)
        }
    }


    fun addRecord(employeeDao: EmployeeDao) {
        val name = binding?.etName?.text?.toString()
        val email = binding?.etEmailId?.text?.toString()

        if (name?.isNotEmpty()!! && email?.isNotEmpty()!!) {
            // running on the background thread
            lifecycleScope.launch {
                employeeDao.insert(EmployeeEntity(name = name, email = email))
                Toast.makeText(this@MainActivity, "Record Saved", Toast.LENGTH_SHORT).show()
                binding?.etName?.text?.clear()
                binding?.etEmailId?.text?.clear()
            }
        }else{
            Toast.makeText(this@MainActivity, "Invalid Data", Toast.LENGTH_SHORT).show()
        }
    }

}