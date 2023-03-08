package com.example.retrofitapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.retrofitapp.R
import com.example.retrofitapp.ViewModel.MainViewModel
import com.example.retrofitapp.databinding.FirstfragmentBinding
import com.example.retrofitapp.models.data.Local.LocalStorage
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FirstFragment:Fragment(R.layout.firstfragment) {
   private lateinit var binding: FirstfragmentBinding
   private lateinit var viewModel: MainViewModel

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding = FirstfragmentBinding.bind(view)

      viewModel = ViewModelProvider(
         requireActivity(),
         ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
      )[MainViewModel::class.java]


      initObservers()

      binding.apply {

         nextPage.setOnClickListener {
            val password = loginPassword.toString()
            val phone = loginGmail.toString()
            if (password.isNotEmpty() && phone.isNotEmpty()) {
               lifecycleScope.launchWhenResumed {
                  viewModel.isLogin(phone, password)
               }
               findNavController().navigate(
                  R.id.action_firstFragment_to_secondFragment
               )
            } else {
               Toast.makeText(requireContext(), "Login ya Parol qate", Toast.LENGTH_SHORT).show()
            }
         }
         nextReg.setOnClickListener {
            findNavController().navigate(
               R.id.action_firstFragment_to_registerFragment
            )
         }
      }
   }

   private fun initObservers() {
      viewModel.getLoginFlow.onEach {
         LocalStorage().token = it
         LocalStorage().isLogin = true
         findNavController().navigate(
            R.id.action_firstFragment_to_secondFragment
         )
         Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
      }.launchIn(lifecycleScope)


   }
}