package com.example.storage.ui.notifications

import android.Manifest
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.storage.databinding.FragmentNotificationsBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    val filename = "meuarquivo.txt"

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

      binding.btnSaveLocally.setOnClickListener {
          val texto = binding.txtMultiline.text.trim().toString()

          context?.openFileOutput(filename, Context.MODE_PRIVATE).use {
              it?.write(texto.toByteArray())
          }
      }
        binding.btnReadLocally.setOnClickListener {
            _ ->
            val conteudo = context?.openFileInput(filename)?.bufferedReader()?.useLines { lines ->
                lines.fold("") { some, text ->
                    "$some\n$text"
                }
            }
           // binding.txtMultiline.setText(conteudo)
            binding.txtConteudo.text =conteudo
        }
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {

                } else {

                }
            }

        binding.btnSaveExternally.setOnClickListener {
            _ ->
            if (isWriteExternalStoragePermissionGranted()) {
                val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val texto = binding.txtMultiline.text.trim().toString()
                val fis = FileOutputStream(File("$downloadDir/$filename"))
                fis.use {
                    it?.write(texto.toByteArray())
                }
            } else {
               // ActivityCompat.requestPermissions(requireActivity(),  arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),  WRITE_EXTERNAL_STORAGE)
                requestPermissionLauncher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
        binding.btnReadExternally.setOnClickListener {
            if (isWriteExternalStoragePermissionGranted()) {
                val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val texto = binding.txtMultiline.text.trim().toString()
                val fis = FileInputStream(File("$downloadDir/$filename"))
                val conteudo = fis.use {
                    it.bufferedReader().useLines { lines ->
                        lines.fold("") { some, text ->
                            "$some\n$text"
                        }
                    }
                }

                //binding.txtMultiline.setText(conteudo)
                binding.txtConteudo.text =conteudo

            } else {
                // ActivityCompat.requestPermissions(requireActivity(),  arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),  WRITE_EXTERNAL_STORAGE)
                requestPermissionLauncher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
        return root
    }

    private fun isWriteExternalStoragePermissionGranted(): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(requireContext(),  WRITE_EXTERNAL_STORAGE)
        return permissionCheck == PackageManager.PERMISSION_GRANTED
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}