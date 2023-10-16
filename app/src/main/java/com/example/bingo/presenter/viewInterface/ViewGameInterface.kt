package com.example.bingo.presenter.viewInterface

import android.content.Context

interface ViewGameInterface {

    fun showTextCoins(coins:Int) // функция показа количества монет

    fun showToast(context: Context, message:String, duration:String) //функция показа всплывающего сообщения

    fun showNumbersInRecyclerView(listNumbers:List<Int>) // функция показа случайной игровой последовательности

    fun showNumbersForGameInRecyclerView(listNumbers:List<Int>,listHiddenNumbers:List<Int>) // функция показа случайной игровой последовательности для игры

}