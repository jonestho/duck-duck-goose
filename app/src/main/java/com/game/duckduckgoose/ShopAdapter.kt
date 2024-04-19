package com.game.duckduckgoose

import android.annotation.SuppressLint
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
    var selectedPos = RecyclerView.NO_POSITION

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val itemName: TextView
        val description: TextView
        val cost: TextView
        val itemPic: ImageView

        init {
            itemName = view.findViewById(R.id.item_name)
            description = view.findViewById(R.id.item_desc)
            cost = view.findViewById(R.id.item_cost)
            itemPic = view.findViewById(R.id.shop_item_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.shop_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.itemView.isSelected = selectedPos == position
        val item = shopItems.get(position)

        holder.itemName.text = "${item.name}"
        holder.description.text = "${item.description}"
        holder.cost.text = "Cost: ${item.cost}"

        holder.itemView.setBackgroundResource(if(position == selectedPos)
            R.color.colorEnabledBackground  else androidx.browser.R.color.browser_actions_bg_grey)

        // set shop item images
        when(item.name){
            "Coffee" -> holder.itemPic.setImageResource(R.drawable.coffee)
            "Fun Dip" -> holder.itemPic.setImageResource(R.drawable.fundip)
            "Farmer" -> holder.itemPic.setImageResource(R.drawable.farmers)
        }

        if(!item.bought || item.cost > userCurrency)
            holder.itemView.isClickable = false

        holder.itemView.setOnClickListener {
            selectedPos = position
            notifyDataSetChanged()
            selectListener(item, true)
        }
    }

    fun markAsBought(){

    }

    override fun getItemCount(): Int = shopItems.size
}
