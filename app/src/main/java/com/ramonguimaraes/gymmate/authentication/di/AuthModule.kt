package com.ramonguimaraes.gymmate.authentication.di

import com.ramonguimaraes.gymmate.authentication.data.dataSource.AuthDataSource
import com.ramonguimaraes.gymmate.authentication.data.dataSource.AuthDataSourceImpl
import com.ramonguimaraes.gymmate.authentication.data.repository.AuthDataRepository
import com.ramonguimaraes.gymmate.authentication.domain.repository.AuthRepository
import com.ramonguimaraes.gymmate.authentication.presenter.viewModel.CodeVerificationViewModel
import com.ramonguimaraes.gymmate.authentication.presenter.viewModel.CreateAccountViewModel
import com.ramonguimaraes.gymmate.authentication.presenter.viewModel.LoginViewModel
import com.ramonguimaraes.gymmate.authentication.presenter.viewModel.PhoneAuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

fun authModule(): Module = module {
    single<AuthDataSource> { AuthDataSourceImpl(get()) }
    single<AuthRepository> { AuthDataRepository(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { CreateAccountViewModel(get()) }
    viewModel { PhoneAuthViewModel() }
    viewModel { CodeVerificationViewModel(get()) }
}
