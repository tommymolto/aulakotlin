package br.edu.infnet.android.ex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController

import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        //configurarNavegacao()
    }
    private fun configurarNavegacao(){
        navbar.setupWithNavController(
            findNavController(R.id.fragcontainer)
        )
    }
}