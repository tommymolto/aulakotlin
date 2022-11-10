package br.edu.infnet.android.firebaseinfnet

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.infnet.android.firebaseinfnet.databinding.FragmentStorageBinding
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StorageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StorageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private  var _binding: FragmentStorageBinding? = null
    private val binding get() = _binding!!

    //firabse
    val storageReference = FirebaseStorage.getInstance().reference
    var imageUrls = mutableListOf<Map<String,String>>()
    var currentFile: Uri? = null

    val imageRef = Firebase.storage.reference


    var SELECT_PICTURE = 200

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }
    fun selectImage(){

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding    = FragmentStorageBinding.inflate(inflater, container, false)




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
            deleteImage(currentFile.toString())
            listFiles()

        }

        listFiles()

        // Inflate the layout for this fragment
        return binding.root
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
                Toast.makeText(requireContext(), "Successfully deleted image.",
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
                imageRef.child("livros/$file_name").putFile(it).await()
                //imageUrls.add(mapOf( "url" to url.toString(), "name" to file_name ))
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Successfully uploaded image $file_name",
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
}