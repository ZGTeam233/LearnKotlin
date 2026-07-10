package com.github.zgteam233

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//
// Copyright(c) 2026 ZGTeam233.
//
object CoroutineTest {
    fun test1() {
        GlobalScope.launch {
            println("codes run in coroutine scope")
        }
        Thread.sleep(1000)
    }
}