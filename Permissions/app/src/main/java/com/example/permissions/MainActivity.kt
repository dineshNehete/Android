package com.example.permissions

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    /*
    * This is how it works for one permission
    * */
    private val cameraResultLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show()
            }
        }
    private val cameraAndLocationLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                permissions.entries.forEach {
                    // it represents the Map of string and boolean, builtin
                    val permissionName = it.key
                    val isGranted = it.value
                    if (isGranted) {
                        if (permissionName == Manifest.permission.ACCESS_FINE_LOCATION) {
                            Toast.makeText(
                                this,
                                "Permission Granted for location",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this,
                                "Permission Granted for camera",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        if (permissionName == Manifest.permission.ACCESS_FINE_LOCATION) {
                            Toast.makeText(
                                this,
                                "Permission denied for location",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this,
                                "Permission denied for camera",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCameraPermission: Button = findViewById<Button>(R.id.btnCameraPermission)

        btnCameraPermission.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
            ) {
                showRationaleDialog(
                    " Permission Demo requires camera access",
                    "Camera cannot be used because Camera access is denied"
                )
            } else {
                // Directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                cameraAndLocationLauncher.launch(
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION)
                )
            }


        }
    }

    /**
     * Shows rationale dialog for displaying why the app needs permission
     * Only shown if the user has denied the permission request previously
     */
    private fun showRationaleDialog(title: String, message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title).setMessage(message).setPositiveButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }
}