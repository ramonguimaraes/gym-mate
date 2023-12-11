package com.ramonguimaraes.gymmate.workout.presenter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.fragment.app.DialogFragment
import com.ramonguimaraes.gymmate.databinding.WorkoutDialogLayoutBinding
import com.ramonguimaraes.gymmate.workout.domain.model.Workout

class WorkoutDialog(private val workout: Workout? = null) : DialogFragment() {

    private var saveListener: (workout: Workout) -> Unit = {}
    fun setSaveClickListener(saveListener: (workout: Workout) -> Unit) {
        this.saveListener = saveListener
    }

    private val binding: WorkoutDialogLayoutBinding by lazy {
        WorkoutDialogLayoutBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureDialog()

        binding.edtName.setText(workout?.name ?: "")
        binding.edtDescription.setText(workout?.description ?: "")

        binding.btnSave.setOnClickListener {
            if (validateFields()) {
                val name = binding.edtName.text.toString()
                val description = binding.edtDescription.text.toString()
                saveListener.invoke(
                    workout?.copy(
                        name = name,
                        description = description
                    ) ?: Workout(
                        name = name,
                        description = description
                    )
                )
                dismiss()
            }
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun validateFields(): Boolean {
        var isValid = true
        val name = binding.edtName.text.toString()
        val description = binding.edtDescription.text.toString()

        if (name.isBlank()) {
            isValid = false
            binding.edtName.error = "Campo obrigatorio"
        }

        if (description.isBlank()) {
            isValid = false
            binding.edtDescription.error = "Campo obrigatorio"
        }

        return isValid
    }

    private fun configureDialog() {
        dialog?.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    companion object {
        const val TAG: String = "workout_dialog_tag"
    }
}