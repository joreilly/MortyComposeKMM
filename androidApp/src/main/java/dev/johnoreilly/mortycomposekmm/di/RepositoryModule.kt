package dev.johnoreilly.mortycomposekmm.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.johnoreilly.mortycomposekmm.shared.MortyRepository

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

  @Provides
  @ViewModelScoped
  fun provideMortyRepository(): MortyRepository {
    return MortyRepository()
  }
}
