package com.game.duckduckgoose

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// TODO: Change String to Item type or whatever is defined.

class ShopAdapter(val shopItems: ArrayList<Item>, val userCurrency: Int,
                  val selectListener: (Item, Boolean)-> Unit):
    RecyclerView.Adapter<ShopAdapter.ViewHolder>() {
    private var selectedPos = RecyclerView.NO_POSITION

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val itemName: TextView
        val description: TextView
        val cost: TextView

        init {
            itemName = view.findViewById(R.id.item_name)
            description = view.findViewById(R.id.item_desc)
            cost = view.findViewById(R.id.item_cost)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.shop_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.isSelected = selectedPos == position
        val item = shopItems.get(position)

        holder.itemName.text = "${item.name}"
        holder.description.text = "${item.description}"
        holder.cost.text = "${item.cost}"

        if(!item.bought || item.cost > userCurrency)
            holder.itemView.isClickable = false

        holder.itemView.setOnClickListener {
            selectListener(item, false)
        }
    }

    fun markAsBought(){

    }

    override fun getItemCount(): Int = shopItems.size
}
