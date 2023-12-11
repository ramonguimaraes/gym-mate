package com.ramonguimaraes.gymmate.exercises.presenter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ramonguimaraes.gymmate.R
import com.ramonguimaraes.gymmate.core.utils.Result
import com.ramonguimaraes.gymmate.core.utils.format
import com.ramonguimaraes.gymmate.databinding.FragmentExercisesBinding
import com.ramonguimaraes.gymmate.exercises.presenter.viewModel.ExercisesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExercisesFragment : Fragment() {

    private val viewModel: ExercisesViewModel by viewModel()
    private val adapter: ExerciseAdapter = ExerciseAdapter()
    private val args: ExercisesFragmentArgs by navArgs()
    private val binding: FragmentExercisesBinding by lazy {
        FragmentExercisesBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        showWorkoutData()
        configureRecycler()
        viewModel.getAll(args.workout.id)
        viewModel.exercises.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    if (it.data.isEmpty()) {
                        adapter.submitList(null)
                    } else {
                        adapter.submitList(it.data.toMutableList())
                    }
                }

                is Result.Loading -> {}
                is Result.Error -> Toast.makeText(
                    context, "Falha ao buscar exercicios", Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.fabAdd.setOnClickListener {
            val action =
                ExercisesFragmentDirections.actionExercisesFragmentToExerciseFragment(args.workout.id)
            findNavController().navigate(action)
        }

        adapter.setItemClickListener { exercise ->
            val action = ExercisesFragmentDirections.actionExercisesFragmentToExerciseFragment(
                args.workout.id,
                exercise
            )
            findNavController().navigate(action)
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun showWorkoutData() {
        with(args.workout) {
            binding.txtName.text = name
            binding.txtDescription.text = description
            binding.txtDate.text = this.date.format()
        }
    }

    private fun configureRecycler() {
        binding.rvExercises.adapter = adapter
        binding.rvExercises.layoutManager = LinearLayoutManager(context)
    }
}
