package br.edu.infnet.android.ex01.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.edu.infnet.android.ex01.AlunoActivity
import br.edu.infnet.android.ex01.R
import br.edu.infnet.android.ex01.model.Turma
import kotlinx.android.synthetic.main.fragment_lista_aluno.*




class AlunoFragment : Fragment() {

    private lateinit var turma: Turma
    private var indice = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var listAlunos = activity as AlunoActivity?
        turma = listAlunos!!.turma

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_aluno, container, false)
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