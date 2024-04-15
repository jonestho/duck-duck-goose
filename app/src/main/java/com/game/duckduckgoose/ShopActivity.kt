package com.game.duckduckgoose

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
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
    var initialized = false
    var boughtItems: ArrayList<Item> = arrayListOf()

    @SuppressLint("SetTextI18n")
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
            val currencyField = findViewById<TextView>(R.id.current_amnt)
            currencyField.text = "Your Points: ${myViewModel.currencyAmount}"
        }

        initialized = true

        // buttons
        val doneBtn = findViewById<Button>(R.id.to_main)
        val buyBtn = findViewById<Button>(R.id.buy_item)

        doneBtn.setOnClickListener{
            // TODO: Implement return items bought back to game via. intent.
            finish()
        }

        buyBtn.setOnClickListener{
            val result = myViewModel.purchaseHandler(recyclerView)

            when(result){
                1 -> Snackbar.make(doneBtn,
                    "Error: You do not have enough points to buy this.",
                    Snackbar.LENGTH_LONG).show()
                2 -> Snackbar.make(doneBtn, "Error: Please select an item to buy."
                    , Snackbar.LENGTH_LONG).show()
                else -> {
                    Snackbar.make(doneBtn, "Success: Item purchased!"
                        , Snackbar.LENGTH_LONG).show()

                    val shopAdapter = recyclerView.adapter as? ShopAdapter
                    val selectedItem = shopAdapter?.shopItems?.get(shopAdapter.selectedPos)

                    boughtItems.add(selectedItem!!)
                    myViewModel._updateShop.value = true
                }
            }
        }
    }
}