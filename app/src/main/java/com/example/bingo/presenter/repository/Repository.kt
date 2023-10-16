package com.example.bingo.presenter.repository

import android.content.Context
import android.widget.Toast
import com.example.bingo.constant.MAIN
import com.example.bingo.view.fragments.GameFragment
import com.example.bingo.view.fragments.MenuFragment
import com.example.bingo.view.fragments.SettingsFragment
import java.util.Calendar

class Repository {

    //функция показа всплывающего сообщения
    fun showToast(context: Context,message:String){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

    fun checkCoinsForGame(): Boolean {
        return getCoinsInCashAccount() >= 25
    }

    //функция показа длительного всплывающего сообщения
    fun showLongToast(context: Context,message:String){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }

    //функция выход из игры
    fun exitingTheApplication(){
        MAIN.finishAffinity()
    }

    //показ фрагмента меню
    fun showMenuFragment(){
        MAIN.replaceFragment(MenuFragment())
    }

    //показ фрагмента настроек
    fun showSettingsFragment(){
        MAIN.replaceFragment(SettingsFragment())
    }

    //показ фрагмента игры
    fun showGameFragment(){
        MAIN.replaceFragment(GameFragment())
    }

    //функция добавления денег к счету
    fun addCoinsInCashAccount(coins:Int){
        MAIN.addCoinsInCashAccount(coins = coins)
    }

    //функция получения денежного счета
    fun getCoinsInCashAccount(): Int {
        return MAIN.getCoinsInCashAccount()
    }

    //функция уменьшения денег на счете
    fun minusCoinsInCashAccount(coins:Int){
        MAIN.minusCoinsInCashAccount(coins = coins)
    }

    //функция получения последнего дня,когда был пополнен счет
    fun getLastDay(): String {
        return MAIN.getLastDay()
    }

    //функция установки последнего дня,когда был пополнен счет
    fun setLastDay(day:String){
        MAIN.setLastDay(day = day)
    }

    //функция получения текущей даты
    fun getCurrentDay(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)+1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return "$year.$month.$day"
    }

    fun createJsonFile(namePhone:String,locale:String,id:String): String {
        val json = """
                      {
                       "phone_name": "$namePhone",
                       "locale": "$locale",
                       "unique": "$id"
                      }
                      """.trimIndent()
        return json
    }

}