package com.lion.mvvmlib.network

import android.net.ParseException
import android.util.MalformedJsonException
import com.google.gson.JsonParseException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

/**
 * 处理异常信息
 */
object ExceptionHandle {

    fun handleException(e: Throwable): ResponseThrowable =
        ResponseThrowable(
            when (e) {
                is HttpException -> Error.HTTP_ERROR
                is JsonParseException, is JSONException, is ParseException, is MalformedJsonException -> Error.PARSE_ERROR
                is ConnectException -> Error.NETWORK_ERROR
                is SSLException -> Error.SSL_ERROR
                is ConnectTimeoutException, is SocketTimeoutException, is UnknownHostException -> Error.TIMEOUT_ERROR
                else -> Error.UNKNOWN
            }
        )
}