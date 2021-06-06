package com.gratus.home.di

import com.gratus.browse.data.remote.GetDirectionSourceImpl
import com.gratus.browse.data.remote.GetVehiclePointSourceImpl
import com.gratus.core.data.remote.api.DirectionsService
import com.gratus.core.data.remote.api.VehiclePointsService
import com.gratus.home.data.HomeRepoImpl
import com.gratus.home.domain.HomeRepository
import com.gratus.home.interactors.GetDirectionsUseCase
import com.gratus.home.interactors.GetVehiclePointUseCase
import com.gratus.home.presentation.VehicleListAdapter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [HomeFeatureModule::class])
class HomeModule {
    @Provides
    fun provideVehiclePointsService(retrofit: Retrofit): VehiclePointsService {
        return retrofit.create(VehiclePointsService::class.java)
    }

    @Provides
    fun provideDirectionService(retrofit: Retrofit): DirectionsService {
        return retrofit.create(DirectionsService::class.java)
    }

    // repo provider
    @Provides
    fun provideHomeRepository(
        getVehiclePointSourceImpl: GetVehiclePointSourceImpl,
        getDirectionSourceImpl: GetDirectionSourceImpl
    ): HomeRepository {
        return HomeRepoImpl(
            getVehiclePointSourceImpl,
            getDirectionSourceImpl
        )
    }

    @Provides
    fun providesGetVehiclePointUseCase(homeRepository: HomeRepository): GetVehiclePointUseCase {
        return GetVehiclePointUseCase(homeRepository)
    }

    @Provides
    fun providesGetDirectionsUseCase(homeRepository: HomeRepository): GetDirectionsUseCase {
        return GetDirectionsUseCase(homeRepository)
    }

    @Provides
    fun provideVehicleListAdapter(): VehicleListAdapter {
        return VehicleListAdapter(ArrayList())
    }
}
