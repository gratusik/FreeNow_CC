package com.gratus.core.domain.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gratus.core.util.CoreConstants.DatabaseConstant.USER_TABLE

@Entity(tableName = USER_TABLE)
// model data class for user data in room db
data class UserEntity(
    val name: String,
    val password: String,
    val email: String,
    val phone: String,
    val loyaltyPoint: Int,
    val freeNowMoney: Double,
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null
)
