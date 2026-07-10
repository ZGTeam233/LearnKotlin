package com.github.zgteam233.activitytest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.zgteam233.activitytest.databinding.SecondLayoutBinding

//
// Copyright(c) 2026 ZGTeam233.
//
class SecondActivity : BaseActivity() {
    companion object {
        fun actionStart(context: Context, data1: String, data2: String) {
            val intent = Intent(context, SecondActivity::class.java)
            intent.putExtra("param1", data1)
            intent.putExtra("param2", data2)
            context.startActivity(intent)
        }
    }

    private lateinit var binding: SecondLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SecondLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button2.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }
    }
}