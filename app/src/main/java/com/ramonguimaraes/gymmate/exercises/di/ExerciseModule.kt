package com.ramonguimaraes.gymmate.exercises.di

import com.ramonguimaraes.gymmate.exercises.data.ExerciseDataSourceImpl
import com.ramonguimaraes.gymmate.exercises.data.ExercisesDataRepository
import com.ramonguimaraes.gymmate.exercises.data.ExercisesDataSource
import com.ramonguimaraes.gymmate.exercises.domain.repository.ExercisesRepository
import com.ramonguimaraes.gymmate.exercises.presenter.viewModel.EditExerciseViewModel
import com.ramonguimaraes.gymmate.exercises.presenter.viewModel.ExercisesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun exerciseModule() = module {
    factory<ExercisesDataSource> { ExerciseDataSourceImpl(get(), get()) }
    factory<ExercisesRepository> { ExercisesDataRepository(get()) }
    viewModel { ExercisesViewModel(get(), get()) }
    viewModel { EditExerciseViewModel(get(), get()) }
}