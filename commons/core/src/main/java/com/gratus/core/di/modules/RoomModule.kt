package com.gratus.core

import android.content.Context
import androidx.room.Room
import com.gratus.core.data.local.dao.UserDao
import com.gratus.core.data.local.db.FreeNowDataBase
import com.gratus.core.util.CoreConstants.DatabaseConstant.FREE_NOW_DB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun providesFreeNowDataBase(context: Context): FreeNowDataBase {
        return Room.databaseBuilder(
            context,
            FreeNowDataBase::class.java,
            FREE_NOW_DB
        )
            .build()
    }

    @Singleton
    @Provides
    fun providesUserDao(freeNowDataBase: FreeNowDataBase): UserDao {
        return freeNowDataBase.userDao()
    }
}
