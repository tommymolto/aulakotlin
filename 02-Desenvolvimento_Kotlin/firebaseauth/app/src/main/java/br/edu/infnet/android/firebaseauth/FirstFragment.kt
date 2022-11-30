package br.edu.infnet.android.firebaseauth

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import br.edu.infnet.android.firebaseauth.databinding.FragmentFirstBinding
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private lateinit var auth: FirebaseAuth
    private var EMAIL = "email";
    private lateinit var user: String;
    val vm: UserViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        binding.btEsqueciSenha.setOnClickListener {


            //logica de esqueci minha senha
            //val email = binding.txtEmail.text.trim().toString()
            if(binding.txtEmail.text.trim().toString().isNotEmpty()){
                auth.sendPasswordResetEmail(binding.txtEmail.text.trim().toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            showSnackbar( "Email enviado.", Snackbar.LENGTH_SHORT)
                        }else{
                            showSnackbar( "Email não cadastrado.", Snackbar.LENGTH_SHORT)
                        }
                    }
            }else{
                showSnackbar( "Por favor entre com seu email.", Snackbar.LENGTH_SHORT)
            }

        }
        binding.btLogin.setOnClickListener {
            val email = binding.txtEmail.text.trim().toString()
            val senha = binding.txtSenha.text.trim().toString()
            //logica de autenticacao
            auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                         user = auth.currentUser.toString()
                        vm.id = auth.currentUser?.uid!!
                        vm.email = auth.currentUser?.email.toString()
                        findNavController().navigate(R.id.SecondFragment )

                    } else {
                        Toast.makeText(requireContext(), "Email ou senha invalidos.",
                            Toast.LENGTH_SHORT).show()

                    }
                }
        }
        binding.btnCadastrar.setOnClickListener {
            val email = binding.txtEmail.text.trim().toString()
            val senha = binding.txtSenha
            val repitasenha = binding.txtRepitaSenha

            //se as senhas nao sao iguais
            //    avise como erro
            //    sai da logica de cadastro
            //senao, continue
            //logica de autenticacao

            if(senha.text.trim().toString() != repitasenha.text.trim().toString()){
                senha.error = "As senhas nao são identicas"
                repitasenha.error = "As senhas nao são identicas"
            }else{
                auth.createUserWithEmailAndPassword(email, senha.text.trim().toString())
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            user = auth.currentUser.toString()
                            vm.id = auth.currentUser?.uid!!
                            vm.email = auth.currentUser?.email.toString()
                            findNavController().navigate(R.id.SecondFragment )
                            findNavController().navigate(R.id.SecondFragment )

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(requireContext(), "Cadastro mal sucedido.",
                                Toast.LENGTH_SHORT).show()

                        }
                    }
            }


        }
        // Initialize Facebook Login button
        var loginButton :LoginButton= binding.loginButton
        loginButton.setReadPermissions(listOf("email"));
        // Callback registration
        // Callback registration
        var callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {


            override fun onCancel() {
                // App code
            }

            override fun onError(exception: FacebookException) {
                // App code
            }

                override fun onSuccess(result: LoginResult?) {
                    handleFacebookAccessToken(result?.accessToken!!)
                    Log.d("INFO", "RESULTADO=${result?.accessToken?.token}")
                }
            })
        return binding.root

    }
    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    Log.d(TAG, "${ user.toString()}")

                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)

                    //updateUI(null)
                }
            }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun showSnackbar( message: String?, duration: Int) {
        Snackbar.make(requireView(), message!!, duration).show()
    }
}