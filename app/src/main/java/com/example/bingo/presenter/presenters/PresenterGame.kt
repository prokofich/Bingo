package com.example.bingo.presenter.presenters

import com.example.bingo.constant.LIST_NUMBERS_FOR_RECYCLERVIEW
import com.example.bingo.presenter.presenterInterface.PresenterGameInterface
import com.example.bingo.presenter.repository.Repository
import com.example.bingo.presenter.viewInterface.ViewGameInterface


class PresenterGame(private val view: ViewGameInterface):PresenterGameInterface {

    private val repository = Repository()

    //загрузка количества монет
    override fun loadMyCoins() {
        view.showTextCoins(coins = repository.getCoinsInCashAccount())
    }

    //добавление монет
    override fun addCoins() {
        repository.addCoinsInCashAccount(100)
    }

    //потеря монет
    override fun minusCoins() {
        repository.minusCoinsInCashAccount(25)
    }

    //загрузка случайного набора чисел для просмотра
    override fun loadNewListNumbersForShowRecyclerView() {
        view.showNumbersInRecyclerView(LIST_NUMBERS_FOR_RECYCLERVIEW.shuffled())
    }

    //загрузка случайного набора чисел для игры
    override fun loadNewListNumbersForGameInRecyclerView(listNumbers:List<Int>) {
        view.showNumbersForGameInRecyclerView(listNumbers, LIST_NUMBERS_FOR_RECYCLERVIEW.shuffled().slice(0..3).toList())
    }

}