package com.ramonguimaraes.gymmate.login.presenter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ramonguimaraes.gymmate.core.Result
import com.ramonguimaraes.gymmate.databinding.FragmentLoginBinding
import com.ramonguimaraes.gymmate.login.presenter.viewModel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModel()
    private val binding: FragmentLoginBinding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.loginResult.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    Toast.makeText(requireContext(), "Logado", Toast.LENGTH_SHORT).show()
                }

                is Result.Error -> {
                    Toast.makeText(requireContext(), "Falha ao fazer login", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.emailError.observe(viewLifecycleOwner) {
            binding.edtEmail.error = it
        }

        viewModel.passwordError.observe(viewLifecycleOwner) {
            binding.edtPassword.error = it
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            viewModel.login(email, password)
        }

        return binding.root
    }
}
