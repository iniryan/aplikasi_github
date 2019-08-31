package com.example.aplikasi_github

import retrofit2.Call
import retrofit2.http.GET

interface  GithubService{

    @GET("users")
    fun getUsers(): Call<List<GithubUser>>




}
