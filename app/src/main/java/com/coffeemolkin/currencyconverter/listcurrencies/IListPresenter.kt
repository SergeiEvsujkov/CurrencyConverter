package com.coffeemolkin.currencyconverter.listcurrencies

import com.coffeemolkin.currencyconverter.ui.currenciesfragment.CurrenciesView

interface IListPresenter<V : CurrenciesView> {
    fun getCount(): Int
}

interface ICurrencyListPresenter : IListPresenter<CurrenciesView>