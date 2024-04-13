package com.game.duckduckgoose

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// TODO: Change String to Item type or whatever is defined.

class ShopAdapter(val shopItems: HashMap<String, Boolean>,
                  val selectListener: (String, Boolean) -> Unit):
    RecyclerView.Adapter<ShopAdapter.ViewHolder>() {
    private var selectedPos = RecyclerView.NO_POSITION
    private var revealedHolder: RecyclerView.ViewHolder? = null

    val shopItemsList = shopItems.entries.toList()

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
        val item = shopItemsList[position]

        val key = item.key
        val value = item.value

        holder.itemName.text = key
        holder.description.text = "COMING SOON"
        holder.cost.text = "COMING SOON"

        if(!value)
            holder.itemView.isClickable = false

        holder.itemView.setOnClickListener {

        }
    }

    fun markAsBought(){

    }

    override fun getItemCount(): Int = shopItems.size
}
