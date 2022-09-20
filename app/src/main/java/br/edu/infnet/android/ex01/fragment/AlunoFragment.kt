package br.edu.infnet.android.ex01.fragment

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.infnet.android.ex01.HomeActivity
import br.edu.infnet.android.ex01.R
import br.edu.infnet.android.ex01.adapter.ListaAlunoAdapter
import br.edu.infnet.android.ex01.model.Aluno
import br.edu.infnet.android.ex01.model.Turma
import br.edu.infnet.android.ex01.viewmodel.TurmaViewModel
import kotlinx.android.synthetic.main.fragment_aluno.*
import kotlinx.android.synthetic.main.fragment_cadastro_aluno.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class AlunoFragment : Fragment() {

    private  val turmaViewModel: TurmaViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_aluno, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lista_alunos.layoutManager =
            LinearLayoutManager(activity)
        lista_alunos.addItemDecoration(
            DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        )
        if(turmaViewModel.alunos.value?.isNotEmpty() == true){
            lista_alunos.visibility = View.VISIBLE
            tvSemAlunos.visibility = View.INVISIBLE
            lista_alunos.adapter = ListaAlunoAdapter(turmaViewModel.alunos.value!!)
        }else{
            lista_alunos.visibility = View.INVISIBLE
            tvSemAlunos.visibility = View.VISIBLE
        }

    }



}