package br.edu.infnet.android.myapplication

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.view.View
import br.edu.infnet.android.myapplication.databinding.ActivityMainBinding
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val tag: String = "ActivityAsyncTask"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.CallBtn.setOnClickListener {
            AsyncTaskExample(this).execute()
        }

    }
    class AsyncTaskExample(private var activity: MainActivity) : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            this.activity?.binding?.MyprogressBar?.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg p0: String?): String {

            var result = ""
            try {
                val url = URL("https://reqres.in/api/users?page=2")
                val httpURLConnection = url.openConnection() as HttpURLConnection

                httpURLConnection.readTimeout = 80000
                httpURLConnection.connectTimeout = 80000
                httpURLConnection.doOutput = true
                httpURLConnection.connect()

                val responseCode: Int = httpURLConnection.responseCode
                Log.d(activity?.tag, "responseCode - $responseCode")

                if (responseCode == 200 || responseCode == 201) {
                    val inStream: InputStream = httpURLConnection.inputStream
                    val isReader = InputStreamReader(inStream)
                    val bReader = BufferedReader(isReader)
                    var tempStr: String?

                    try {

                        while (true) {
                            tempStr = bReader.readLine()
                            if (tempStr == null) {
                                break
                            }
                            result += tempStr
                        }
                    } catch (Ex: Exception) {
                        Log.e(activity?.tag, "Error in convertToString " + Ex.printStackTrace())
                    }
                }
            } catch (ex: Exception) {
                Log.d("", "Error in doInBackground " + ex.message)
            }
            return result
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            this.activity?.binding?.MyprogressBar?.visibility = View.INVISIBLE
            if (result == "") {
                this.activity?.binding?.myText?.setText("Erro")
            } else {
                var parsedResult = ""
//                var jsonObject: JSONObject? = JSONObject(result)
//                jsonObject = jsonObject?.getJSONObject("data")
//                parsedResult += "Code Name : " + (jsonObject?.get("code_name")) + "\n"
//                parsedResult += "Version Number : " + (jsonObject?.get("version_number")) + "\n"
//                parsedResult += "API Level : " + (jsonObject?.get("api_level"))
                this.activity?.binding?.myText?.setText(result)
            }
        }
    }



}