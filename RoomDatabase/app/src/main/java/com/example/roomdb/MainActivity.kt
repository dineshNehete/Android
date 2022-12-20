package com.example.roomdb

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdb.databinding.ActivityMainBinding
import com.example.roomdb.databinding.DialogUpdateBinding
import kotlinx.coroutines.flow.collect
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

        lifecycleScope.launch {
            employeeDao.fetchAllEmployees().collect {
                // typecasting because the function expects an arraylist instead of list
                val list = ArrayList(it)
                setUpListOfDataIntoRecyclerView(list, employeeDao)
            }
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
        } else {
            Toast.makeText(this@MainActivity, "Invalid Data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpListOfDataIntoRecyclerView(
        employeeList: ArrayList<EmployeeEntity>,
        employeeDao: EmployeeDao
    ) {
        if (employeeList.isNotEmpty()) {
            val itemAdapter = ItemAdapter(
                employeeList,
                fun(updateId: Int) {
                    updateRecordDialog(updateId, employeeDao)
                },
                fun(deleteId: Int) {
                    // collect is suspending function need to launch in another coroutine
                    lifecycleScope.launch {
                        employeeDao.fetchEmployeeById(deleteId).collect {
                            if (it != null) {
                                deleteRecordAlertDialog(deleteId, employeeDao, it)
                            }
                        }
                    }
                }

            )
            binding?.rvItemsList?.layoutManager = LinearLayoutManager(this)
            binding?.rvItemsList?.adapter = itemAdapter
            binding?.rvItemsList?.visibility = View.VISIBLE
            binding?.tvNoRecordsAvailable?.visibility = View.GONE
        } else {
            binding?.rvItemsList?.visibility = View.GONE
            binding?.tvNoRecordsAvailable?.visibility = View.VISIBLE


        }
    }

    // passing in the dao obj  to fire the queries and make change in the database
    private fun updateRecordDialog(id: Int, employeeDao: EmployeeDao) {
        val updateDialog = Dialog(this, R.style.Theme_AppCompat_Dialog)
        updateDialog.setCancelable(false)

        val binding = DialogUpdateBinding.inflate(layoutInflater)
        updateDialog.setContentView(binding.root)

        lifecycleScope.launch {
            employeeDao.fetchEmployeeById(id).collect {
                binding.etUpdateName.setText(it.name)
                binding.etUpdateEmailId.setText(it.email)
            }
        }
        binding.tvUpdate.setOnClickListener {
            val name = binding.etUpdateName.text.toString()
            val email = binding.etUpdateEmailId.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty()) {
                lifecycleScope.launch {
                    employeeDao.update(EmployeeEntity(id, name, email))

                }
                Toast.makeText(applicationContext, "Record Updated", Toast.LENGTH_SHORT).show()
                updateDialog.dismiss()
            } else {
                Toast.makeText(applicationContext, "Record Cannot Be Empty", Toast.LENGTH_SHORT)
                    .show()

            }
        }
        binding.tvCancel.setOnClickListener {
            updateDialog.dismiss()
        }
        updateDialog.show()
    }

    fun deleteRecordAlertDialog(id: Int, employeeDao: EmployeeDao, employee: EmployeeEntity) {
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Delete Record")
        //set message for alert dialog
        builder.setMessage("Are you sure you wants to delete ${employee.name}.")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, _ ->
            lifecycleScope.launch {
                employeeDao.delete(EmployeeEntity(id))
                Toast.makeText(
                    applicationContext,
                    "Record deleted successfully.",
                    Toast.LENGTH_LONG
                ).show()

                dialogInterface.dismiss()
            }

        }


        builder.setNegativeButton("No") { dialogInterface, which ->
            dialogInterface.dismiss() // Dialog will be dismissed
        }

        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false) // Will not allow user to cancel after clicking on remaining screen area.
        alertDialog.show()
    }

}