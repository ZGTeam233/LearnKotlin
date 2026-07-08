package com.github.zgteam233.activitytest

import android.os.Bundle
import com.github.zgteam233.activitytest.databinding.ThirdLayoutBinding

class ThirdActivity : BaseActivity() {
    private lateinit var binding: ThirdLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ThirdLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button3.setOnClickListener {
            ActivityCollector.finishAll()
            android.os.Process.killProcess(android.os.Process.myPid())
        }

    }
}