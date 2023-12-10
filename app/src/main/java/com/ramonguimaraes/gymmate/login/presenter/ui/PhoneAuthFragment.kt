package com.ramonguimaraes.gymmate.login.presenter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.ramonguimaraes.gymmate.databinding.FragmentPhoneAuthBinding
import com.ramonguimaraes.gymmate.login.presenter.viewModel.PhoneAuthViewModel
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class PhoneAuthFragment : Fragment() {

    private val firebaseAuth: FirebaseAuth by inject()
    private val viewModel: PhoneAuthViewModel by viewModels()
    private val binding: FragmentPhoneAuthBinding by lazy {
        FragmentPhoneAuthBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.btnSendOtp.setOnClickListener {
            val phoneNumber = binding.edtPhone.text.toString()
            if (viewModel.validatePhoneNumber(phoneNumber)) {
                singUpWithPhone(phoneNumber)
            } else {
                binding.edtPhone.error = "Número inválido"
            }
        }

        return binding.root
    }

    private fun singUpWithPhone(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    val action =
                        PhoneAuthFragmentDirections.actionPhoneAuthFragmentToCodeVerificationFragment(
                            verificationId,
                            phoneNumber
                        )
                    findNavController().navigate(action)
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}
