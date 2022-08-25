package br.edu.infnet.android.ex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import br.edu.infnet.android.ex01.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    val TAG = "QUEM VOTEI"

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        Log.d("INFO", "Cliquei")





    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG , "Entrou onStart")
    }
    override fun onRestart() {
        super.onRestart()
        Log.d(TAG , "Entrou onRestart")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG , "Entrou onResume")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG , "Entrou onPause")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG , "Entrou onStop")
    }
    override fun onDestroy() {
        Log.d(TAG , "Entrou onDestroy")
        super.onDestroy()
    }
}