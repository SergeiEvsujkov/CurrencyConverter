package com.coffeemolkin.currencyconverter.ui.listfragment

import android.util.Log
import com.coffeemolkin.currencyconverter.data.Currencies
import com.coffeemolkin.currencyconverter.data.CurrenciesRepo
import com.coffeemolkin.currencyconverter.data.Valute
import com.coffeemolkin.currencyconverter.navigation.AndroidScreens
import com.coffeemolkin.currencyconverter.ui.listfragment.item.IListPresenter
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

class ListPresenter(
    private val currenciesRepo: CurrenciesRepo,
    private val router: Router
) : MvpPresenter<ListView>() {
    private var positionValute = 1
    private var valuteIn: Valute? = null

    inner class CurrenciesListItemPresenter :
        IListPresenter {

        var currencies: Currencies? = null


        override fun getCount(): Int {
            return currencies?.valuteList?.size ?: 0
        }

        override var itemClickListener: ((ListItemView) -> Unit)? = null
        override fun bindView(view: ListItemView) {
            val valute = currencies?.valuteList?.get(view.pos)
            if (valute != null) {
                view.showTitle(valute.charCode)
            }
        }

    }

    val currenciesListItemPresenter = CurrenciesListItemPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadData()
        viewState.init()
        currenciesListItemPresenter.itemClickListener = { itemView ->
            viewState.setPosition()
            viewState.getValute()
            if (positionValute == FIRST_VALUTE) {
                valuteIn?.let {
                    AndroidScreens.CurrencyScreensValute(
                        positionValute,
                        currenciesListItemPresenter.currencies!!.valuteList[itemView.pos],
                        it
                    )
                }?.let {
                    router.newRootScreen(
                        it
                    )
                }
            } else if (positionValute == SECOND_VALUTE) {
                valuteIn?.let {
                    AndroidScreens.CurrencyScreensValute(
                        positionValute,
                        it,
                        currenciesListItemPresenter.currencies!!.valuteList[itemView.pos]

                    )
                }?.let {
                    router.newRootScreen(
                        it
                    )
                }
            }

        }

    }


    private fun loadData() {
        currenciesRepo.getCurrencies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ currencies ->
                currencies.valuteList = currencies.valuteList.plus(
                    VALUTE_RUB
                )
                currenciesListItemPresenter.currencies = currencies
                viewState.updateList()
                viewState.progressBarEnd()
            }, {
                Log.e("CurrenciesPresenter", "Ошибка", it)
            })
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    fun positionSet(position: Int?) {
        if (position != null) {
            positionValute = position
        }
    }

    fun getValute(valute: Valute?) {
        valuteIn = valute
    }

}