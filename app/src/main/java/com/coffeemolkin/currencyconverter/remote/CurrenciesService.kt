package com.coffeemolkin.currencyconverter.remote

import com.coffeemolkin.currencyconverter.data.Currencies
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface CurrenciesService {
    @GET("XML_daily.asp")
    fun getCurrency(): Single<Currencies>
}