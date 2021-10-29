package com.coffeemolkin.currencyconverter.ui.activity

import android.os.Bundle
import com.coffeemolkin.currencyconverter.App
import com.coffeemolkin.currencyconverter.R
import com.coffeemolkin.currencyconverter.databinding.ActivityMainBinding
import com.coffeemolkin.currencyconverter.navigation.BackButtonListener
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainActivity : MvpAppCompatActivity(), MainView {

    private val navigator = SupportAppNavigator(this, R.id.container)

    private val presenter by moxyPresenter {
        MainPresenter(App.instance.router)
    }

    private var _binding: ActivityMainBinding? = null

    private val vb
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CurrencyConverter)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.instance.navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.instance.navigationHolder.removeNavigator()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
        presenter.backPressed()
    }
}