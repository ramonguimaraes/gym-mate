package com.ramonguimaraes.gymmate.exercises.presenter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ramonguimaraes.gymmate.R
import com.ramonguimaraes.gymmate.core.utils.DefaultDiffCallback
import com.ramonguimaraes.gymmate.databinding.ExerciseItemBinding
import com.ramonguimaraes.gymmate.exercises.domain.model.Exercise
import com.squareup.picasso.Picasso

class ExerciseAdapter : ListAdapter<Exercise, ExerciseAdapter.ExerciseViewHolder>(
    DefaultDiffCallback<Exercise>()
) {

    private var itemClickListener: (item: Exercise) -> Unit = {}
    fun setItemClickListener(itemClickListener: (item: Exercise) -> Unit) {
        this.itemClickListener = itemClickListener
    }

    override fun submitList(list: MutableList<Exercise>?) {
        super.submitList(list ?: arrayListOf())
    }

    class ExerciseViewHolder(
        private val binding: ExerciseItemBinding,
        private var itemClickListener: (item: Exercise) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Exercise) {
            binding.txtName.text = item.name
            binding.txtDescription.text = item.description
            Picasso.get()
                .load(item.imgUri)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(binding.imgExercise)
            binding.root.setOnClickListener {
                itemClickListener.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ExerciseItemBinding.inflate(inflater, parent, false)
        return ExerciseViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}