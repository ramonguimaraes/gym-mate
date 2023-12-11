package com.ramonguimaraes.gymmate.workout.presenter.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ramonguimaraes.gymmate.core.utils.DefaultDiffCallback
import com.ramonguimaraes.gymmate.databinding.WorkoutItemBinding
import com.ramonguimaraes.gymmate.workout.domain.model.Workout

class WorkoutAdapter : ListAdapter<Workout, WorkoutAdapter.WorkoutViewHolder>(
    DefaultDiffCallback<Workout>()
) {

    private var optionsListener: (view: View, item: Workout) -> Unit = { _: View, _: Workout -> }
    fun setOptionsClickListener(optionsListener: (view: View, item: Workout) -> Unit) {
        this.optionsListener = optionsListener
    }

    private var itemClickListener: () -> Unit = {}
    fun setItemClickListener(itemClickListener: () -> Unit) {
        this.itemClickListener = itemClickListener
    }

    override fun submitList(list: MutableList<Workout>?) {
        super.submitList(list ?: arrayListOf())
    }

    class WorkoutViewHolder(
        private val binding: WorkoutItemBinding,
        private val optionsListener: (view: View, item: Workout) -> Unit,
        private val itemClickListener: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Workout) {
            binding.txtName.text = item.name
            binding.txtDescription.text = item.description
            binding.btnOptions.setOnClickListener { optionsListener.invoke(it, item) }
            binding.root.setOnClickListener { itemClickListener.invoke() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = WorkoutItemBinding.inflate(inflater, parent, false)
        return WorkoutViewHolder(binding, optionsListener, itemClickListener)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}