package com.github.zgteam233

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//
// Copyright(c) 2026 ZGTeam233.
//
const val CHOICE = 2

fun main() {
    when (CHOICE) {
        1 -> CoroutineTest.test1()
        2 -> StandardFunTest.apply {
            test1()
            println()
            test2()
            println()
            test3()
        }
    }
}