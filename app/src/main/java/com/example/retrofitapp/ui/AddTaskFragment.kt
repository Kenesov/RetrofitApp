package com.example.retrofitapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.retrofitapp.R
import com.example.retrofitapp.ViewModel.TasksViewModel
import com.example.retrofitapp.databinding.AddtaskFragmentBinding

class AddTaskFragment:Fragment(R.layout.addtask_fragment){


    private lateinit var binding: AddtaskFragmentBinding
    private lateinit var viewModel: TasksViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = AddtaskFragmentBinding.bind(view)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[TasksViewModel::class.java]

        binding.apply {


            taskBtn.setOnClickListener {
                val task = taskText.text.toString()
                val desc = discriptionText.text.toString()
                if (task.isNotEmpty() && desc.isNotEmpty()) {
                    lifecycleScope.launchWhenResumed {

                        viewModel.createTasks(desc, task)
                        findNavController().popBackStack()
                    }
                } else {
                    Toast.makeText(requireContext(), "Toltir", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}