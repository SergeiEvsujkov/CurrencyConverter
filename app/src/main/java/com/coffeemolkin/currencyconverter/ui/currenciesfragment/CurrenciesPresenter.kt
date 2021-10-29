package com.coffeemolkin.currencyconverter.ui.currenciesfragment

import android.util.Log
import com.coffeemolkin.currencyconverter.data.Currencies
import com.coffeemolkin.currencyconverter.data.CurrenciesRepo
import com.coffeemolkin.currencyconverter.data.Valute
import com.coffeemolkin.currencyconverter.data.ValutePair
import com.coffeemolkin.currencyconverter.listcurrencies.ICurrencyListPresenter
import com.coffeemolkin.currencyconverter.navigation.AndroidScreens
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

private val VALUTE_RUB: Valute = Valute(
    "rub",
    "643",
    "RUB",
    "1",
    "Российский рубль",
    "1"
)

private const val FIRST_VALUTE = 1
private const val SECOND_VALUTE = 2

class CurrenciesPresenter(
    private val currenciesRepo: CurrenciesRepo,
    private val router: Router,
) : MvpPresenter<CurrenciesView>() {


    inner class CurrenciesListPresenter : ICurrencyListPresenter {

        var currencies: Currencies? = null
        var valutePair = ValutePair(
            null, null
        )


        override fun getCount(): Int = currencies?.valuteList?.size ?: 0

    }

    private val currenciesListPresenter = CurrenciesListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadData()
        viewState.init()

    }

    private fun loadData() {
        currenciesRepo.getCurrencies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ currencies ->
                currencies.valuteList = currencies.valuteList.plus(
                    VALUTE_RUB
                )
                currenciesListPresenter.currencies = currencies
                initValute()
                viewState.updateList()
                viewState.progressBarEnd()
            }, {
                Log.e("CurrenciesPresenter", "Ошибка", it)
            })

    }

    private fun initValute() {
        if (currenciesListPresenter.valutePair.valuteFirst == null) {
            currenciesListPresenter.valutePair.valuteFirst =
                currenciesListPresenter.currencies?.valuteList?.get(
                    currenciesListPresenter.currencies?.valuteList!!.size - 1
                )
        }

        if (currenciesListPresenter.valutePair.valuteSecond == null) {
            currenciesListPresenter.valutePair.valuteSecond =
                currenciesListPresenter.currencies?.valuteList?.get(10)
        }
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    fun getValueSecond(): String? {
        return currenciesListPresenter.valutePair.valuteSecond?.charCode
    }

    fun getValueFirst(): String? {
        return currenciesListPresenter.valutePair.valuteFirst?.charCode
    }


    fun firstValueChange(value: String) {
        val secondValute = String.format(
            "%.2f",
            value.replace(",", ".")
                .toFloat() / currenciesListPresenter.valutePair.valuteFirst?.nominal!!.replace(
                ",",
                "."
            )
                .toFloat() / currenciesListPresenter.valutePair.valuteSecond?.value!!.replace(
                ",",
                "."
            )
                .toFloat() * currenciesListPresenter.valutePair.valuteFirst?.value!!.replace(
                ",",
                "."
            )
                .toFloat()
        )

        viewState.updateList()
        viewState.updateFirstChange(secondValute)

    }

    fun secondValueChange(value: String) {
        val firstValute = String.format(
            "%.2f",
            value.replace(",", ".")
                .toFloat() / currenciesListPresenter.valutePair.valuteSecond?.nominal!!.replace(
                ",",
                "."
            )
                .toFloat() / currenciesListPresenter.valutePair.valuteFirst?.value!!.replace(
                ",",
                "."
            )
                .toFloat() * currenciesListPresenter.valutePair.valuteSecond?.value!!.replace(
                ",",
                "."
            )
                .toFloat()
        )
        viewState.updateList()
        viewState.updateSecondChange(firstValute)

    }

    fun toListFragment(valute: Int) {
        if (valute == FIRST_VALUTE) {
            currenciesListPresenter.valutePair.valuteSecond?.let {
                AndroidScreens.ListScreens(
                    valute,
                    it
                )
            }?.let {
                router.navigateTo(
                    it
                )
            }
        } else if (valute == SECOND_VALUTE) {
            currenciesListPresenter.valutePair.valuteFirst?.let {
                AndroidScreens.ListScreens(
                    valute,
                    it
                )
            }?.let {
                router.navigateTo(
                    it
                )
            }
        }
    }

    fun setFirstValute(valute: ValutePair) {
        currenciesListPresenter.valutePair = valute

    }

    fun setSecondValute(valute: ValutePair) {
        currenciesListPresenter.valutePair = valute

    }


}