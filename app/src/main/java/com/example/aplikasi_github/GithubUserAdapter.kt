package com.example.aplikasi_github

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.ScrollingTabContainerView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.github_user_item.*
import java.util.*

class GithubUserAdapter (private val context: Context, private val items : List<GithubUser>,
                         private val listener: (GithubUser) -> Unit):
        RecyclerView.Adapter<GithubUserAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GithubUserAdapter.ViewHolder(context,
    LayoutInflater.from(context).inflate(R.layout.github_user_item, parent,
    false))


    override fun getItemCount(): Int {
       return items.size
    }

    override fun onBindViewHolder(holder: GithubUserAdapter.ViewHolder, position: Int) {
       holder.bindItem(items.get(position), listener)
    }

    class ViewHolder(val context: Context, override val containerView: View):
        RecyclerView.ViewHolder(containerView), LayoutContainer{

        var txtUsername : TextView = containerView.findViewById(R.id.txtUsername)
        var txtUserRepo : TextView = containerView.findViewById(R.id.txtRepo)
        var imgUser : ImageView = containerView.findViewById(R.id.imgUser)

        fun bindItem(item: GithubUser, listener: (GithubUser) -> Unit){

            txtUsername.text = item.login
            txtUserRepo.text = item.reposUrl

            Glide.with(context).load(item.avatarUrl).into(imgUser)
            containerView.setOnClickListener {listener(item)}
        }

    }


}