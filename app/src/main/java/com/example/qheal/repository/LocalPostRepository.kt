package com.example.qheal.repository

import com.example.qheal.data.local.PostDao
import com.example.qheal.data.local.PostEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalPostRepository @Inject constructor(private val postDao: PostDao){
    suspend fun insertPostInRoom(postEntity: PostEntity){
        postDao.insertPost(postEntity = postEntity)
    }
    suspend fun removePostFromRoom(id:String){
        postDao.removePost(id = id)
    }
    suspend fun isPostExist(id: String):Int{
        return postDao.existPost(id)
    }
    fun getAllPostFromRoom(): Flow<List<PostEntity>> {
        return postDao.getAllPost()
    }
}