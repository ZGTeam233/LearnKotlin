package com.github.zgteam233

//
// Copyright(c) 2026 ZGTeam233.
//
object StandardFunTest {
    val list = listOf("Apple", "Banana", "Orange", "Pear", "Grape")

    fun test1() {
        val result = with(StringBuilder()) {
            append("Start eating fruits.\n")
            for (fruit in list) {
                append(fruit).append("\n")
            }
            append("Ate all fruits.")
            toString()
        }
        println(result)
    }

    fun test2() {
        val result = StringBuilder().run {
            append("Start eating fruits.\n")
            for (fruit in list) {
                append(fruit).append("\n")
            }
            append("Ate all fruits.")
            toString()
        }
        println(result)
    }

    fun test3() {
        val result = StringBuilder().apply {
            append("Start eating fruits.\n")
            for (fruit in list) {
                append(fruit).append("\n")
            }
            append("Ate all fruits.")
        }
        println(result.toString())
    }
}