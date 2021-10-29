package com.coffeemolkin.currencyconverter.ui.listfragment.item

import com.coffeemolkin.currencyconverter.ui.listfragment.ListItemView

interface IItemPresenter<V : IItemView> {

    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}

interface IListPresenter : IItemPresenter<ListItemView>