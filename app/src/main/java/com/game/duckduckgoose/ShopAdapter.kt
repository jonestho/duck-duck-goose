package com.game.duckduckgoose

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// TODO: Change String to Item type or whatever is defined.

class ShopAdapter(val shopItems: ArrayList<String>,
                  val selectListener: (String, Boolean) -> Unit):
    RecyclerView.Adapter<ShopAdapter.ViewHolder>() {
    private var selectedPos = RecyclerView.NO_POSITION
    private var revealedHolder: RecyclerView.ViewHolder? = null

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val itemName: TextView
        val description: TextView
        val cost: TextView

        init {
            itemName = view.findViewById(R.id.item_name)
            description = view.findViewById(R.id.description)
            cost = view.findViewById(R.id.item_cost)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = shopItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}
