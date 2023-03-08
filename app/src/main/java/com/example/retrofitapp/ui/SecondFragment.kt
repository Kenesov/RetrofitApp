package com.example.retrofitapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.retrofitapp.R
import com.example.retrofitapp.ViewModel.TasksViewModel
import com.example.retrofitapp.databinding.SecondfragmentBinding
import com.example.retrofitapp.ui.Adapter.TaskAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SecondFragment: Fragment(R.layout.secondfragment) {
    private lateinit var binding: SecondfragmentBinding
    private lateinit var viewModel: TasksViewModel
    private val adapter = TaskAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SecondfragmentBinding.bind(view)

        binding.recyclerView.adapter = adapter

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                DividerItemDecoration.VERTICAL
            )
        )

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[TasksViewModel::class.java]

        lifecycleScope.launchWhenResumed {
            viewModel.getAllTasks()
        }

        initObservers()

        lifecycleScope.launchWhenResumed {
            viewModel.getAllTasks()
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(
                R.id.action_secondFragment_to_addTaskFragment
            )
        }

        adapter.setOnItemClickListener { id, task, desc ->
            findNavController().navigate(
               AddTaskFragmentDirections.actionAddTaskFragmentToSecondFragment()
            )
        }

    }

    override fun onStop() {
        super.onStop()

        lifecycleScope.launchWhenResumed {
            viewModel.getAllTasks()
        }
    }

    private fun initObservers() {
        viewModel.getAllTasksFlow.onEach {
            adapter.submitList(it)
        }.launchIn(lifecycleScope)
    }
}