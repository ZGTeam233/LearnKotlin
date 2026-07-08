package com.github.zgteam233

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object CoroutineTest {
    fun test1() {
        GlobalScope.launch {
            println("codes run in coroutine scope")
        }
        Thread.sleep(1000)
    }
}