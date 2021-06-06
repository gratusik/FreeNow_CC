package com.gratus.core.util.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class NetworkBoundResource<T>() {

    fun asFlow(): Flow<Resource<T>> = flow {
        // check if should fetch data from remote or not
        if (shouldFetch()) {
            if (shouldFetchWithLocalData()) { // should emit cached date with loading state or not
                emit(Resource.success(data = localFetch()))
            }

            val remoteResponse = safeApiCall() {
                remoteFetch() // fetch the remote source provided
            }

            when (remoteResponse) {
                is ResultWrapper.Success -> {
                    if (shouldFetchWithLocalData()) {
                        remoteResponse.value?.let {
                            saveFetchResult(remoteResponse.value)
                        }
                    }
                    emit(Resource.success(data = remoteResponse.value))
                }

                is ResultWrapper.GenericError -> {
                    emit(
                        Resource.error(
                            remoteResponse.message, null
                        )
                    )
                }
            }
        } else {
            // get from cache
            emit(Resource.success(data = localFetch()))
        }
    }

    abstract suspend fun remoteFetch(): T?
    abstract suspend fun saveFetchResult(data: T)
    abstract suspend fun localFetch(): T?
    open fun shouldFetch() = true
    open fun shouldFetchWithLocalData() = false
    open fun shouldSaveFetchResult() = false
}
