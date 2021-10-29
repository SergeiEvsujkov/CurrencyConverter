package com.coffeemolkin.currencyconverter.ui.currenciesfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import com.coffeemolkin.currencyconverter.App
import com.coffeemolkin.currencyconverter.R
import com.coffeemolkin.currencyconverter.data.CurrenciesRepo
import com.coffeemolkin.currencyconverter.data.Valute
import com.coffeemolkin.currencyconverter.data.ValutePair
import com.coffeemolkin.currencyconverter.databinding.FragmentCurrenciesBinding
import com.coffeemolkin.currencyconverter.navigation.BackButtonListener
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

private const val POSITION = "position"
private const val VALUTE_FIRST = "valute_first"
private const val VALUTE_SECOND = "valute_second"

private const val FIRST_VALUTE = 1
private const val SECOND_VALUTE = 2

class CurrenciesFragment : MvpAppCompatFragment(), CurrenciesView, BackButtonListener {

    private var binding: FragmentCurrenciesBinding? = null
    private var valuteFirst: Valute? = null
    private var valuteSecond: Valute? = null
    private var position: Int? = null
    private val presenter by moxyPresenter {
        CurrenciesPresenter(
            CurrenciesRepo(),
            App.instance.router
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(POSITION)
            valuteFirst = it.getParcelable(VALUTE_FIRST)
            valuteSecond = it.getParcelable(VALUTE_SECOND)

            if (position == FIRST_VALUTE) {
                presenter.setFirstValute(ValutePair(valuteFirst, valuteSecond))
            } else if (position == SECOND_VALUTE) {
                presenter.setSecondValute(ValutePair(valuteFirst, valuteSecond))
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentCurrenciesBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.firstValuteChange.setOnClickListener { toListFirst() }
        binding!!.secondValuteChange.setOnClickListener { toListSecond() }
    }

    override fun toListFirst() {
        presenter.toListFragment(FIRST_VALUTE)
    }

    override fun toListSecond() {
        presenter.toListFragment(SECOND_VALUTE)
    }

    override fun progressBarEnd() {
        binding!!.progressBar.isGone = true
    }

    override fun init() {
        updateList()
        binding!!.secondValuteCode.text = presenter.getValueSecond()
        binding!!.firstValuteCode.text = presenter.getValueFirst()

        binding!!.firstValuteInto.addTextChangedListener {
            var value = binding!!.firstValuteInto.text.toString()
            if (value.isBlank()) {
                value = "0"
            }
            if (binding!!.firstValuteInto.isFocused) {
                presenter.firstValueChange(value)
            }

        }

        binding!!.secondValuteInto.addTextChangedListener {
            var value = binding!!.secondValuteInto.text.toString()
            if (value.isBlank()) {
                value = "0"
            }

            if (binding!!.secondValuteInto.isFocused) {

                presenter.secondValueChange(value)
            }
        }

    }

    override fun updateList() {


        binding!!.secondValuteCode.text = presenter.getValueSecond()
        binding!!.firstValuteCode.text = presenter.getValueFirst()
        if (!binding!!.firstValuteInto.text.isNullOrEmpty()) {
            binding!!.firstValuteValue.text = binding!!.firstValuteInto.editableText
        } else {
            binding!!.firstValuteValue.text = "0"
        }
        if (!binding!!.secondValuteInto.text.isNullOrEmpty()) {
            binding!!.secondValuteValue.text = binding!!.secondValuteInto.editableText
        } else {
            binding!!.secondValuteValue.text = "0"
        }
    }

    override fun updateFirstChange(secondValute: String) {
        binding!!.secondValuteValue.text = secondValute
    }

    override fun updateSecondChange(firstValute: String) {
        binding!!.firstValuteValue.text = firstValute
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun backPressed(): Boolean {
        return presenter.backPressed()
    }

    companion object {
        @JvmStatic
        fun newInstance(
            position: Int,
            valuteFirst: Valute,
            valuteSecond: Valute
        ): CurrenciesFragment =
            CurrenciesFragment().apply {
                arguments = Bundle().apply {
                    putInt(POSITION, position)
                    putParcelable(VALUTE_FIRST, valuteFirst)
                    putParcelable(VALUTE_SECOND, valuteSecond)
                }
            }
    }

}