package com.game.duckduckgoose

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
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

        /* TODO: Declare variable for game data to be passed and held in.
            val data: HashMap<Item, Bool>? = intent.getParcelable(?)("name_here")
         */

        recyclerView = findViewById<RecyclerView>(R.id.shop_items)
        myViewModel = ViewModelProvider(this).get(ShopViewModel::class.java)

        // TODO: Call function to load data into ViewModel property and update store

        // TODO: Set adapter

        // TODO: Set layout manager

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