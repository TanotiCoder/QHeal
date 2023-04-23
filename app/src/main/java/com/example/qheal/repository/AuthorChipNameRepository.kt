package com.example.qheal.repository

import com.example.qheal.utils.ResourceState
import com.example.qheal.data.remote.ApiServiceAuthorChipName
import com.example.qheal.data.remote.responce.AuthorChipNameResponse
import retrofit2.HttpException
import javax.inject.Inject

class AuthorChipNameRepository @Inject constructor(private val apiServiceAuthorChipName: ApiServiceAuthorChipName) {
    suspend fun getAuthorChipName(): ResourceState<AuthorChipNameResponse> {
        return try {
            ResourceState.Success(apiServiceAuthorChipName.getAuthorName())
        }catch (e:Exception){
            ResourceState.Error(e.localizedMessage!!)
        }catch (e:HttpException){
            ResourceState.Error(e.localizedMessage!!)
        }
    }
}