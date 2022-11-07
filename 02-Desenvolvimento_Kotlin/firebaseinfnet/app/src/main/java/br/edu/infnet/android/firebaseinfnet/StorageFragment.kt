package br.edu.infnet.android.firebaseinfnet

import android.R.attr
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import com.google.firebase.storage.ktx.component3
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import br.edu.infnet.android.firebaseinfnet.databinding.FragmentStorageBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.io.FileDescriptor
import java.io.IOException
import android.content.ContentResolver
import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.pm.PackageManager

import android.os.Build

import android.view.View.OnLongClickListener
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlin.random.Random


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
    private lateinit var recyclerView: RecyclerView
    private lateinit var tm: ImagemAdapter
    private lateinit var listImages : MutableList<String>
    //firabse
    val storageReference = FirebaseStorage.getInstance().reference


    var SELECT_PICTURE = 200

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding    = FragmentStorageBinding.inflate(inflater, container, false)
        listImages = mutableListOf()
        binding.BSelectImage.setOnClickListener(View.OnClickListener {
            imageChooser()
        })
        recyclerView= binding.rvImagens
        configureRecyclerView()
        // Inflate the layout for this fragment
        return binding.root
    }
    private fun configureRecyclerView(){
        //Log.d("configureRecyclerView()","${viewModel.exposeItems().size}")
        listImages = listarArquivos()
        listImages.add("ppp")
        tm = ImagemAdapter(listImages)
        recyclerView.layoutManager  = LinearLayoutManager(activity)
        recyclerView.adapter        = tm
        recyclerView.addItemDecoration(
            DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        )
        tm.notifyDataSetChanged()


    }
    private fun imageChooser() {

        // create an instance of the
        // intent of the type image
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE)
    }
    fun uploadImageToCloudStorage(bitmap: Bitmap?){
        if (bitmap != null){

            // Nome do Objeto/Arquivo no Cloud Storage
            // Se já existe, substitui, caso contrário, cria
            val xx = Random(43434).toString().replace("@","")
            val file_name = "${xx}.jpeg"

            // Objeto para compressão do bitmap
            val baos = ByteArrayOutputStream()

            // Compressão do bitmap
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)

            // Pegando a referência do serviço
            var mStorageRef = storageReference

            // Apontando para o objeto
            val bitmap = mStorageRef!!
                .child("livros/${file_name}")
            val imageStorageRef = mStorageRef!!
                .child("livros/${file_name}")
            // Chamando o método put que retorna um UploadTask
            val ulTask = imageStorageRef.putBytes(baos.toByteArray())

            ulTask.addOnSuccessListener {
                Log.i("Upload", "Carregou com sucesso.")
            }
                .addOnFailureListener {
                    Log.e("Upload", it.message.toString())
                }
        }
    }
    // this function is triggered when user
    // selects the image from the imageChooser
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                val selectedImageUri: Uri? = data?.data
                if (null != selectedImageUri) {
                    // update the preview image in the layout

                    var bm = uriToBitmap(selectedImageUri)
                    uploadImageToCloudStorage(bm)
                    listarArquivos()
                    binding.IVPreviewImage.setImageURI(selectedImageUri)
                }
            }
        }
    }
    private fun uriToBitmap(selectedFileUri: Uri): Bitmap? {
        try {
            val parcelFileDescriptor = context?.contentResolver?.openFileDescriptor(selectedFileUri, "r")
            val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
            val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor.close()
            return image
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
    private fun listarArquivos() : MutableList<String>{
        val storage = Firebase.storage
        val listRef = storage.reference.child("livros")
        var ls = List<String>()
// You'll need to import com.google.firebase.storage.ktx.component1 and
// com.google.firebase.storage.ktx.component2
        listRef.listAll()
            .addOnSuccessListener {
                    (items, prefixes) ->
                prefixes.forEach { prefix ->
                    // All the prefixes under listRef.
                    // You may call listAll() recursively on them.
                }

                items.forEach { item ->
                    ls.add(item.path)
                    Log.d( "INFO", "TENHO Item ${item.path}")
                }
            }
            .addOnFailureListener {
                // Uh-oh, an error occurred!
            }
        return  ls

    }
}