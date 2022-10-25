package com.example.storage.ui.dashboard

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.storage.R
import com.example.storage.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnSaveSP.setOnClickListener {
            _ ->
            val texto = binding.txtInputText.text.trim().toString()
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            with (sharedPref?.edit()) {
                this?.putString("textosalvo", texto)
                this?.commit()
            }
        }
        binding.btnShowSP.setOnClickListener {
            _ ->
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            val texto = sharedPref?.getString("textosalvo", "Não Encontrado")
            binding.txtSP.text = texto
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}