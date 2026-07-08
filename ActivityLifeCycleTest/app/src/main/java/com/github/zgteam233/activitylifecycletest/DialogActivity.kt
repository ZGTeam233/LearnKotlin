package com.github.zgteam233.activitylifecycletest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.zgteam233.activitylifecycletest.databinding.ActivityDialogBinding

class DialogActivity : AppCompatActivity() {
    lateinit var binding: ActivityDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}