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
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FirstFragment:Fragment(R.layout.firstfragment) {
   private lateinit var binding: FirstfragmentBinding

   private val viewModel by viewModel<MainViewModel>()
   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding = FirstfragmentBinding.bind(view)

      initObservers()

      binding.apply {
         nextReg.setOnClickListener {
            findNavController().navigate(
               FirstFragmentDirections.actionFirstFragmentToRegisterFragment()
            )
         }
         try {
            nextPage.setOnClickListener {
               val password = password.text.toString()
               val phone = phoneNumber.text.toString()
               if (password.isNotEmpty() && phone.isNotEmpty()) {
                  lifecycleScope.launch {
                     viewModel.isLogin(phone, password)
                  }
               } else {
                  Toast.makeText(requireContext(), "Login ya Parol qate", Toast.LENGTH_SHORT).show()
               }
            }
         } catch (e: Exception){
            e.printStackTrace()
         }
      }
   }
   private fun initObservers() {
      viewModel.getLoginFlow.onEach {
         LocalStorage().token = it
         LocalStorage().isLogin = true
         findNavController().navigate(
            FirstFragmentDirections.actionFirstFragmentToSecondFragment()
         )
      }.launchIn(lifecycleScope)
   }
}