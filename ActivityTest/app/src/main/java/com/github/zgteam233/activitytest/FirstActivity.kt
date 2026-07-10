package com.github.zgteam233.activitytest

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.github.zgteam233.activitytest.databinding.FirstLayoutBinding

//
// Created by wsnzg6 on ${DATE}.
// Copyright(c) $YEAR ZGTeam233.
//
class FirstActivity : BaseActivity() {
    companion object {
        private const val TAG = "FirstActivity"
    }

    private lateinit var binding: FirstLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FirstLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button1.setOnClickListener {
            SecondActivity.actionStart(this, "data1", "data2")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_item -> "You clicked Add".showToast()
            R.id.remove_item -> finish()
        }
        return true
    }
}