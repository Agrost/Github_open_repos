package com.example.githubopenrepos.ui.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubopenrepos.R
import com.example.githubopenrepos.domain.entity.GitCard

class RecyclerAdapter(
    val openWebPage: (url: String) -> Unit,
    val sendUrl: (url: String) -> Unit
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var items: MutableList<GitCard> = mutableListOf()
        set(value) {
            val callback = DiffCallback(field, value)
            field = value
            DiffUtil.calculateDiff(callback).dispatchUpdatesTo(this)
        }



    override fun getItemCount(): Int = items.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.github_repo_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val idText = holder.itemView.context
            .getString(R.string.id_text, items[position].id.toString())
        val repoNameText = holder.itemView.context
            .getString(R.string.repo_name_text, items[position].name)
        holder.repoId.text = idText
        holder.repoOwner.text = items[position].login.witCapitalLetter()
        holder.repoName.text = repoNameText
        holder.repoDescription.text = items[position].description
        holder.openLinkButton.setOnClickListener { openWebPage(items[position].url) }
        holder.shareButton.setOnClickListener { sendUrl(items[position].url) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var repoId: TextView = itemView.findViewById(R.id.repo_id)
        var repoName: TextView = itemView.findViewById(R.id.repo_name)
        var repoOwner: TextView = itemView.findViewById(R.id.repo_owner)
        var repoDescription: TextView = itemView.findViewById(R.id.description)
        var openLinkButton: Button = itemView.findViewById(R.id.open_link)
        var shareButton: Button = itemView.findViewById(R.id.share)

    }

    private fun String.witCapitalLetter(): String {
        return this[0].uppercaseChar() + this.substring(1)
    }
}