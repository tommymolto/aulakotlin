package com.example.modulo1.ui.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import com.example.modulo1.R
import com.example.modulo1.databinding.ActivityProfileBinding
import com.example.modulo1.ui.login.LoginActivity
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val pickImage = 100
    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("INFO", "CHEGUEI")
        super.onCreate(savedInstanceState)
        binding =  ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = this.getSharedPreferences("moduloum", Context.MODE_PRIVATE)
        if(sharedPref.getString("imagem", "erro") == "erro"){
            Log.d("INFO", "SEM SHARED!")
            redirecionaLogin()
        }
        binding.imageView.setImageResource(R.drawable.ic_launcher_foreground)

        //val conteudo = File( "${applicationContext.filesDir}/dados").inputStream().readBytes().toString(Charsets.UTF_8)
        if(File("${applicationContext.filesDir}/dados").exists()){
            Log.d("INFO","EXISTE")
            val data = Uri.fromFile(File("${applicationContext.filesDir}/dados"))
            binding.imageView.setImageURI(data)

        }

        val texto = sharedPref?.getString("imagem", "Não Encontrado")
        binding.imageView.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
        binding.button.setOnClickListener {
            _ ->
            logout()
        }

    }
    fun salvaImagem(data: Intent?){
        val inputStream: InputStream =
            applicationContext.contentResolver.openInputStream(data?.data!!)!!
        val file: File = File("${  applicationContext.filesDir}/dados") // from somewhere
        val bytes = inputStream.readBytes()
        file.writeBytes(bytes)
        Log.d("FILE=", file.absolutePath)
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        with (sharedPref?.edit()) {
            this?.putString("imagem", file.absolutePath)
            this?.commit()
        }

    }
    fun logout(){
        val sharedPref = this.getSharedPreferences("moduloum", Context.MODE_PRIVATE)
        with (sharedPref?.edit()) {
            this?.clear()
            this?.commit()
        }
        redirecionaLogin()
    }




    private fun redirecionaLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        // intent.putExtra("keyIdentifier", value)
        startActivity(intent)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {

            imageUri = data?.data
            salvaImagem(data)
            binding.imageView.setImageURI(imageUri)
            salvaImagem(data)
        }
    }
}