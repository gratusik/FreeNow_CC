package com.gratus.core.util.network

import com.gratus.core.util.network.NetworkHelper.CONNECTION_TIME_OUT
import com.gratus.core.util.network.NetworkHelper.ERROR_KEY
import com.gratus.core.util.network.NetworkHelper.GENERIC_ERROR
import com.gratus.core.util.network.NetworkHelper.MESSAGE_KEY
import com.gratus.core.util.network.NetworkHelper.NETWORK
import com.gratus.core.util.network.NetworkHelper.NETWORK_MESSAGE
import com.gratus.core.util.network.NetworkHelper.SESSION_EXPIRED
import com.gratus.core.util.network.NetworkHelper.TIMEOUT
import com.gratus.core.util.network.NetworkHelper.UNKNOWN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

object NetworkHelper {
    const val MESSAGE_KEY = "message"
    const val ERROR_KEY = "error"
    const val NETWORK = "NETWORK"
    const val TIMEOUT = "TIMEOUT"
    const val SESSION_EXPIRED = "SESSION_EXPIRED"
    const val UNKNOWN = "UNKNOWN"
    const val NETWORK_MESSAGE = "NETWORK_MESSAGE"
    const val GENERIC_ERROR = "Something went wrong please try again!"
    const val CONNECTION_TIME_OUT = "Failed to connect to server please check you network!"
}

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(val code: String, val message: String? = null) :
        ResultWrapper<Nothing>()
}

suspend inline fun <T> safeApiCall(
    crossinline apiCall: suspend () -> T?
): ResultWrapper<T?> {
    return try {
        val response = withContext(Dispatchers.IO) { apiCall.invoke() }
        ResultWrapper.Success(response)
    } catch (e: Exception) {
        withContext(Dispatchers.Main) {
            when (e) {
                is HttpException -> {
                    if (e.code() == 401) ResultWrapper.GenericError(
                        SESSION_EXPIRED,
                        getErrorMessage(SESSION_EXPIRED)
                    )
                    else {
                        val body = e.response()?.errorBody()
                        ResultWrapper.GenericError(
                            NETWORK_MESSAGE,
                            getNetworkErrorMessage(body)
                        )
                    }
                }
                is SocketTimeoutException -> ResultWrapper.GenericError(
                    TIMEOUT,
                    getErrorMessage(TIMEOUT)
                )
                is IOException -> ResultWrapper.GenericError(
                    NETWORK,
                    getErrorMessage(NETWORK)
                )
                else -> ResultWrapper.GenericError(
                    UNKNOWN,
                    getErrorMessage(UNKNOWN)
                )
            }
        }
    }
}

fun getNetworkErrorMessage(responseBody: ResponseBody?): String {
    return try {
        val jsonObject = JSONObject(responseBody!!.string())
        when {
            jsonObject.has(MESSAGE_KEY) -> jsonObject.getString(MESSAGE_KEY)
            jsonObject.has(ERROR_KEY) -> jsonObject.getString(ERROR_KEY)
            else -> GENERIC_ERROR
        }
    } catch (e: Exception) {
        GENERIC_ERROR
    }
}

fun getErrorMessage(errorType: String): String {
    return try {
        when (errorType) {
            SESSION_EXPIRED -> {
                CONNECTION_TIME_OUT
            }
            TIMEOUT -> {
                CONNECTION_TIME_OUT
            }
            NETWORK -> {
                CONNECTION_TIME_OUT
            }
            else -> {
                GENERIC_ERROR
            }
        }
    } catch (e: Exception) {
        GENERIC_ERROR
    }
}
