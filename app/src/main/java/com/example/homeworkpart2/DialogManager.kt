package com.example.homeworkpart2

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkpart2.databinding.AlertCardBinding
import com.example.homeworkpart2.databinding.AlertMenuBinding

object DialogManager {
    fun setContact(context: Context, listener: Listener) {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogLayout = inflater.inflate(R.layout.alert_card, null)
        val binding = AlertCardBinding.bind(dialogLayout)
        val dialog = builder.create()
        with(dialog) {
            setTitle("Добавить контакт")
            setButton(AlertDialog.BUTTON_POSITIVE, "Ok") { _, _ ->
                binding.apply {
                    listener.onClick(
                        name = etName.text.toString(),
                        surName = etSurName.text.toString(),
                        phoneNumber = etPhone.text.toString()
                    )
                }
            }
            setView(dialogLayout)
            show()
        }
    }

    interface Listener {
        fun onClick(name: String, surName: String, phoneNumber: String)
    }
}