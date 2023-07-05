package com.example.homeworkpart2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.homeworkpart2.databinding.ActivityMainBinding
import com.example.homeworkpart2.databinding.AlertMenuBinding
import com.github.javafaker.Faker
import java.util.Collections

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rcView: RecyclerView
    private val adapter = RecyclerAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRcView()
        rcView = binding.rcView
        val isClicked = adapter.isClicked.observe(this, Observer {
            if (it && adapter.itemsForDelete.isNotEmpty()){
                invalidateOptionsMenu()
            } else invalidateOptionsMenu()
        })
        val observe = adapter.data.observe(this, Observer {
            when (it.whatNeedToDo) {
                "Change" -> {
                    DialogManager.setContact(context = this, object : DialogManager.Listener {
                        override fun onClick(name: String, surName: String, phoneNumber: String) {
                            if (name.isBlank() || surName.isBlank() || phoneNumber.isBlank()) {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Пожалуйста, заполните все поля",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                adapter.contactsList.removeAt(it.position)
                                adapter.contactsList.add(
                                    it.position,
                                    ContactModel(
                                        it.position + 1, name, surName, phoneNumber
                                    )
                                )
                                adapter.setDataToRc(adapter.contactsList.toList())
                            }
                        }
                    })

                }
                "Delete" -> deleteItem(it.position)
            }
        })
        ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN
                    or ItemTouchHelper.START or ItemTouchHelper.END, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val startPosition = viewHolder.adapterPosition
                val endPosition = target.adapterPosition
                Collections.swap(adapter.contactsList, startPosition, endPosition)
                adapter.setDataToRc(adapter.contactsList.toList())
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.contactsList.removeAt(viewHolder.adapterPosition)
                adapter.setDataToRc(adapter.contactsList.toList())
            }
        }).attachToRecyclerView(rcView)

        binding.btnAdd.setOnClickListener {
            DialogManager.setContact(context = this, object : DialogManager.Listener {
                override fun onClick(name: String, surName: String, phoneNumber: String) {
                    if (name.isBlank() || surName.isBlank() || phoneNumber.isBlank()) {
                        Toast.makeText(
                            this@MainActivity,
                            "Пожалуйста, заполните все поля",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        adapter.contactsList.add(
                            ContactModel(
                                adapter.contactsList.last().id + 1, name, surName, phoneNumber
                            )
                        )
                        adapter.setDataToRc(adapter.contactsList.toList())
                    }
                }
            })
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        val item = menu?.findItem(R.id.delete)
        item?.isVisible = adapter.itemsForDelete.isNotEmpty()
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.delete ->{
               adapter.contactsList.removeAll(adapter.itemsForDelete)
                val test = adapter.contactsList.toList()
                Log.d("asdadsadsdsasdasdzzz", "onOptionsItemSelected: $test")
                adapter.setDataToRc(test)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun initRcView() = with(binding) {
        rcView.adapter = adapter
        rcView.layoutManager = LinearLayoutManager(this@MainActivity)
        val list = adapter.contactsList.toList()
        adapter.setDataToRc(list)
    }
    private fun deleteItem(position: Int) {
        adapter.contactsList.removeAt(position)
        adapter.setDataToRc(adapter.contactsList.toList())
    }
}