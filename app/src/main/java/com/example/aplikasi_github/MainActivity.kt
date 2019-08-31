package com.example.aplikasi_github

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        callApiGetGithubUser()
    }

    private fun callApiGetGithubUser(){
        showLoading(this, swipeRefreshlayout)

        val httpClient = httpClient()
        val apiRequest = apiRequest<GithubService>(httpClient)
        val call = apiRequest.getUsers()
        call.enqueue(object : Callback<List<GithubUser>>{
            override fun onFailure(call: Call<List<GithubUser>>, t: Throwable){
                dismissLoading(swipeRefreshlayout)
            }

            override fun onResponse(call: Call<List<GithubUser>>, response: Response<List<GithubUser>>){
                dismissLoading(swipeRefreshlayout)
                when{
                    response.isSuccessful ->
                        when{
                            response.body()?.size != 0 -> tampilGithubUser(response.body()!!)
                            else -> tampilToast(applicationContext, "Berhasil Menampilkan Data")
                        }
                    else -> tampilToast(applicationContext, "Gagal Menampilkan Data")
                }
            }
        })
    }

    private fun tampilGithubUser(githubUser: List<GithubUser>)
    {
        listGithubUser.layoutManager = GridLayoutManager(this, 2)
        listGithubUser.adapter = GithubUserAdapter(this, githubUser){
            val github = it
            tampilToast(applicationContext, github.login)
        }
    }

    private fun tampilToast(context: Context,text: String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT)
    }

    private fun showLoading(context: Context, swipeRefreshLayout: SwipeRefreshLayout ){
        swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(context, R.color.colorPrimary))
        swipeRefreshLayout.isEnabled = true
        swipeRefreshLayout.isRefreshing = true
    }

    private fun dismissLoading(swipeRefreshLayout: SwipeRefreshLayout){
        swipeRefreshLayout.isEnabled = false
        swipeRefreshLayout.isRefreshing = false
    }
}
