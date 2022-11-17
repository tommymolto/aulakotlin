package br.edu.infnet.android.firebaseinfnet.ui.dashboard

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.infnet.android.firebaseinfnet.Model.Perfil
import br.edu.infnet.android.firebaseinfnet.PerfilAdapter
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
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : PerfilAdapter
    private lateinit var perfis : MutableList<Perfil>
    private var _statusEdit: Boolean = false
    private lateinit var _perfil: Perfil

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        perfis = mutableListOf<Perfil>()
        recyclerView= binding.perfilRecycleView
        recyclerView.layoutManager  = LinearLayoutManager(activity)
        adapter = PerfilAdapter(perfis, preparaAtualizacao, deletePerfil)
        recyclerView.adapter        = adapter

        recyclerView.addItemDecoration(
            DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        )
        binding.btnCadastrarNovoUsuario.setOnClickListener {
            if(!_statusEdit){
                adicionaUsuarios()

            }else{
                _perfil = Perfil(
                    userId = _perfil.userId,
                    primeiroNome = binding.etPrimeiroNome.text.trim().toString(),
                    ultimoNome = binding.etUltimoNome.text.trim().toString(),
                    anoNascimento = Integer.parseInt(binding.etAnoNascimento.text.trim().toString())
                )
                updatePerfil(_perfil)
            }
        }
        binding.btnCadastrarMock.setOnClickListener {
            adicionaUsuariosMock()
        }
        binding.btnListar.setOnClickListener {
            //leDados()
            listDados()
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
                /*perfis.add(
                    Perfil(
                    userId = documentReference.id,
                        primeiroNome = binding.etPrimeiroNome.text.trim().toString(),
                        ultimoNome = binding.etUltimoNome.text.trim().toString(),
                        anoNascimento = Integer.parseInt(binding.etAnoNascimento.text.trim().toString())
                ))
                adapter.update(perfis)*/
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
    }
    private var  preparaAtualizacao : (perfil: Perfil) -> Unit = {
        _perfil = it
        _statusEdit = true
        binding.etPrimeiroNome.setText(_perfil.primeiroNome.toString())
        binding.etUltimoNome.setText(_perfil.ultimoNome.toString())
        binding.etAnoNascimento.setText(_perfil.anoNascimento.toString())
    }
    private var deletePerfil : (perfil: Perfil) -> Unit = {
        db.collection("perfil").document(it.userId).delete()
    }
    fun updatePerfil(perfil: Perfil){
        _statusEdit=false
        val docRef = db.collection("perfil").document(perfil.userId)

// Remove the 'capital' field from the document
        val updates = hashMapOf<String, Any>(
            "primeiro_nome" to perfil.primeiroNome,
            "ultimo_nome" to perfil.ultimoNome,
            "ano_nascimento" to perfil.anoNascimento!!,
        )

        docRef.update(updates).addOnCompleteListener {
            //leDados()
            listDados()
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
                //leDados()
                listDados()
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
                perfis = mutableListOf<Perfil>()
                for (document in result) {
                    saidaBanco += "${document.id} => ${document.data} \r\n\r\n"
                    perfis.add(
                        Perfil(
                            userId = document.id,
                            primeiroNome = document.data["primeiro_nome"].toString(),
                            ultimoNome = document.data["ultimo_nome"].toString(),
                            anoNascimento = Integer.parseInt(document.data["ano_nascimento"].toString())
                        ))
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                }
                adapter.update(perfis)
                binding.textView.text = saidaBanco
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }
    fun listDados(){
        saidaBanco=""
        db.collection("perfil")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                perfis = mutableListOf<Perfil>()

                //val cities = ArrayList<String>()
                for (document in value!!) {
                    /*doc.getString("primeiro_nome")?.let {
                        cities.add(it)
                    }*/
                    saidaBanco += "${document.id} => ${document.data} \r\n\r\n"
                    perfis.add(
                        Perfil(
                            userId = document.id,
                            primeiroNome = document.data["primeiro_nome"].toString(),
                            ultimoNome = document.data["ultimo_nome"].toString(),
                            anoNascimento = Integer.parseInt(document.data["ano_nascimento"].toString())
                        ))
                }
                adapter.update(perfis)
                binding.textView.text = saidaBanco
                Log.d(TAG, "$perfis")
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}