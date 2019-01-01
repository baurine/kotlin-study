package com.google.codelabs.mdc.kotlin.shrine

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.android.volley.toolbox.NetworkImageView

class ProductCardViewHolder(itemView: View) //TODO: Find and store views from itemView
    : RecyclerView.ViewHolder(itemView) {
    var productImage = itemView.findViewById<NetworkImageView>(R.id.product_image)
    var productTitle = itemView.findViewById<TextView>(R.id.product_title)
    var productPrice = itemView.findViewById<TextView>(R.id.product_price)
}
