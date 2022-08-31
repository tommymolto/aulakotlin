package br.edu.infnet.android.ex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import br.edu.infnet.android.ex01.model.Turma
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ListaAlunosActivity : AppCompatActivity() {
    var turma = Turma()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_alunos)


        val conteudo = intent.getSerializableExtra("turma")
        turma = conteudo?.let { Json.decodeFromString(it as String) }!!
        var txtAlunos = ""
        for(aluno in turma.alunos){
            txtAlunos += " ${ aluno.nome} \n"
        }
        val tvAlunos = findViewById<TextView>(R.id.tvAlunos)
        tvAlunos.textSize = 22.0F
        tvAlunos.text = txtAlunos
        Log.d("Info", txtAlunos)
    }
}


