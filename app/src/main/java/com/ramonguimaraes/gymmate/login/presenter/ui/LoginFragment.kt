package com.ramonguimaraes.gymmate.login.presenter.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.ramonguimaraes.gymmate.BuildConfig
import com.ramonguimaraes.gymmate.R
import com.ramonguimaraes.gymmate.core.Result
import com.ramonguimaraes.gymmate.databinding.FragmentLoginBinding
import com.ramonguimaraes.gymmate.login.presenter.viewModel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModel()
    private lateinit var activityResultLaucher: ActivityResultLauncher<Intent>
    private lateinit var googleSignInClient: GoogleSignInClient
    private val binding: FragmentLoginBinding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_API_KEY)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)
        activityResultLaucher = registerForActivityResult(StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

                try {
                    val account = task.getResult(ApiException::class.java)
                    viewModel.signInWithGoogle(account.idToken)
                } catch (e: Exception) {
                    Toast.makeText(context, "Falha no login", Toast.LENGTH_SHORT).show()
                }
            }
        }
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
                    Toast.makeText(requireContext(), "Falha ao fazer login", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        viewModel.emailError.observe(viewLifecycleOwner) {
            binding.edtEmail.error = it
        }

        viewModel.passwordError.observe(viewLifecycleOwner) {
            binding.edtPassword.error = it
        }

        viewModel.resetPasswordResult.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    Toast.makeText(requireContext(), "Verifique seu email", Toast.LENGTH_SHORT)
                        .show()
                }

                is Result.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Erro ao tentar resetar senha",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            viewModel.login(email, password)
        }

        binding.txtForgetPassword.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            viewModel.resetPassword(email)
        }

        binding.txtCreateAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_createAccountFragment)
        }

        binding.btnPhoneSingUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_phoneAuthFragment)
        }

        binding.btnGoogleSingUp.setOnClickListener {
            activityResultLaucher.launch(googleSignInClient.signInIntent)
        }

        return binding.root
    }
}
