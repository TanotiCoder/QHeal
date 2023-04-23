package com.example.qheal.di

import android.content.Context
import androidx.room.Room
import com.example.qheal.data.local.PostDao
import com.example.qheal.data.local.PostDatabase
import com.example.qheal.utils.Utils.BASE_URL
import com.example.qheal.utils.Utils.GIST_URL
import com.example.qheal.data.remote.ApiService
import com.example.qheal.data.remote.ApiServiceAuthorChipName
import com.example.qheal.data.remote.WikiAuthorDetailApiService
import com.example.qheal.repository.AuthorChipNameRepository
import com.example.qheal.repository.HomeRepository
import com.example.qheal.repository.LocalPostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    //==========================Local Database===========

    @Singleton
    @Provides
    fun provideLocalDatabase(@ApplicationContext context: Context): PostDatabase {
        return Room.databaseBuilder(context, PostDatabase::class.java, "postEntity")
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun providePostDao(postDatabase: PostDatabase) = postDatabase.postDao()

    @Singleton
    @Provides
    fun providePostRepository(postDao: PostDao): LocalPostRepository =
        LocalPostRepository(postDao = postDao)
    //==========================Network Call=============

    @Singleton
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .callTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient).build().create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthorName(okHttpClient: OkHttpClient): ApiServiceAuthorChipName {
        return Retrofit.Builder().baseUrl(GIST_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient).build().create(ApiServiceAuthorChipName::class.java)

    }

    @Provides
    @Singleton
    fun provideWikiAuthorDetailApi(okHttpClient: OkHttpClient): WikiAuthorDetailApiService =
        Retrofit.Builder()
            .baseUrl("https://en.wikipedia.org/api/rest_v1/")
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)
            .build().create(WikiAuthorDetailApiService::class.java)

    //=======================Repository=================

    @Singleton
    @Provides
    fun provideHomeRepository(
        apiService: ApiService,
        wikiAuthorDetailApiService: WikiAuthorDetailApiService
    ): HomeRepository = HomeRepository(apiService, wikiAuthorDetailApiService)

    @Singleton
    @Provides
    fun provideAuthorNameRepository(apiServiceAuthorChipName: ApiServiceAuthorChipName) =
        AuthorChipNameRepository(apiServiceAuthorChipName)
}