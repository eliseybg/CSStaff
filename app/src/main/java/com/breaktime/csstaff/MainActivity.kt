package com.breaktime.csstaff

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.breaktime.csstaff.adapter.ItemData
import com.breaktime.csstaff.adapter.ListAdapter
import com.breaktime.csstaff.api.RetrofitInstance
import com.breaktime.csstaff.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val inProgressAdapter = ListAdapter(false, Picasso.get())
        binding.inProgressList.layoutManager = LinearLayoutManager(this)
        binding.inProgressList.adapter = inProgressAdapter

        val readyAdapter = ListAdapter(true, Picasso.get())
        binding.readyList.layoutManager = LinearLayoutManager(this)
        binding.readyList.adapter = readyAdapter

        MainScope().launch(Dispatchers.IO) {
            val menuRequest = RetrofitInstance.API.getMenu()
            val ordersRequest = RetrofitInstance.API.getOrders()
            val menu = menuRequest.await()
            println(menu)
            val shopOrders = ordersRequest.await()
            println(shopOrders)
            val inProgressItems = shopOrders.orders.filter { order ->
                order.endoff.toInt() * 1000 < System.currentTimeMillis()
            }.map { order ->
                val images = mutableSetOf<String>()
                order.products.forEach {
                    it.productid.toIntOrNull()?.let { pos ->
                        if (pos < menu.size)
                            images.add(menu[pos].image)
                    }
                }
                ItemData(images, order.orderid)
            }
            val readyItems = shopOrders.orders.filter { order ->
                order.endoff.toInt() * 1000 < System.currentTimeMillis()
            }.map { order ->
                val images = mutableSetOf<String>()
                order.products.forEach {
                    it.productid.toIntOrNull()?.let { pos ->
                        if (pos < menu.size)
                            images.add(menu[pos].image)
                    }
                }
                ItemData(images, order.orderid)
            }
            withContext(Dispatchers.Main) {
                readyAdapter.items = readyItems
                inProgressAdapter.items = inProgressItems
            }
        }
    }
}