package com.gratus.credentials.di

import com.gratus.browse.data.CredentialRepoImpl
import com.gratus.browse.data.local.CredentialLocalSourceImpl
import com.gratus.credentials.domain.CredentialRepository
import com.gratus.credentials.interactors.LocalCacheUseCase
import dagger.Module
import dagger.Provides

@Module(includes = [CredentialsFeatureModule::class])
class CredentialsModule {

    // repo provider
    @Provides
    fun provideCredentialRepository(
        credentialLocalSourceImpl: CredentialLocalSourceImpl
    ): CredentialRepository {
        return CredentialRepoImpl(
            credentialLocalSourceImpl
        )
    }

    @Provides
    fun providesLocalCacheUseCase(credentialRepository: CredentialRepository): LocalCacheUseCase {
        return LocalCacheUseCase(credentialRepository)
    }
}
