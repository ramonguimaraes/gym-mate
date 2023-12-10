package com.ramonguimaraes.gymmate.authentication.presenter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ramonguimaraes.gymmate.R
import com.ramonguimaraes.gymmate.core.utils.Result
import com.ramonguimaraes.gymmate.databinding.FragmentCreateAccountBinding
import com.ramonguimaraes.gymmate.authentication.presenter.viewModel.CreateAccountViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateAccountFragment : Fragment() {

    private val viewModel: CreateAccountViewModel by viewModel()
    private val binding: FragmentCreateAccountBinding by lazy {
        FragmentCreateAccountBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel.createAccountResult.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Success -> {
                    Toast.makeText(requireContext(), "Verifique seu email", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_createAccountFragment_to_loginFragment)
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), "Erro ao criar a conta", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.emailError.observe(viewLifecycleOwner) {
            binding.edtEmail.error = it
        }

        viewModel.passwordError.observe(viewLifecycleOwner) {
            binding.edtPassword.error = it
        }

        viewModel.repeatPasswordError.observe(viewLifecycleOwner) {
            binding.edtRepeatPassword.error = it
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_createAccountFragment_to_loginFragment)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val repeatPassword = binding.edtRepeatPassword.text.toString()
            viewModel.createAccount(email, password, repeatPassword)
        }

        return binding.root
    }
}
