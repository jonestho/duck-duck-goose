package com.game.duckduckgoose

import android.annotation.SuppressLint
import android.content.Intent
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

        myViewModel = ViewModelProvider(this).get(ShopViewModel::class.java)

        val data: ArrayList<Item> = arrayListOf(Item("Coffee",
            "Increase the Honks per click by one!", 100),
            Item("Fun Dip", "Increase the Honks per click by five! ", 200),
            Item("Farmer", "Have the geese work when idle!", 300))

        myViewModel.currencyAmount = intent.getIntExtra("honks", 0)
        var increments = 0
        var idles = 0

        recyclerView = findViewById<RecyclerView>(R.id.shop_items)
        myViewModel.loadData(data, myViewModel.currencyAmount)

        recyclerView.adapter =
            ShopAdapter(myViewModel.shopItems!!, myViewModel.currencyAmount)
                { item: Item, isSelected: Boolean -> }

        recyclerView.layoutManager = LinearLayoutManager(this)

        myViewModel.updateShop.observe(this){
            val currencyField = findViewById<TextView>(R.id.current_amnt)
            currencyField.text = "Total Honks: ${myViewModel.currencyAmount}"
        }

        // buttons
        val doneBtn = findViewById<Button>(R.id.to_main)
        val buyBtn = findViewById<Button>(R.id.buy_item)

        doneBtn.setOnClickListener{
            val backIntent = Intent()

            backIntent.putExtra("honks", myViewModel.currencyAmount)
            backIntent.putExtra("inc", increments)
            backIntent.putExtra("idle", idles)

            setResult(RESULT_OK, backIntent)
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

                    when(selectedItem!!.name){
                        "Coffee" -> increments += 1
                        "Fun Dip" -> increments += 5
                        "Farmer" -> idles += 1
                    }

                    myViewModel._updateShop.value = true
                }
            }
        }
    }
}