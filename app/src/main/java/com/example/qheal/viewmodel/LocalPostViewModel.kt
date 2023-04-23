package com.example.qheal.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qheal.data.local.PostEntity
import com.example.qheal.repository.LocalPostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalPostViewModel @Inject constructor(private val localPostRepository: LocalPostRepository) :
    ViewModel() {
    private var _exist = mutableStateOf(0)
    val exist: State<Int> = _exist

    private val _localPost = mutableStateOf<Flow<List<PostEntity>>>(emptyFlow())
    val localPost: State<Flow<List<PostEntity>>> = _localPost

//    init {
//        getAllPost()
//    }
//
//    private fun getAllPost() {
//        _localPost.value = localPostRepository.getAllPostFromRoom()
//    }

    fun exist(postId: String) {
        viewModelScope.launch {
            _exist.value = localPostRepository.isPostExist(id = postId)
        }
    }

    fun removePostFromRoom(postId: String) {
        viewModelScope.launch {
            localPostRepository.removePostFromRoom(id = postId)
        }.invokeOnCompletion {
            exist(postId = postId)
        }
    }

    fun insertPostInRoom(postEntity: PostEntity) {
        viewModelScope.launch {
            localPostRepository.insertPostInRoom(postEntity = postEntity)
        }.invokeOnCompletion {
            exist(postId = postEntity._id)
        }
    }
}