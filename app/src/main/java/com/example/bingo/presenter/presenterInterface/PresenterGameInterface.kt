package com.example.bingo.presenter.presenterInterface

interface PresenterGameInterface {

    fun loadMyCoins() // функция получения количества монет для их отображения

    fun addCoins() // функция добавления монет

    fun minusCoins() // функция покупки попытки

    fun loadNewListNumbersForShowRecyclerView() // функция загрузки новой последовательности для показа

    fun loadNewListNumbersForGameInRecyclerView(listNumbers:List<Int>) // функция загрузки новой последовательности для игры

}