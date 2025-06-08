package com.swalisoft.payer.common.di

import com.swalisoft.payer.auth.login.data.LoginRepositoryImp
import com.swalisoft.payer.auth.login.data.remote.LoginHttpDataSource
import com.swalisoft.payer.auth.login.domain.LoginRepository
import com.swalisoft.payer.auth.login.presentation.LoginViewModel
import com.swalisoft.payer.common.data.HttpClientFactory
import com.swalisoft.payer.component.todo.presentation.ToDoViewModel
import com.swalisoft.payer.home.presentation.HomeViewModel
import com.swalisoft.payer.note.data.remote.CategoryHttpDataSource
import com.swalisoft.payer.note.data.CategoryRepositoryImpl
import com.swalisoft.payer.note.domain.CategoryNoteRepository
import com.swalisoft.payer.note.domain.NoteRepository
import com.swalisoft.payer.ocr.presentation.OcrViewModel
import com.swalisoft.payer.note.presentation.CategoryViewModel
import com.swalisoft.payer.ocr.data.OrcRepositoryImp
import com.swalisoft.payer.ocr.data.remote.OrcHttpDataSource
import com.swalisoft.payer.ocr.domain.OcrRepository
import com.swalisoft.payer.profile.presentation.ProfileViewModel
import com.swalisoft.payer.profile.data.ProfileHttpDataSource
import com.swalisoft.payer.profile.data.ProfileRepositoryImpl
import com.swalisoft.payer.profile.domain.ProfileRepository
import com.swalisoft.payer.profile.domain.GetProfileUseCase
import com.swalisoft.payer.profile.domain.UpdateProfileUseCase
import com.swalisoft.payer.profile.domain.ChangePasswordUseCase
import com.swalisoft.payer.profile.domain.DeleteProfileUseCase
import com.swalisoft.payer.common.getStorageManager
import com.swalisoft.payer.notification.data.NotificationHttpDataSource
import com.swalisoft.payer.notification.data.NotificationRepositoryImp
import com.swalisoft.payer.notification.domain.NotificationRepository
import com.swalisoft.payer.notification.presentation.NotificationsViewModel
import com.swalisoft.payer.todo.data.TaskHttpDataSource
import com.swalisoft.payer.todo.data.ToDoRepositoryImp
import com.swalisoft.payer.todo.domain.ToDoRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import com.swalisoft.payer.note.data.remote.NoteHttpDataSource
import com.swalisoft.payer.note.data.NoteRepositoryImpl
import com.swalisoft.payer.note.presentation.NoteViewModel

expect val platformModule: Module

val sharedModule = module {
  single { HttpClientFactory.create(get()) }
  single<suspend () -> String> { { getStorageManager().getAuthToken() ?: "" } }

  viewModelOf(::HomeViewModel)
  viewModelOf(::LoginViewModel)

  // Auth
  singleOf(::LoginHttpDataSource)
  singleOf(::LoginRepositoryImp).bind<LoginRepository>()

  // Category
  singleOf(::CategoryHttpDataSource)
  viewModelOf(::CategoryViewModel)
  singleOf(::CategoryRepositoryImpl).bind<CategoryNoteRepository>()

  //Ocr
  singleOf(::OrcHttpDataSource)
  viewModelOf(::OcrViewModel)
  singleOf(::OrcRepositoryImp).bind<OcrRepository>()

  // Profile feature
  singleOf(::ProfileHttpDataSource)
  singleOf(::ProfileRepositoryImpl).bind<ProfileRepository>()
//  single { GetProfileUseCase(get()) }
//  single { UpdateProfileUseCase(get()) }
//  single { ChangePasswordUseCase(get()) }
//  single { DeleteProfileUseCase(get()) }
//  single<suspend () -> String> { { getStorageManager().getAuthToken() ?: "" } }
  viewModelOf(::ProfileViewModel)

  // Notification feature
  singleOf(::NotificationHttpDataSource)
  singleOf(::NotificationRepositoryImp).bind<NotificationRepository>()
  viewModelOf(::NotificationsViewModel)

  singleOf(::NoteHttpDataSource)
  singleOf(::NoteRepositoryImpl).bind<NoteRepository>()
  viewModelOf(::NoteViewModel)

  // ToDo feature
  singleOf(::TaskHttpDataSource)
  singleOf(::ToDoRepositoryImp).bind<ToDoRepository>()
  viewModelOf(::ToDoViewModel)
}
