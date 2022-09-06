package br.edu.infnet.android.ex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import br.edu.infnet.android.ex01.model.Turma

import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    var turma = Turma()
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