package com.example.retrofitapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.retrofitapp.R
import com.example.retrofitapp.ViewModel.MainViewModel
import com.example.retrofitapp.databinding.RegisterfragmentBinding
import com.example.retrofitapp.models.data.Local.LocalStorage
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RegisterFragment: Fragment(R.layout.registerfragment) {

    private lateinit var binding: RegisterfragmentBinding
    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = RegisterfragmentBinding.bind(view)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[MainViewModel::class.java]

        initObservers()

        binding.apply {
            btnBackLogin.setOnClickListener {
             findNavController().popBackStack()
            }
            next.setOnClickListener {
                if (nameReg.text.toString().isNotEmpty() && phoneReg.text.toString()
                        .isNotEmpty() && passwordReg.text.toString().isNotEmpty()
                ) {
                    lifecycleScope.launchWhenResumed {
                        viewModel.isSuccess(
                            name = nameReg.text.toString(),
                            password = passwordReg.text.toString(),
                            phone = phoneReg.text.toString()
                        )
                    }
                    findNavController().navigate(
                        R.id.action_registerFragment_to_secondFragment
                    )
                } else {
                    Toast.makeText(requireContext(), "Toltirin", Toast.LENGTH_SHORT).show()
                }
            }
        }

            }

private fun initObservers() {
    viewModel.getSuccessFlow.onEach {
        Log.w("TTTT", it)
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }.launchIn(lifecycleScope)
}
}