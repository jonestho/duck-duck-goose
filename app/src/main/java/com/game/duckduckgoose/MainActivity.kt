package com.game.duckduckgoose

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var myViewModel: MainViewModel

    // two-way navigation with shop activity
    private val prefLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            //val newItems = it.data!!.get

            //myViewModel.updateItems(updatedMin, updatedMax)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        myViewModel = ViewModelProvider(this)[MainViewModel::class.java]

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        // buttons
        val quitBtn = findViewById<Button>(R.id.quitBtn)
        val shopBtn = findViewById<Button>(R.id.shopBtn)
        val honkBtn = findViewById<Button>(R.id.honkBtn)

        // textviews
        val gooseName = findViewById<TextView>(R.id.gooseName)
        val totalHonks = findViewById<TextView>(R.id.totalHonks)
        val totalFarmers = findViewById<TextView>(R.id.totalFarmersNum)
        val honkInc = findViewById<TextView>(R.id.honksPerClickNum)

        myViewModel.setUID(intent.getStringExtra("uid")!!)
        myViewModel.loadUsername()

        // observers
        myViewModel.gooseName.observe(this) {
            val name = it.toString()
            gooseName.text = name
        }

        myViewModel.totalClicks.observe(this){
            val hCount = it.toString()
            totalHonks.text = "$hCount"
        }

        myViewModel.idleClickers.observe(this){
            val fCount = it.toString()
            totalFarmers.text = "$fCount"
        }

        myViewModel.clickIncrement.observe(this){
            val iCount = it.toString()
            honkInc.text = "$iCount"
        }

        // buttons
        honkBtn.setOnClickListener {
            myViewModel.addClicks()
        }

        shopBtn.setOnClickListener {
            val toPreferences = Intent(this, ShopActivity::class.java)

            prefLauncher.launch(toPreferences)
        }

        quitBtn.setOnClickListener {
            finish()
        }
    }
}