package br.edu.infnet.android.ex01.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import br.edu.infnet.android.ex01.HomeActivity
import br.edu.infnet.android.ex01.R
import br.edu.infnet.android.ex01.model.Aluno
import br.edu.infnet.android.ex01.model.Turma
import kotlinx.android.synthetic.main.fragment_cadastro_aluno.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CadastroAlunoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CadastroAlunoFragment : Fragment() {
    // TODO: Rename and change types of parameters

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
        btnSalvaUsuario.setOnClickListener {
                view -> salvarUsuario(view)
        }
        btnListaAlunos.setOnClickListener {
                _ -> goToListaAlunos()
        }
    }
    fun goToListaAlunos(){

        /*val profileIntent = Intent(this,
            ListaAlunosActivity::class.java)
        profileIntent.putExtra("turma", Json.encodeToString(turma))

        startActivity(profileIntent)*/
        findNavController().navigate(R.id.AlunoFragmentDest)
    }
    fun salvarUsuario( view: View){
        var novoAluno = Aluno(
            textNome.text.toString(),
            notaUm.text.toString().toDouble(),
            notaDois.text.toString().toDouble()

        )
        turma.alunos.add(novoAluno)
        Toast.makeText(view.context, "Aluno Salvo.", Toast.LENGTH_SHORT).show()
        btnListaAlunos.visibility = View.VISIBLE
        textNome.setText("")
        notaUm.setText("")
        notaDois.setText("")
        Log.d("INFO", "Total alunos = ${ turma.alunos.size }")
    }


}