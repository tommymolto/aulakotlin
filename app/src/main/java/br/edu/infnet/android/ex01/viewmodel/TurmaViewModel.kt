package br.edu.infnet.android.ex01.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.infnet.android.ex01.model.Aluno

class TurmaViewModel: ViewModel() {
    private var _alunos = MutableLiveData<List<Aluno>>()
    var alunos: LiveData<List<Aluno>> = _alunos

    fun addAluno(_aluno: Aluno): Unit{
        Log.d("TOTAL", "addAluno")

        //_alunos.value?.toMutableList()?.add(_aluno)
        _alunos.value=_alunos.value?.plus(_aluno).apply {  } ?: mutableListOf(_aluno).apply {  }
        Log.d("TOTAL", _alunos.value.toString())

    }
}