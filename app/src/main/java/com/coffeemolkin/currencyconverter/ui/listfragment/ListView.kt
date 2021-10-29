package com.coffeemolkin.currencyconverter.ui.listfragment

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface ListView : MvpView {
    fun init()
    fun updateList()
    fun setPosition()
    fun getValute()
    fun progressBarEnd()
}