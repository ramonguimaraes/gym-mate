package com.ramonguimaraes.gymmate.login.presenter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ramonguimaraes.gymmate.core.Result
import com.ramonguimaraes.gymmate.databinding.FragmentCodeVerificationBinding
import com.ramonguimaraes.gymmate.login.presenter.viewModel.CodeVerificationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CodeVerificationFragment : Fragment() {

    private val args: CodeVerificationFragmentArgs by navArgs()
    private val viewModel: CodeVerificationViewModel by viewModel()

    private val binding: FragmentCodeVerificationBinding by lazy {
        FragmentCodeVerificationBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.singUpWithPhoneResult.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    Toast.makeText(requireContext(), "Seja bem vindo", Toast.LENGTH_SHORT).show()
                }

                is Result.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Falha ao verificar o código",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        configureCodeFields()

        binding.btnVerifyCode.setOnClickListener {
            val code = getCode()
            val verificationId = args.verificationId
            viewModel.verifyCode(verificationId, code)
        }

        return binding.root
    }

    private fun configureCodeFields() {
        val codeFields = listOf(
            binding.edtFirstDigit,
            binding.edtSecondDigit,
            binding.edtThirdDigit,
            binding.edtFourthDigit,
            binding.edtFifthDigit,
            binding.edtSixthDigit
        )

        codeFields.forEachIndexed { index, editText ->
            editText.doOnTextChanged { text, _, _, _ ->
                if (text?.length == 1 && index < codeFields.size - 1) {
                    codeFields[index + 1].requestFocus()
                }
            }
        }
    }

    private fun getCode(): String {
        val digits = listOf(
            binding.edtFirstDigit.text.toString(),
            binding.edtSecondDigit.text.toString(),
            binding.edtThirdDigit.text.toString(),
            binding.edtFourthDigit.text.toString(),
            binding.edtFifthDigit.text.toString(),
            binding.edtSixthDigit.text.toString()
        )

        return if (digits.none { it.isBlank() }) {
            digits.joinToString("")
        } else {
            ""
        }
    }
}
