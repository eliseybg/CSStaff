package com.breaktime.csstaff.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.breaktime.csstaff.databinding.ProductItemBinding
import com.squareup.picasso.Picasso

class ProductAdapter(private val data: ItemData) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            Picasso.get().load(data.products[position].img).fit().centerCrop().into(binding.img)
            binding.size.text = data.products[position].volume
            binding.price.text = data.products[position].price
            binding.name.text = data.products[position].coffee

            val extra = buildString {
                append("+")
                if (data.products[position].milk == "true") {
                    append(", milk")
                }
                if (data.products[position].sugar == "true") {
                    append(", sugar")
                }
                if (data.products[position].syrup == "true") {
                    append(", syrup")
                }
            }
            if (extra.length > 1) {
                binding.extra.text = extra
                binding.extra.visibility = View.VISIBLE
            } else {
                binding.extra.visibility = View.GONE
            }
        }
    }

    override fun getItemCount() = data.products.size

    inner class ViewHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)
}