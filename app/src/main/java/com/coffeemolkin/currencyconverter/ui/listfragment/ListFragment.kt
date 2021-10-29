package com.coffeemolkin.currencyconverter.ui.listfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.coffeemolkin.currencyconverter.App
import com.coffeemolkin.currencyconverter.data.CurrenciesRepo
import com.coffeemolkin.currencyconverter.data.Valute
import com.coffeemolkin.currencyconverter.databinding.FragmentListBinding
import com.coffeemolkin.currencyconverter.navigation.BackButtonListener
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

private const val POSITION = "position"
private const val VALUTE = "valute"
private val VALUTE_RUB: Valute = Valute(
    "rub",
    "643",
    "RUB",
    "1",
    "Российский рубль",
    "1"
)

class ListFragment : MvpAppCompatFragment(), ListView, BackButtonListener {

    private var binding: FragmentListBinding? = null
    private var position: Int? = null
    private var valute: Valute? = null
    private val presenter by moxyPresenter { ListPresenter(CurrenciesRepo(), App.instance.router) }

    private val adapter by lazy {
        RVadapter(
            presenter.currenciesListItemPresenter
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(POSITION)
            valute = it.getParcelable(VALUTE)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentListBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun init() {
        binding?.rvValute?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvValute?.adapter = adapter

    }

    override fun updateList() {
        adapter.notifyDataSetChanged()
    }

    override fun setPosition() {
        presenter.positionSet(position)
    }

    override fun getValute() {
        presenter.getValute(valute)
    }

    override fun progressBarEnd() {
        binding!!.progressBar.isGone = true
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
        fun newInstance(position: Int, valute: Valute): ListFragment = ListFragment().apply {
            arguments = Bundle().apply {
                putInt(POSITION, position)
                putParcelable(VALUTE, valute)
            }
        }
    }

}