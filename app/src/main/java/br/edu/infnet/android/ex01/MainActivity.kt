package br.edu.infnet.android.ex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import br.edu.infnet.android.ex01.databinding.ActivityMainBinding
import br.edu.infnet.android.ex01.model.Aluno
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    val TAG = "QUEM VOTEI"
    val botao = "CADASTRAR"
    val alunos = mutableListOf<Aluno>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.textNome.setText("Digite seu Nome")
        binding.textNome.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                binding.textNome.setText("")
        }
        Log.d("INFO", "Cliquei")

        binding.btnSalvaUsuario.setOnClickListener {view ->
            cliqueiAqui(view)
        }



    }
    fun cliqueiAqui( view: View){
        binding.btnSalvaUsuario.setText("Salvo")
        alunos.add(Aluno(
            binding.textNome.text.toString(),
            binding.notaUm.text.toString().toDouble(),
            binding.notaDois.text.toString().toDouble()
        ))
        Toast.makeText(view.context, "You clicked me.", Toast.LENGTH_SHORT).show()
        Log.d("TOTAL", "TOTAL DE ALUNOS : ${alunos.size}")
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
