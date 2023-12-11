package com.ramonguimaraes.gymmate.workout.presenter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ramonguimaraes.gymmate.R
import com.ramonguimaraes.gymmate.core.utils.Result
import com.ramonguimaraes.gymmate.databinding.FragmentWorkoutBinding
import com.ramonguimaraes.gymmate.workout.domain.model.Workout
import com.ramonguimaraes.gymmate.workout.presenter.viewModel.WorkoutViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class WorkoutFragment : Fragment() {

    private val viewModel: WorkoutViewModel by viewModel()
    private val binding: FragmentWorkoutBinding by lazy {
        FragmentWorkoutBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val adapter = WorkoutAdapter()
        binding.rvWorkout.adapter = adapter
        binding.rvWorkout.layoutManager = LinearLayoutManager(context)

        viewModel.workout.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    if (it.data.isEmpty()) {
                        adapter.submitList(null)
                        binding.txtEmptyList.visibility = View.VISIBLE
                    } else {
                        binding.txtEmptyList.visibility = View.GONE
                        adapter.submitList(it.data.toMutableList())
                    }
                }
                is Result.Loading -> {}
                is Result.Error -> Toast.makeText(
                    context,
                    "Falha ao carregar os treinos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.saveResult.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    Toast.makeText(
                        context,
                        "Salvo com sucesso",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Result.Loading -> {}
                is Result.Error -> Toast.makeText(context, "Falha ao salvar", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        viewModel.getAll()
        adapter.setItemClickListener { workout ->
            val action = WorkoutFragmentDirections.actionWorkoutFragmentToExercisesFragment(workout)
            findNavController().navigate(action)
        }

        adapter.setOptionsClickListener { view: View, workout: Workout ->
            val popupMenu = PopupMenu(context, view)
            popupMenu.inflate(R.menu.workout_options_menu)
            popupMenu.setOnMenuItemClickListener { option ->
                when (option.itemId) {
                    R.id.delete -> {
                        viewModel.delete(workout)
                        true
                    }

                    R.id.edit -> {
                        showDialog(workout)
                        true
                    }

                    else -> {
                        false
                    }
                }
            }
            popupMenu.show()
        }
        binding.fabAdd.setOnClickListener {
            showDialog(null)
        }
        return binding.root
    }

    private fun showDialog(workout: Workout?) {
        val dialog = WorkoutDialog(workout)
        dialog.setSaveClickListener { viewModel.save(it) }
        dialog.show(childFragmentManager, WorkoutDialog.TAG)
    }
}