package br.edu.infnet.android.firebaseinfnet.ui.home

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.infnet.android.firebaseinfnet.ImageAdapter
import br.edu.infnet.android.firebaseinfnet.databinding.FragmentHomeBinding
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
private const val REQUEST_CODE_IMAGE_PICK = 0

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
//firabse


    val storageReference = FirebaseStorage.getInstance().reference
    var imageUrls = mutableListOf<Map<String,String>>()
    var currentFile: Uri? = null

    val imageRef = Firebase.storage.reference


    var SELECT_PICTURE = 200
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.ivImage.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                startActivityForResult(it, REQUEST_CODE_IMAGE_PICK)
            }
        }

        binding.btnUploadImage.setOnClickListener {
            uploadImageToStorage("myImage").invokeOnCompletion {
                listFiles()
            }

        }



        binding.btnDeleteImage.setOnClickListener {
            deleteImage(currentFile.toString()).invokeOnCompletion {
                listFiles()
            }
        }

        listFiles()

        return root
    }
    private fun listFiles() = CoroutineScope(Dispatchers.IO).launch {
        imageUrls = mutableListOf<Map<String,String>>()
        try {
            val images = imageRef.child("livros/").listAll().await()

            for(image in images.items) {
                val url = image.downloadUrl.await()
                val urlFriendly = image.name
                imageUrls.add(mapOf( "url" to url.toString(), "name" to urlFriendly ))
            }
            withContext(Dispatchers.Main) {
                val imageAdapter = ImageAdapter(imageUrls, requireContext() , ::setaImagem
                )
                binding.rvImages.apply {
                    adapter = imageAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                }
            }
        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun deleteImage(filename: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            Log.d("Info", "Deletando $filename")
            imageRef.child("livros/$filename").delete().await()
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "Imagem deletada.",
                    Toast.LENGTH_LONG).show()
            }
        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun downloadImage(filename: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val maxDownloadSize = 5L * 1024 * 1024
            val bytes = imageRef.child("livros/$filename").getBytes(maxDownloadSize).await()
            val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            withContext(Dispatchers.Main) {
                binding.ivImage.setImageBitmap(bmp)
            }
        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun setaImagem(filename: Map<String, String>) : Unit {
//        val maxDownloadSize = 5L * 1024 * 1024
//        val bytes = imageRef.child("livros/$filename").getBytes(maxDownloadSize).await()
//        val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        currentFile = Uri.parse(filename["name"])
//        binding.ivImage.setImageBitmap(bmp)
        Glide.with(requireActivity()).load(filename["url"]).into(binding.ivImage)
    }


    private fun uploadImageToStorage(filename: String) = CoroutineScope(Dispatchers.IO).launch {
        val file_name = "${(1..100000000).random()}_$filename"
        try {
            currentFile?.let {
                val x = imageRef.child("livros/$file_name").putFile(it).await()
                //imageUrls.add(mapOf( "url" to url.toString(), "name" to file_name ))
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Imagem $file_name subida",
                        Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_IMAGE_PICK) {
            data?.data?.let {
                currentFile = it
                binding.ivImage.setImageURI(it)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}