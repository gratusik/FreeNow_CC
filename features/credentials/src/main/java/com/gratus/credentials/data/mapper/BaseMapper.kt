package com.gratus.credentials.data.mapper

abstract class BaseMapper<in T, out R> {
    abstract fun map(value: T): R?
}
