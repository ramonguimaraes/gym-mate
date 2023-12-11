package com.ramonguimaraes.gymmate.workout.di

import com.ramonguimaraes.gymmate.workout.data.dataSource.WorkoutDataSource
import com.ramonguimaraes.gymmate.workout.data.dataSource.WorkoutDataSourceImpl
import com.ramonguimaraes.gymmate.workout.data.repository.WorkoutDataRepository
import com.ramonguimaraes.gymmate.workout.domain.repository.WorkoutRepository
import com.ramonguimaraes.gymmate.workout.presenter.viewModel.WorkoutViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun workoutModule() = module {
    factory<WorkoutDataSource> { WorkoutDataSourceImpl(get()) }
    factory<WorkoutRepository> { WorkoutDataRepository(get()) }
    viewModel { WorkoutViewModel(get(), get()) }
}