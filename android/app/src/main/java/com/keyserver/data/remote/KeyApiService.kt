package com.keyserver.data.remote

import com.keyserver.data.model.KeyResponse
import retrofit2.http.*

interface KeyApiService {
    @GET("keys")
    suspend fun getKeys(): KeyResponse

    @POST("keys/generate")
    suspend fun generateKey(@Body request: Any): KeyResponse
}
