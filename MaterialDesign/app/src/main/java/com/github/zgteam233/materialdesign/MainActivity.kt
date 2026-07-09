package com.github.zgteam233.materialdesign

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.github.zgteam233.materialdesign.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    val fruits = mutableListOf(
        Fruit("Apple", R.drawable.apple),
        Fruit("Banana", R.drawable.banana),
        Fruit("Orange", R.drawable.orange),
        Fruit("Watermelon", R.drawable.watermelon),
        Fruit("Pear", R.drawable.pear),
        Fruit("Grape", R.drawable.grape),
        Fruit("Pineapple", R.drawable.pineapple),
        Fruit("Strawberry", R.drawable.strawberry),
        Fruit("Cherry", R.drawable.cherry),
        Fruit("Mango", R.drawable.mango)
    )

    val fruitList = ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        // 这个滑动菜单也是夹带私货了哈
        binding.navView.setCheckedItem(R.id.navCall)
        binding.navView.setNavigationItemSelectedListener {
            binding.drawerLayout.closeDrawers()
            true
        }

        binding.fab.setOnClickListener { view ->
            view.showSnackbar("Data deleted", "Undo") {
                "Data restored".showToast()
            }
        }

        initFruits()
        val layoutManager = GridLayoutManager(this, 2)
        binding.recycleView.layoutManager = layoutManager
        val adapter = FruitAdapter(this, fruitList)
        binding.recycleView.adapter = adapter

        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        binding.swipeRefresh.setOnRefreshListener {
            refreshFruits(adapter)
        }
    }

    private fun initFruits() {
        fruitList.clear()
        repeat(50) {
            val index = (0 until fruits.size).random()
            fruitList.add(fruits[index])
        }
    }

    private fun refreshFruits(adapter: FruitAdapter) {
        thread {
            Thread.sleep(2000)
            runOnUiThread {
                initFruits()
                adapter.notifyDataSetChanged()
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
            R.id.backup -> "You clicked Backup".showToast()
            R.id.delete -> "You clicked Delete".showToast()
            R.id.settings -> "You clicked Settings".showToast()
        }
        return true
    }

}