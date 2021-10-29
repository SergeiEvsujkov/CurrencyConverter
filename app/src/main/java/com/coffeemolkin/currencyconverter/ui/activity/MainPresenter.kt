package com.coffeemolkin.currencyconverter.ui.activity

import com.coffeemolkin.currencyconverter.navigation.AndroidScreens
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

class MainPresenter(private val router: Router) : MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(AndroidScreens.CurrencyScreens())
    }

    fun backPressed() {
        router.exit()
    }
}