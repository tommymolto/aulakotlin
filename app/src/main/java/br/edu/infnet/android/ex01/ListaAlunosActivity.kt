package br.edu.infnet.android.ex01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.edu.infnet.android.ex01.databinding.ActivityMainBinding
import br.edu.infnet.android.ex01.model.Turma
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ListaAlunosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var turma = Turma()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_lista_alunos)
        var alunos = intent.getSerializableExtra("turma")
        turma = alunos?.let { Json.decodeFromString(it as String) }!!
        Log.d("size","${ turma.alunos.size}")


    }
}