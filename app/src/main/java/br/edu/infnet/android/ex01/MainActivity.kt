package br.edu.infnet.android.ex01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import br.edu.infnet.android.ex01.databinding.ActivityMainBinding
import br.edu.infnet.android.ex01.model.Aluno
import br.edu.infnet.android.ex01.model.Turma
import com.google.android.material.snackbar.Snackbar
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class MainActivity : AppCompatActivity() {
    val TAG = "QUEM VOTEI"
    var turma = Turma()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding.textNome.setText("Olha eu alterado")
        binding.textNome.setOnFocusChangeListener {
            _ , estaFocado ->
             if(estaFocado){
                 binding.textNome.setText("")
             }else{
                // binding.textNome.setText("To preenchido, gente")
             }

        }
        binding.notaUm.setOnFocusChangeListener {
                _ , estaFocado ->
            if(estaFocado){
                binding.notaUm.setText("")
            }else{
                // binding.textNome.setText("To preenchido, gente")
            }

        }
        binding.notaDois.setOnFocusChangeListener {
                _ , estaFocado ->
            if(estaFocado){
                binding.notaDois.setText("")
            }else{
                // binding.textNome.setText("To preenchido, gente")
            }

        }

        binding.btnSalvaUsuario.setOnClickListener {
            view -> salvarUsuario(view)
        }
        binding.btnListaAlunos.setOnClickListener {
                _ -> goToListaAlunos()
        }
        Log.d("INFO", "Cliquei")

    }
    fun goToListaAlunos(){
        val profileIntent = Intent(this,
            ListaAlunosActivity::class.java)
        profileIntent.putExtra("turma", Json.encodeToString(turma))

        startActivity(profileIntent)
    }
    fun salvarUsuario( view: View){
        var novoAluno = Aluno(
            binding.textNome.text.toString(),
            binding.notaUm.text.toString().toDouble(),
            binding.notaDois.text.toString().toDouble()

        )
        turma.alunos.add(novoAluno)
        Toast.makeText(view.context, "Aluno Salvo.", Toast.LENGTH_SHORT).show()
        findViewById<Button>(R.id.btnListaAlunos).visibility = View.VISIBLE
        Log.d("INFO", "Total alunos = ${ turma.alunos.size }")
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

    override fun onSaveInstanceState(outState: Bundle) {
        // put string value
        Log.d(TAG, "me salva")
        outState.putString("alunos", Json.encodeToString(turma))
        super.onSaveInstanceState(outState)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // get values from saved state
        Log.d(TAG, "me salva")

        val oldAlunos = savedInstanceState.getString("alunos")
        ///javascript object notation
        turma = oldAlunos?.let { Json.decodeFromString(it) }!!
        super.onRestoreInstanceState(savedInstanceState)
    }

}