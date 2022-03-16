package com.breaktime.csstaff.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.breaktime.csstaff.R
import com.breaktime.csstaff.databinding.OrderItemBinding
import com.breaktime.csstaff.px
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso

class ListAdapter(private val isReady: Boolean, private val picasso: Picasso) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    var items: List<ItemData> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        if (isReady)
            binding.root.backgroundTintList =
                binding.root.context.resources.getColorStateList(R.color.orange)
        else
            binding.root.backgroundTintList =
                binding.root.context.resources.getColorStateList(R.color.dark)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.drinkType.removeAllViews()
            items[position].products.forEach { product ->
                val image = ImageView(binding.root.context)
                val layoutParams =
                    LinearLayout.LayoutParams(25.px, 25.px)
                layoutParams.marginStart = 3.px
                layoutParams.marginEnd = 3.px
                image.layoutParams = layoutParams
                binding.drinkType.addView(image)
                picasso.load(product.img).fit().centerCrop().into(image)
            }
            binding.root.setOnClickListener {
                showBottomSheetDialog(binding, position)
            }
            binding.userId.text = items[position].userId
        }
    }


    private fun showBottomSheetDialog(binding: OrderItemBinding, position: Int) {
        val bottomSheetDialog = BottomSheetDialog(binding.root.context)
        bottomSheetDialog.behavior.peekHeight = 800.px
        bottomSheetDialog.setContentView(R.layout.product_list_dialog_layout)

        val backBtn = bottomSheetDialog.findViewById<ImageView>(R.id.back)
        backBtn?.setOnClickListener {
            bottomSheetDialog.hide()
        }
        val qrBtn = bottomSheetDialog.findViewById<Button>(R.id.qr_code)
        qrBtn?.setOnClickListener {
            showBottomSheetQRDialog(binding)
            bottomSheetDialog.hide()
        }
        val list = bottomSheetDialog.findViewById<RecyclerView>(R.id.recyclerView)
        list?.adapter = ProductAdapter(items[position])
        list?.layoutManager = LinearLayoutManager(binding.root.context)
        bottomSheetDialog.show()
    }

    private fun showBottomSheetQRDialog(binding: OrderItemBinding) {
        val bottomSheetDialog = BottomSheetDialog(binding.root.context)
        bottomSheetDialog.behavior.peekHeight = 800.px
        bottomSheetDialog.setContentView(R.layout.qr_code_dialog_layout)

        val backBtn = bottomSheetDialog.findViewById<ImageView>(R.id.back)
        backBtn?.setOnClickListener {
            bottomSheetDialog.hide()
        }
        bottomSheetDialog.show()
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(val binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root)
}