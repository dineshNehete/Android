package com.example.customdialog

import android.app.Dialog
import android.content.DialogInterface
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageButton: ImageButton = findViewById(R.id.image_button)
        imageButton.setOnClickListener { view ->
            Snackbar.make(view, "You clicked image button", Snackbar.LENGTH_LONG).show()
        }
        val btnAlertDialog: Button = findViewById(R.id.btn_alert_dialog)
        btnAlertDialog.setOnClickListener { view ->

            //Launch Alert Dialog
            alertDialogFunction()
        }
    }

    private fun customDialogFunction() {
        val customDialog = Dialog(this)
        /*Set the screen content from a layout resource.
    The resource will be inflated, adding all top-level views to the screen.*/
        customDialog.setContentView(R.layout.dialog_custom)

        customDialog.findViewById<TextView>(R.id.tv_submit).setOnClickListener {
            Toast.makeText(applicationContext, "clicked submit", Toast.LENGTH_LONG).show()
            customDialog.dismiss() // Dialog will be dismissed
        }

        customDialog.findViewById<TextView>(R.id.tv_cancel).setOnClickListener {
            Toast.makeText(applicationContext, "clicked cancel", Toast.LENGTH_LONG).show()
            customDialog.dismiss()
        }
        //Start the dialog and display it on screen.
        customDialog.show()

    }


    private fun alertDialogFunction() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Alert")
        builder.setMessage("This is a alert dialog which is used to show alerts in our app")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes", fun(dialogInterface: DialogInterface, which: Int) {
            Toast.makeText(applicationContext, "Clicked yes", Toast.LENGTH_LONG).show()
            dialogInterface.dismiss()
        })

        builder.setNegativeButton("No", fun(dialogInterface: DialogInterface, which: Int) {
            Toast.makeText(applicationContext, "Clicked No", Toast.LENGTH_LONG).show()
            dialogInterface.dismiss()
        })

        builder.setNeutralButton("Cancel", fun(dialogInterface: DialogInterface, which: Int) {
            Toast.makeText(applicationContext, "Clicked No", Toast.LENGTH_LONG).show()
            dialogInterface.dismiss()
        })

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false) // will not allow the user to cancel if clicked on the remaining area
        alertDialog.show()
    }

    private fun customProgressDialogFunction() {
        val customProgressDialog = Dialog(this)
        /*Set the screen content from a layout resource.
           The resource will be inflated, adding all top-level views to the screen.*/
        customProgressDialog.setContentView(R.layout.dialog_custom_progress)
        /*
        * Start the dialog
        * */
        customProgressDialog.show()
    }
}