package com.example.homeworkpart2

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkpart2.databinding.AlertMenuBinding
import com.example.homeworkpart2.databinding.ContactCardBinding
import com.github.javafaker.Faker

class RecyclerAdapter(val context: Context) : RecyclerView.Adapter<RecyclerAdapter.Holder>() {
    var oldContactsList = listOf<ContactModel>()
    var itemsForDelete = mutableListOf<ContactModel>()
    val faker = Faker.instance()
    val contactsList = (1..100).map {
        ContactModel(
            id = it,
            name = faker.name().firstName(),
            surName = faker.name().lastName(),
            phoneNumber = faker.phoneNumber().cellPhone()
        )
    }.toMutableList()

    private val _data = MutableLiveData<WhatNeedToDo>()
    val data: LiveData<WhatNeedToDo>
        get() = _data

    private val _isClicked = MutableLiveData<Boolean>()
    val isClicked: LiveData<Boolean>
        get() = _isClicked

    class Holder(item: View) : RecyclerView.ViewHolder(item) {
        val bindind = ContactCardBinding.bind(item)
        fun bind(contact: ContactModel) = with(bindind) {
            tvId.text = contact.id.toString()
            tvName.text = contact.name
            tvSurname.text = contact.surName
            tvPhoneNumber.text = contact.phoneNumber
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_card, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var isClicked = false
        holder.bind(contactsList[position])
        holder.bindind.contactCardView.setOnClickListener {
            if (!isClicked) {
                menuItem(context, holder.adapterPosition)
            }
        }
        holder.bindind.contactCardView.setOnLongClickListener{
            val currentItem = contactsList[holder.adapterPosition]
            if (!isClicked) {
                holder.bindind.contactCardView.setCardBackgroundColor(Color.CYAN)
                itemsForDelete.add(currentItem)
                isClicked = !isClicked
                _isClicked.value = isClicked
            } else {
                holder.bindind.contactCardView.setCardBackgroundColor(Color.WHITE)
                itemsForDelete.remove(currentItem)
                isClicked = !isClicked
                _isClicked.value = isClicked
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    fun setDataToRc(newContactsList: List<ContactModel>) {
        val diffUtil = HomeWorkDiffUtil(oldContactsList, newContactsList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldContactsList = newContactsList
        diffResult.dispatchUpdatesTo(this)
    }

    fun menuItem(context: Context, position: Int) {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogLayout = inflater.inflate(R.layout.alert_menu, null)
        val binding = AlertMenuBinding.bind(dialogLayout)
        val dialog = builder.create()
        binding.apply {
            deleteCard.setOnClickListener {
                _data.value = WhatNeedToDo(
                    "Delete",
                    position
                )
                dialog.dismiss()
            }
            changeCard.setOnClickListener {
                _data.value = WhatNeedToDo(
                    "Change",
                    position
                )
                dialog.dismiss()
            }
        }
        with(dialog) {
            setButton(AlertDialog.BUTTON_POSITIVE, "Ok") { _, _ ->
            }
            setView(dialogLayout)
            show()
        }
    }
}