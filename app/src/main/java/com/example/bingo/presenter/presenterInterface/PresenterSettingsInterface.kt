package com.example.bingo.presenter.presenterInterface

import android.content.Context

interface PresenterSettingsInterface {

    fun loadTextRules()// функция получения текста правил игры с сервера

    fun loadMyCoins() // функция получения количества монет для их отображения

    fun addCoins(coins:Int) // функция добавления монет

    fun checkLastDay():Boolean // функция проверки последнего дня,когда были пополнены монеты

    fun setLastDay(day:String) // функция установки текущего дня,как последнего,когда были пополнены монеты

    fun loadNewCoinsForReplenish(context: Context) //функция получения ежедневного денежного приза

}