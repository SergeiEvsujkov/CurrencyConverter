package com.coffeemolkin.currencyconverter.data

import com.coffeemolkin.currencyconverter.remote.ApiHolder

class CurrenciesRepo {
    fun getCurrencies() = ApiHolder.apiService.getCurrency()
}