package com.github.zgteam233.activitylifecycletest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.zgteam233.activitylifecycletest.databinding.ActivityNormalBinding

class NormalActivity : AppCompatActivity() {
    lateinit var binding: ActivityNormalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNormalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }
}