package br.edu.infnet.android.ex01

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.INFO
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.NavController

import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import br.edu.infnet.android.ex01.model.Turma
import com.google.android.material.bottomnavigation.BottomNavigationView

import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    var turma = Turma()
    private lateinit var  host: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        host = supportFragmentManager
            .findFragmentById(R.id.fragcontainer) as NavHostFragment


        // Set up Action Bar
        val navController = host.navController
        /*navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                Integer.toString(destination.id)
            }

            Toast.makeText(this@HomeActivity, "Navigated to $dest",
                Toast.LENGTH_SHORT).show()
            Log.d("NavigationActivity", "Navigated to $dest")
        }*/
        configurarNavegacao(navController)
    }
    private fun configurarNavegacao(navController: NavController){
        Log.e("INFO", "CONFIGUREI")
        val bottomNav = findViewById<BottomNavigationView>(R.id.navbar)
        bottomNav?.setupWithNavController(navController)
    }


}