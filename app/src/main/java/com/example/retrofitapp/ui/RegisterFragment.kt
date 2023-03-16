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
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment: Fragment(R.layout.registerfragment) {

    private lateinit var binding: RegisterfragmentBinding

    private val viewModel by viewModel<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = RegisterfragmentBinding.bind(view)

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
                } else {
                  Toast.makeText(requireContext(), "Toltirin", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }
    fun initObservers() {
        viewModel.getSuccessFlow.onEach {
            LocalStorage().token = it
            LocalStorage().isLogin = true
            LocalStorage().isDone = false
            findNavController().navigate(
                R.id.action_registerFragment_to_secondFragment
            )
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()

        }.launchIn(lifecycleScope)
    }
}
