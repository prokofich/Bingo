package com.example.bingo.presenter.viewInterface

import android.content.Context

interface ViewSettingsInterface {

    fun showTextRules(text:String) // функция показа текста правил игры

    fun showTextCoins(coins:Int) // функция показа количества монет

    fun showMessageForReplenish(coins: Int) // функция показа сообщения после пополнения

    fun showMessageForCancelReplenish(text: String) //функция показа сообщения,если пополнение невозможно

    fun showToast(context: Context,message:String,duration:String) //функция показа всплывающего сообщения

}