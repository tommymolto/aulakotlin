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
import br.edu.infnet.android.ex01.HomeActivity
import br.edu.infnet.android.ex01.R
import br.edu.infnet.android.ex01.model.Aluno
import br.edu.infnet.android.ex01.model.Turma
import br.edu.infnet.android.ex01.viewmodel.TurmaViewModel
import kotlinx.android.synthetic.main.fragment_aluno.*
import kotlinx.android.synthetic.main.fragment_cadastro_aluno.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class AlunoFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private  val turmaViewModel: TurmaViewModel by activityViewModels()
    //private lateinit var turma: Turma
    private lateinit var alunos: List<Aluno>
    private var indice = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_aluno, container, false)
    }
    private fun subscribe(){
        Log.d("OBSERVE", "subscribe")

        turmaViewModel.alunos.observe(viewLifecycleOwner, Observer {
            Log.d("OBSERVE", "AQUI")
            Log.d("OBSERVE", it.size.toString())
            // Se o valor não for nulo, nem vazio ou espaços
            if(it != null && it.isNotEmpty()){
                // torna as Views visíveis
                llAluno.visibility = View.VISIBLE
                tvSemAlunos.visibility = View.INVISIBLE
                alunos = it
                navegaAluno()
            } else { // caso contrário, esconde as Views
                tvSemAlunos.visibility = View.VISIBLE
                llAluno.visibility = View.INVISIBLE
            }
        })
    }
    fun hasNext(){
        //if(indice < (turma.alunos.size -1)){
        if(indice < (alunos.size -1)){
            btProximo.visibility = View.VISIBLE
        }else{
            btProximo.visibility = View.INVISIBLE
        }
    }
    fun hasPrevious(){
        if(indice > 0){
            btAnterior.visibility = View.VISIBLE
        }else{
            btAnterior.visibility = View.INVISIBLE
        }
    }
    fun navegaAluno(){
        //if(turma.alunos?.isEmpty() == true) {
        if(this::alunos.isInitialized) {
            llAluno.visibility = View.VISIBLE
            tvSemAlunos.visibility = View.INVISIBLE
            txtNome.text = alunos[indice].nome
            txtNotaUm.text = alunos[indice].notaUm.toString()
            txtNotaDois.text = alunos[indice].notaDois.toString()
            hasNext()
            hasPrevious()

        }else{

            tvSemAlunos.visibility = View.VISIBLE
            llAluno.visibility = View.INVISIBLE
        }


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        activity?.let {
//            turmaViewModel = ViewModelProvider(this).get(TurmaViewModel::class.java)
//        }
        val al = Aluno("x",5.0,6.0)
        val al2 = Aluno("x1",5.0,6.0)
        val al3 = Aluno("x2",5.0,6.0)
        turmaViewModel.addAluno(al)
        turmaViewModel.addAluno(al3)
        turmaViewModel.addAluno(al2)
        Log.d("turmaViewModel", turmaViewModel.alunos.value.toString())
        subscribe()
        navegaAluno()
        btAnterior.setOnClickListener {
            _ ->
            indice--
            navegaAluno()
        }
        btProximo.setOnClickListener {
                _ ->
            indice++
            navegaAluno()
        }
    }


}