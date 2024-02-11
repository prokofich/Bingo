package com.example.bingo.presenter.presenters

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.example.bingo.constant.LIST_COINS_FOR_REPLENISH
import com.example.bingo.constant.SHORT_DURATION
import com.example.bingo.constant.URL_JSON_FILE
import com.example.bingo.presenter.model.ResponseGet
import com.example.bingo.presenter.presenterInterface.PresenterSettingsInterface
import com.example.bingo.presenter.repository.Repository
import com.example.bingo.presenter.viewInterface.ViewSettingsInterface
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class PresenterSettings(private val view:ViewSettingsInterface):PresenterSettingsInterface {

    private val repository = Repository()

    //загрузка текста с правилами с сервера
    override fun loadTextRules() {

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(URL_JSON_FILE)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                val responseFromServer = Gson().fromJson(responseBody,ResponseGet::class.java)
                Handler(Looper.getMainLooper()).post {
                    view.showTextRules(responseFromServer.text)
                }
            }
        })
    }

    //загрузка количества монет
    override fun loadMyCoins() {
        view.showTextCoins(coins = repository.getCoinsInCashAccount())
    }

    //добавление монет
    override fun addCoins(coins:Int) {
        repository.addCoinsInCashAccount(coins = coins)
    }

    //проверка на возможность пополнения монет
    override fun checkLastDay(): Boolean {
        return repository.getCurrentDay()!=repository.getLastDay()
    }

    //установка текущего дня,как последнего,когда были пополнены монеты
    override fun setLastDay(day:String) {
        repository.setLastDay(day = day)
    }

    //загрузка новых монет при пополнении
    override fun loadNewCoinsForReplenish(context: Context) {
        val newCoins = LIST_COINS_FOR_REPLENISH.shuffled()[0]
        view.showToast(context = context, message = "+$newCoins coins!", duration = SHORT_DURATION)
        view.showMessageForReplenish(coins = newCoins)
        addCoins(coins = newCoins)
        loadMyCoins()
        setLastDay(day = repository.getCurrentDay())
    }

}