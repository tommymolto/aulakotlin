package br.edu.infnet.android.firebaseinfnet.ui.dashboard

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.edu.infnet.android.firebaseinfnet.databinding.FragmentDashboardBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
private const val REQUEST_CODE_IMAGE_PICK = 0

class DashboardFragment : Fragment() {

    private var _binding: br.edu.infnet.android.firebaseinfnet.databinding.FragmentDashboardBinding? = null
    private val db = Firebase.firestore
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var saidaBanco = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.btnCadastrarNovoUsuario.setOnClickListener {
            adicionaUsuarios()
        }
        binding.btnCadastrarMock.setOnClickListener {
            adicionaUsuariosMock()
        }
        binding.btnListar.setOnClickListener {
            leDados()
        }

        return root
    }
    fun adicionaUsuarios() {
        val newUser = hashMapOf(
            "primeiro_nome" to binding.etPrimeiroNome.text.trim().toString(),
            "ultimo_nome" to binding.etUltimoNome.text.trim().toString(),
            "ano_nascimento" to Integer.parseInt(binding.etAnoNascimento.text.trim().toString())
        )
        db.collection("perfil")
            .add(newUser)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
    }
    fun adicionaUsuariosMock(){
        val user1 = hashMapOf(
            "primeiro_nome" to "Ada",
            "ultimo_nome" to "Lovelace",
            "ano_nascimento" to 1978
        )

// Add a new document with a generated ID
        db.collection("perfil")
            .add(user1)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                leDados()
            }
        val user2 = hashMapOf(
            "primeiro_nome" to "Ada",
            "nome_do_meio" to "M",
            "ultimo_nome" to "Lovelace",
            "ano_nascimento" to 1978
        )

// Add a new document with a generated ID
        db.collection("perfil")
            .add(user2)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
    }
    fun leDados(){
        saidaBanco=""
        db.collection("perfil")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    saidaBanco += "${document.id} => ${document.data} \r\n\r\n"
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                }
                binding.textView.text = saidaBanco
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}