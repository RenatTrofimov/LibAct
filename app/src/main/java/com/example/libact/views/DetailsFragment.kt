package com.example.libact.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.libact.R
import com.example.libact.databinding.LibDetailsFragmentBinding
import com.example.libact.modelsview.LibViewModel
import com.example.libact.surface.Tree
import kotlinx.android.synthetic.main.lib_details_fragment.*

class DetailsFragment():Fragment(R.layout.details_item) {

    lateinit var libViewModel:LibViewModel
    private var _binding: LibDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("LibViewModel", "Called ViewModelProvider.get")
        libViewModel = ViewModelProvider(requireActivity()).get(LibViewModel::class.java)
        _binding = LibDetailsFragmentBinding.inflate(inflater, container, false)
        _binding!!.libViewModel = libViewModel
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind()
    }
    fun bind(){
        binding.libViewModel = libViewModel
        MyHolstForTest.set(libViewModel.tree)
        binding.invalidateAll()
    }
}