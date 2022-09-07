package br.edu.infnet.android.ex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import br.edu.infnet.android.ex01.model.Turma
import com.google.android.material.bottomnavigation.BottomNavigationView

class AlunoActivity : AppCompatActivity() {
    var turma = Turma()
    private lateinit var  host: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aluno)
        host = supportFragmentManager
            .findFragmentById(R.id.actHomeFragmentContainer) as NavHostFragment
        val navController = host.navController
        configurarNavegacao(navController)

        /*
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                Integer.toString(destination.id)
            }

            Toast.makeText(this@HomeActivity, "Navigated to $dest",
                Toast.LENGTH_SHORT).show()
            Log.d("NavigationActivity", "Navigated to $dest")
        }
         */
    }
    private fun configurarNavegacao(navController: NavController){
        Log.e("INFO", "CONFOIGUREI")
        val bottomNav = findViewById<BottomNavigationView>(R.id.actHomeNavigationBarBottom)
        bottomNav?.setupWithNavController(navController)
    }
}