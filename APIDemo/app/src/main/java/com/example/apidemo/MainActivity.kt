package com.example.apidemo

import android.app.Dialog
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import java.sql.Connection
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CallAPILoginAsync().execute()
    }

    private inner class CallAPILoginAsync() : AsyncTask<Any, Void, String>() {
        private lateinit var customProgressDialog: Dialog

        /**
         * This will be executed before doInBackground method
         */
        override fun onPreExecute() {
            super.onPreExecute()
            showProgressDialog()
        }

        override fun doInBackground(vararg params: Any?): String {
            var result: String
            var connection: HttpURLConnection? = null

            try {
                val url = URL("https://run.mocky.io/v3/3fc49fc4-e540-4e2f-86d7-5341964a6b05")
                connection = url.openConnection() as HttpURLConnection?
                connection!!.doInput = true
                connection.doOutput = true

                val httpResult: Int = connection.responseCode

                if (httpResult == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream

                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val stringBuilder = StringBuilder()
                    var line: String? = ""

                    try {
//                        while (true) {
//                            line = reader.readLine()
//                            if (line == null) break
//                            stringBuilder.append(line)
//                        }
                        // another way, this closes the stream automatically
                        val inputString =
                            BufferedReader(InputStreamReader(inputStream)).useLines { lines ->
                                lines.forEach { stringBuilder.append(it) }
                            }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } finally {
                        try {
                            inputStream.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                    result = stringBuilder.toString()
                } else {
                    result = connection.responseMessage
                }

            } catch (e: SocketTimeoutException) {
                result = "Connection Timeout"
            } catch (e: Exception) {
                result = "Error : " + e.message
            } finally {
                connection?.disconnect()
            }
            return result
        }


        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            cancelProgesssDialog()


            if (result != null) {
                Log.i("JSON RESULT : ", result)
                // processing the json objects
                val jsonObject = JSONObject(result)
                val name = jsonObject.optString("name")
                Log.i("Name", name)
                val dept = jsonObject.optString("dept")
                Log.i("Dept", dept)


                // if we want to grab an array
//                val dataListArray = jsonObject.optJSONArray("nameOfArray")

                // nested fields
//                val name2 = jsonObject.optJSONObject("profile").optString("name")
            }
        }

        private fun showProgressDialog() {
            customProgressDialog = Dialog(this@MainActivity)
            customProgressDialog.setContentView(R.layout.dialog_custom_progress)
            customProgressDialog.show()
        }

        private fun cancelProgesssDialog() {
            customProgressDialog.dismiss()
        }

    }
}