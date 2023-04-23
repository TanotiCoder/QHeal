package com.example.qheal.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertPost(postEntity: PostEntity)

    @Query("DELETE FROM postEntity WHERE _id =:id")
    suspend fun removePost(id: String)

    @Query("SELECT EXISTS (SELECT 1 FROM postEntity WHERE _id =:id)")
    suspend fun existPost(id: String):Int

    @Query("SELECT * FROM postEntity ORDER BY addedOn DESC")
    fun getAllPost(): Flow<List<PostEntity>>
}

