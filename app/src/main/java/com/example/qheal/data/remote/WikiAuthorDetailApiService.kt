package com.example.qheal.data.remote

import com.example.qheal.data.remote.responce.AuthorDetailReponce
import retrofit2.http.GET
import retrofit2.http.Path

interface WikiAuthorDetailApiService {
    //https://en.wikipedia.org/api/rest_v1/page/summary/A._P._J._Abdul_Kalam
    @GET("page/summary/{name}")
    suspend fun getAuthorDetail(@Path("name") name: String): AuthorDetailReponce
}