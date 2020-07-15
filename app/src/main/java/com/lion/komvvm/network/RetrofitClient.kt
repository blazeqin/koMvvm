package com.lion.komvvm.network

import com.lion.komvvm.BuildConfig
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {
    companion object{
        /**
         * use static inner class to get the single instance
         */
        fun getInstance() = SingletonHolder.INSTANCE
    }

    private val mRetrofit by lazy {
        Retrofit.Builder()
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.baseUrl)
            .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(20L, TimeUnit.SECONDS)
            .writeTimeout(20L, TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(8,20,TimeUnit.SECONDS))
            .build()
    }

    /**
     * use dynamic proxy to create service
     */
    fun <T> create(service: Class<T>) = mRetrofit.create(service)

    private object SingletonHolder{
        val INSTANCE = RetrofitClient()
    }
}