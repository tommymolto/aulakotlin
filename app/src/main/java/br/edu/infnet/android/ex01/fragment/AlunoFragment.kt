package br.edu.infnet.android.ex01.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.edu.infnet.android.ex01.HomeActivity
import br.edu.infnet.android.ex01.ListaAlunosActivity
import br.edu.infnet.android.ex01.R
import br.edu.infnet.android.ex01.model.Aluno
import br.edu.infnet.android.ex01.model.Turma
import kotlinx.android.synthetic.main.fragment_aluno.*
import kotlinx.android.synthetic.main.fragment_cadastro_aluno.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AlunoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlunoFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var turma: Turma
    private var indice = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var listAlunos = activity as HomeActivity?
        turma = listAlunos!!.turma

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_aluno, container, false)
    }
    fun hasNext(){
        if(indice < (turma.alunos.size -1)){
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

        txtNome.text = turma.alunos[indice].nome
        txtNotaUm.text = turma.alunos[indice].notaUm.toString()
        txtNotaDois.text = turma.alunos[indice].notaDois.toString()
        hasNext()
        hasPrevious()

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        /*txtNome.text = turma.alunos[0].nome
        txtNotaUm.text = turma.alunos[0].notaUm.toString()
        txtNotaDois.text = turma.alunos[0].notaDois.toString()*/
    }


}