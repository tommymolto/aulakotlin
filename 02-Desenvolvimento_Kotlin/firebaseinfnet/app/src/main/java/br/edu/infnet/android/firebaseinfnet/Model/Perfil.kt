package br.edu.infnet.android.firebaseinfnet.Model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot


data class Perfil(val userId: String, //Document ID is actually the user id
                val primeiroNome: String,
                val ultimoNome: String,
                val anoNascimento: Number?)  {

    companion object {
        fun DocumentSnapshot.toPerfil(): Perfil {
                val primeiroNome = getString("primeiro_nome")!!
                val ultimoNome = getString("ultimo_nome")!!
                val anoNascimento = getString("ano_nascimento")?.toInt()

                return Perfil(id, primeiroNome, ultimoNome, anoNascimento)

        }
        private const val TAG = "Perfil"
    }
}