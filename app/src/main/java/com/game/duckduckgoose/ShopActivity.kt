package com.game.duckduckgoose

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class ShopActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var myViewModel: ShopViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shop)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val data: ArrayList<Item> = arrayListOf(Item("Coffee",
            "Short and instant speed boost!", 10),
            Item("Fun Dip", "Long and gradual speed boost!", 15),
            Item("Bread Basket", "Attracts more geese!", 20))
        val currencyAmount = 50

        // TODO: Add the following later for the two variables above: intent.getParcelable(?)("name_here")

        recyclerView = findViewById<RecyclerView>(R.id.shop_items)
        myViewModel = ViewModelProvider(this).get(ShopViewModel::class.java)
        myViewModel.loadData(data, currencyAmount)

        recyclerView.adapter =
            ShopAdapter(myViewModel.shopItems!!, myViewModel.currencyAmount)
                { item: Item, isSelected: Boolean -> }

        recyclerView.layoutManager = LinearLayoutManager(this)

        myViewModel.updateShop.observe(this){
            // TODO: Disable the item that is bought.
        }

        // buttons
        val doneBtn = findViewById<Button>(R.id.to_main)
        val buyBtn = findViewById<Button>(R.id.buy_item)

        // TODO: Define onClickListeners
        doneBtn.setOnClickListener{
            // TODO: Implement bidirectional
            finish()
        }

        buyBtn.setOnClickListener{
            val shopAdapter = recyclerView.adapter as? ShopAdapter

            if(shopAdapter?.selectedPos != RecyclerView.NO_POSITION){
                val selectedItem = shopAdapter?.shopItems?.get(shopAdapter.selectedPos)

                if(selectedItem?.cost!! > currencyAmount){
                    Snackbar.make(doneBtn, "Error: You do not have enough money to buy this.",
                        Snackbar.LENGTH_LONG).show()
                }
            } else {
                Snackbar.make(doneBtn, "Error: Please select an item to buy.",
                    Snackbar.LENGTH_LONG).show()
            }
        }
    }
}