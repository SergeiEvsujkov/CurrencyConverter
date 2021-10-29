package com.coffeemolkin.currencyconverter.ui.listfragment

import com.coffeemolkin.currencyconverter.ui.listfragment.item.IItemView

interface ListItemView : IItemView {

    fun showTitle(title: String)
}