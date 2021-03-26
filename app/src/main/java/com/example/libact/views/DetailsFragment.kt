package com.example.libact.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import androidx.fragment.app.Fragment
import com.example.libact.R
import com.example.libact.databinding.LibDetailsFragmentBinding
import com.example.libact.modelsview.LibViewModel
import kotlinx.android.synthetic.main.lib_details_fragment.*

class DetailsFragment():Fragment(R.layout.details_item) {
    var libViewModel = LibViewModel()
    private var _binding: LibDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LibDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = context?.let { ArrayAdapter<String>(it,R.layout.details_item,R.id.details_tv,libViewModel.translateList) }
        detailsListView.adapter = adapter
    }
}