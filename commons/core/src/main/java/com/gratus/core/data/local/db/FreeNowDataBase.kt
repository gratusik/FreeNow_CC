package com.gratus.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gratus.core.data.local.dao.UserDao
import com.gratus.core.domain.local.UserEntity

// Database setup and version
@Database(entities = [UserEntity::class], version = 1)
abstract class FreeNowDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao
}
