@file:Suppress("SpellCheckingInspection")

package com.gratus.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gratus.core.domain.local.UserEntity

// DAO for room datatbase user table
@Dao
interface UserDao {
    // insert dao new user
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userEntity: UserEntity)

    // find based on username and password  for login
    @Query("SELECT * FROM user_table WHERE email = :email and password= :password")
    fun getFindUser(email: String, password: String): UserEntity?

    // find based on username before inserting to db
    @Query("SELECT * FROM user_table WHERE email = :email")
    fun getCheckUser(email: String): List<UserEntity>

    fun insertCheck(userEntity: UserEntity): Boolean {
        val itemsFromDB: List<UserEntity> = userEntity.email.let { getCheckUser(it) }
        return if (itemsFromDB.isEmpty()) {
            insert(userEntity)
            true
        } else {
            false
        }
    }
}
