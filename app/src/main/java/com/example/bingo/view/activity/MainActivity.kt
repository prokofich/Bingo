package com.example.bingo.view.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.bingo.R
import com.example.bingo.constant.APP_PREFERENCES
import com.example.bingo.constant.LAST_DAY
import com.example.bingo.constant.MAIN
import com.example.bingo.constant.MY_CASH_ACCOUNT
import com.example.bingo.databinding.ActivityMainBinding
import com.example.bingo.view.fragments.GameFragment
import com.example.bingo.view.fragments.MenuFragment
import com.example.bingo.view.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //изначальный показ фрагмента меню
        replaceFragment(MenuFragment())

        MAIN = this

        //полноэкранный режим
        @Suppress("DEPRECATION")
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

    }

    //функция замены фрагмента на экране
    fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.commit()
    }

    //функция добавления денег к счету
    fun addCoinsInCashAccount(coins:Int){
        val preferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        preferences.edit()
            .putInt(MY_CASH_ACCOUNT,getCoinsInCashAccount()+coins)
            .apply()
    }

    //функция получения денежного счета
    fun getCoinsInCashAccount(): Int {
        return getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE).getInt(MY_CASH_ACCOUNT,0)
    }

    //функция уменьшения денег на счете
    fun minusCoinsInCashAccount(coins:Int){
        val preferences = getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE)
        preferences.edit()
            .putInt(MY_CASH_ACCOUNT,getCoinsInCashAccount()-coins)
            .apply()
    }

    //функция получения последнего дня,когда был пополнен счет
    fun getLastDay(): String {
        val preferences = getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE).getString(LAST_DAY,"")
        return preferences ?: ""
    }

    //функция установки последнего дня,когда был пополнен счет
    fun setLastDay(day:String){
        val preferences = getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE)
        preferences.edit()
            .putString(LAST_DAY,day)
            .apply()
    }

}