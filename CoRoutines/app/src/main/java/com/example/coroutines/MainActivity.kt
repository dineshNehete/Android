package com.example.coroutines

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    //Create a dialog variable
    var customProgressDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnExecute: Button = findViewById(R.id.btn_execute)

        btnExecute.setOnClickListener {
            lifecycleScope.launch {
                showProgressDialog()
                execute("Task Executed Successfully")
            }
        }
    }

    /*
    * To stop the function from blocking the UI we need to write the suspend keyword and also we need to make sure that the code runs on a different thread
    * */
    private suspend fun execute(result: String) {
        /*
        * withContext method executes the code in another thread and then after completion brings back to current thread
        * */
        withContext(Dispatchers.IO) {
            for (i in 1..100000) {
                Log.e("Delay: ", "" + i)
            }
            /*
            * This method is added because we are executing the toast message on the Dispatchers thread but actually it should execute on the UI thread, therefore we explicitly need to mention that
            * */
            runOnUiThread {
                cancelProgressDialog()
                Toast.makeText(
                    this@MainActivity, result,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun showProgressDialog() {
        customProgressDialog = Dialog(this@MainActivity)

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        customProgressDialog?.setContentView(R.layout.dialog_custom_progress)

        //Start the dialog and display it on screen.
        customProgressDialog?.show()
    }

    /**
     * This function is used to dismiss the progress dialog if it is visible to user.
     */
    private fun cancelProgressDialog() {
        if (customProgressDialog != null) {
            customProgressDialog?.dismiss()
            customProgressDialog = null
        }
    }

}