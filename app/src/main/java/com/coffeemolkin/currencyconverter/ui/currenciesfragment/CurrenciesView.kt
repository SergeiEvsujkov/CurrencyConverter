package com.coffeemolkin.currencyconverter.ui.currenciesfragment

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd


@AddToEnd
interface CurrenciesView : MvpView {
    fun init()
    fun updateList()
    fun updateFirstChange(secondValute: String)
    fun updateSecondChange(firstValute: String)
    fun toListFirst()
    fun toListSecond()
    fun progressBarEnd()

}