package com.example.githubopenrepos.ui.recycler

import androidx.recyclerview.widget.DiffUtil
import com.example.githubopenrepos.domain.entity.GitCard

class DiffCallback(oldList: List<GitCard>, newList: List<GitCard>) : DiffUtil.Callback() {
    private val oldList = oldList.toList()
    private val newList = newList.toList()
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldCard: Int, newCard: Int): Boolean {
        return oldList[oldCard].id == newList[newCard].id
    }

    override fun areContentsTheSame(oldCard: Int, newCard: Int): Boolean {
        return oldList[oldCard] == newList[newCard]
    }
}