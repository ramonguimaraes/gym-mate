package com.ramonguimaraes.gymmate.login.di

import com.ramonguimaraes.gymmate.login.data.dataSource.AuthDataSource
import com.ramonguimaraes.gymmate.login.data.dataSource.AuthDataSourceImpl
import com.ramonguimaraes.gymmate.login.data.repository.AuthDataRepository
import com.ramonguimaraes.gymmate.login.domain.repository.AuthRepository
import com.ramonguimaraes.gymmate.login.presenter.viewModel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

fun authModule(): Module = module {
    single<AuthDataSource> { AuthDataSourceImpl(get()) }
    single<AuthRepository> { AuthDataRepository(get()) }
    viewModel { LoginViewModel(get()) }
}
