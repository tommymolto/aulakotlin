package br.edu.infnet.android.ex01.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import br.edu.infnet.android.ex01.AlunoActivity
import br.edu.infnet.android.ex01.R
import br.edu.infnet.android.ex01.model.Aluno
import br.edu.infnet.android.ex01.model.Turma
import kotlinx.android.synthetic.main.fragment_cadastro_aluno.*


class CadastroAlunoFragment : Fragment() {

    private lateinit var turma: Turma
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
        return inflater.inflate(R.layout.fragment_cadastro_aluno, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSalvaUsuario.setOnClickListener {
                view -> salvarUsuario(view)
        }
//        btnListaAlunos.setOnClickListener {
//                _ -> goToListaAlunos()
//        }
    }
    fun goToListaAlunos() {


        findNavController().navigate(R.id.HomeActFragmentListar)
    }
    fun salvarUsuario( view: View){
        var novoAluno = Aluno(
            textNome.text.toString(),
            notaUm.text.toString().toDouble(),
            notaDois.text.toString().toDouble()

        )
        turma.alunos.add(novoAluno)
        Toast.makeText(view.context, "Aluno Salvo.", Toast.LENGTH_SHORT).show()
        //btnListaAlunos.visibility = View.VISIBLE
        textNome.setText("")
        notaUm.setText("")
        notaDois.setText("")
        Log.d("INFO", "Total alunos = ${ turma.alunos.size }")
    }


}