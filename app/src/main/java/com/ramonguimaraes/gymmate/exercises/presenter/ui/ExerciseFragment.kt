package com.ramonguimaraes.gymmate.exercises.presenter.ui

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.ramonguimaraes.gymmate.R
import com.ramonguimaraes.gymmate.core.utils.Result
import com.ramonguimaraes.gymmate.databinding.FragmentExerciseBinding
import com.ramonguimaraes.gymmate.exercises.domain.model.Exercise
import com.ramonguimaraes.gymmate.exercises.presenter.viewModel.EditExerciseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExerciseFragment : Fragment() {

    private val viewModel: EditExerciseViewModel by viewModel()
    private val args: ExerciseFragmentArgs by navArgs()
    private val binding: FragmentExerciseBinding by lazy {
        FragmentExerciseBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setObservers()

        if (args.exercise != null) {
            binding.edtName.setText(args.exercise?.name.toString())
            binding.edtDescription.setText(args.exercise?.description.toString())
            binding.btnDelete.visibility = View.VISIBLE
        }

        val registry = takePictureForResult()
        binding.btnGallery.setOnClickListener {
            registry.launch(
                CropImageContractOptions(
                    Uri.EMPTY,
                    CropImageOptions(
                        imageSourceIncludeCamera = false,
                        cropShape = CropImageView.CropShape.RECTANGLE,
                        fixAspectRatio = true
                    )
                )
            )
        }

        binding.btnSave.setOnClickListener {
            val name = binding.edtName.text.toString()
            val description = binding.edtDescription.text.toString()

            val exercise = args.exercise?.copy(
                name = name, description = description, workoutId = args.workoutId
            ) ?: Exercise(
                name = name, description = description, workoutId = args.workoutId
            )

            viewModel.save(exercise)
        }

        binding.btnDelete.setOnClickListener {
            args.exercise?.let { exercise -> viewModel.delete(exercise) }
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }


        return binding.root
    }

    private fun setObservers() {

        viewModel.deleteResult.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    findNavController().popBackStack()
                }

                is Result.Loading -> {
                    showLoading()
                }

                is Result.Error -> {
                    Toast.makeText(context, "Falha ao deletar", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.saveResult.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    findNavController().popBackStack()
                }

                is Result.Loading -> {
                    showLoading()
                }

                is Result.Error -> {
                    Toast.makeText(context, "Falha ao salvar", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun takePictureForResult(): ActivityResultLauncher<CropImageContractOptions> {
        return registerForActivityResult(CropImageContract()) { result ->
            if (result.isSuccessful) {
                viewModel.setImgUri(result.uriContent)
            }
        }
    }
}