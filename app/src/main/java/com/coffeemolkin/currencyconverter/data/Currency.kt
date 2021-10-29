package com.coffeemolkin.currencyconverter.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Parcelize
@Root(name = "ValCurs")
data class Currencies(

    @field:Attribute(name = "Date")
    @param:Attribute(name = "Date")
    val date: String,


    @field:Attribute(name = "name")
    @param:Attribute(name = "name")
    val name: String,

    @field:ElementList(name = "Valute", inline = true)
    @param:ElementList(name = "Valute", inline = true)
    var valuteList: List<Valute>

) : Parcelable

@Parcelize
@Root(name = "Valute")
data class Valute(
    @field:Attribute(name = "ID")
    @param:Attribute(name = "ID")
    val id: String,


    @field:Element(name = "NumCode")
    @param:Element(name = "NumCode")
    var numCode: String,


    @field:Element(name = "CharCode")
    @param:Element(name = "CharCode")
    var charCode: String,


    @field:Element(name = "Nominal")
    @param:Element(name = "Nominal")
    var nominal: String,


    @field:Element(name = "Name")
    @param:Element(name = "Name")
    var name: String,


    @field:Element(name = "Value")
    @param:Element(name = "Value")
    var value: String
) : Parcelable