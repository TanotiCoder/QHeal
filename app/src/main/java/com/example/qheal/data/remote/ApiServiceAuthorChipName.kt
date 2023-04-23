package com.example.qheal.data.remote

import com.example.qheal.data.remote.responce.AuthorChipNameResponse
import retrofit2.http.GET

interface ApiServiceAuthorChipName{
    @GET("AuthorName.json")
    suspend fun getAuthorName(): AuthorChipNameResponse
}