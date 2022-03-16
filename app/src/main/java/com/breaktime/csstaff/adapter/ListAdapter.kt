package com.breaktime.csstaff.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.breaktime.csstaff.R
import com.breaktime.csstaff.databinding.OrderItemBinding
import com.breaktime.csstaff.px
import com.google.android.material.bottomsheet.BottomSheetBehavior
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
            items[position].images.forEach { url ->
                val image = ImageView(binding.root.context)
                val layoutParams =
                    LinearLayout.LayoutParams(25.px, 25.px)
                layoutParams.marginStart = 3.px
                layoutParams.marginEnd = 3.px
                image.layoutParams = layoutParams
                binding.drinkType.addView(image)
                picasso.load(url).fit().centerCrop().into(image)
            }
            binding.root.setOnClickListener {
                showBottomSheetDialog(binding)
            }
            binding.userId.text = items[position].userId
        }
    }


    private fun showBottomSheetDialog(binding: OrderItemBinding) {
        val bottomSheetDialog = BottomSheetDialog(binding.root.context)
        bottomSheetDialog.behavior.peekHeight = 800.px
        bottomSheetDialog.setContentView(R.layout.product_list_dialog_layout)
//        val copy = bottomSheetDialog.findViewById<LinearLayout>(R.id.copyLinearLayout)
//        val share = bottomSheetDialog.findViewById<LinearLayout>(R.id.shareLinearLayout)
//        val upload = bottomSheetDialog.findViewById<LinearLayout>(R.id.uploadLinearLayout)
//        val download = bottomSheetDialog.findViewById<LinearLayout>(R.id.download)
//        val delete = bottomSheetDialog.findViewById<LinearLayout>(R.id.delete)
        bottomSheetDialog.show()
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(val binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root)
}