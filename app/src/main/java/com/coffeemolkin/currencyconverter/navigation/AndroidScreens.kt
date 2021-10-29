package com.coffeemolkin.currencyconverter.navigation

import com.coffeemolkin.currencyconverter.data.Valute
import com.coffeemolkin.currencyconverter.ui.currenciesfragment.CurrenciesFragment
import com.coffeemolkin.currencyconverter.ui.listfragment.ListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object AndroidScreens {

    class CurrencyScreensValute(
        private val position: Int,
        private val valuteFirst: Valute,
        private val valuteSecond: Valute
    ) : SupportAppScreen() {

        override fun getFragment() =
            CurrenciesFragment.newInstance(position, valuteFirst, valuteSecond)
    }

    class CurrencyScreens(

    ) : SupportAppScreen() {

        override fun getFragment() = CurrenciesFragment()
    }


    class ListScreens(private val position: Int, private val valute: Valute) : SupportAppScreen() {
        override fun getFragment() = ListFragment.newInstance(position, valute)
    }


}