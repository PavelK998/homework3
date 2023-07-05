package com.example.homeworkpart2

import androidx.recyclerview.widget.DiffUtil

class HomeWorkDiffUtil(
    private val oldList: List<ContactModel>,
    private val newList: List<ContactModel>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> false
            oldList[oldItemPosition].name != newList[newItemPosition].name -> false
            oldList[oldItemPosition].surName != newList[newItemPosition].surName -> false
            oldList[oldItemPosition].phoneNumber != newList[newItemPosition].phoneNumber -> false
            else -> true
        }
    }
}