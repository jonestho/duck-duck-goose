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


        val data: HashMap<String, Boolean> = hashMapOf("Coffee" to true, "Fun Dip" to true,
            "Farmers" to true)
        val currencyAmount = 50

        // TODO: Add the following later for the two variables above: intent.getParcelable(?)("name_here")

        recyclerView = findViewById<RecyclerView>(R.id.shop_items)
        myViewModel = ViewModelProvider(this).get(ShopViewModel::class.java)
        myViewModel.loadData(data, currencyAmount)

        recyclerView.adapter =
            ShopAdapter(myViewModel.shopItems!!) { itemName: String, isSelected: Boolean -> }

        recyclerView.layoutManager = LinearLayoutManager(this)

        myViewModel.updateShop.observe(this){
            // TODO: Disable the item that is bought.
        }

        // buttons
        val doneBtn = findViewById<Button>(R.id.to_main)
        val buyBtn = findViewById<Button>(R.id.buy_item)

        // TODO: Define onClickListeners
        doneBtn.setOnClickListener{

        }

        buyBtn.setOnClickListener{

        }
    }
}