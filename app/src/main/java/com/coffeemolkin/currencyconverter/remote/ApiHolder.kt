package com.coffeemolkin.currencyconverter.remote

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object ApiHolder {

    val apiService: CurrenciesService by lazy {

        Retrofit.Builder()
            .baseUrl("http://www.cbr.ru/scripts/")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
            .create(CurrenciesService::class.java)
    }

}