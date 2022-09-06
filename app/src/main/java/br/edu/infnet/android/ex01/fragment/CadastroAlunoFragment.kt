package br.edu.infnet.android.ex01.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.edu.infnet.android.ex01.HomeActivity
import br.edu.infnet.android.ex01.R
import br.edu.infnet.android.ex01.model.Aluno
import br.edu.infnet.android.ex01.model.Turma
import br.edu.infnet.android.ex01.viewmodel.TurmaViewModel
import kotlinx.android.synthetic.main.fragment_cadastro_aluno.*
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


/**
 * A simple [Fragment] subclass.
 * Use the [CadastroAlunoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CadastroAlunoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private  val turmaViewModel: TurmaViewModel by activityViewModels()

    private lateinit var turma: Turma

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

        Log.d("INFO", "Cliquei")
        return inflater.inflate(R.layout.fragment_cadastro_aluno, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        activity?.let {act->
//            turmaViewModel = ViewModelProvider(this).get(TurmaViewModel::class.java)
//            }
        btnSalvaUsuario.setOnClickListener {
                view -> salvarUsuario(view)
        }

    }

    fun salvarUsuario( view: View){
        var novoAluno = Aluno(
            textNome.text.toString(),
            notaUm.text.toString().toDouble(),
            notaDois.text.toString().toDouble()

        )
        turmaViewModel.addAluno(novoAluno)
        //turma.alunos.add(novoAluno)
        Toast.makeText(view.context, "Aluno Salvo.", Toast.LENGTH_SHORT).show()
        //btnListaAlunos.visibility = View.VISIBLE
        textNome.setText("")
        notaUm.setText("")
        notaDois.setText("")
        Log.d("INFO", "Total alunos = ${ turmaViewModel.alunos.value?.size }")
    }


}
