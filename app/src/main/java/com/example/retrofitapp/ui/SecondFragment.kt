package com.example.retrofitapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.retrofitapp.R
import com.example.retrofitapp.ViewModel.MainViewModel
import com.example.retrofitapp.ViewModel.TasksViewModel
import com.example.retrofitapp.databinding.SecondfragmentBinding
import com.example.retrofitapp.models.data.TaskData
import com.example.retrofitapp.ui.Adapter.TaskAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class SecondFragment: Fragment(R.layout.secondfragment) {
    private lateinit var binding: SecondfragmentBinding

    private val viewModel by viewModel<TasksViewModel>()
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
        initObservers()

        lifecycleScope.launchWhenResumed {
            viewModel.getAllTasks()
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(
                R.id.action_secondFragment_to_addTaskFragment
            )
        }

        binding.btnBackLogin.setOnClickListener {
            findNavController().navigate(
                SecondFragmentDirections.actionSecondFragmentToFirstFragment()
            )
        }
    }
    private fun initObservers() {
        viewModel.getAllTasksFlow.onEach {
            adapter.submitList(it)
        }.launchIn(lifecycleScope)
    }
}