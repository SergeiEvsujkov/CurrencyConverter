package com.coffeemolkin.currencyconverter.ui.listfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coffeemolkin.currencyconverter.databinding.ItemValuteBinding
import com.coffeemolkin.currencyconverter.ui.listfragment.item.IListPresenter
import kotlinx.android.synthetic.main.item_valute.view.*

class RVadapter(
    private val presenter: IListPresenter
) : RecyclerView.Adapter<RVadapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemValuteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.item_valute.setOnClickListener { presenter.itemClickListener?.invoke(this) }
        }
    }

    override fun getItemCount(): Int = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        presenter.bindView(holder.apply { pos = position })
    }

    inner class ViewHolder(private val vb: ItemValuteBinding) : RecyclerView.ViewHolder(vb.root),
        ListItemView {
        override fun showTitle(title: String) {
            vb.titleValute.text = title
        }

        override var pos: Int = -1

    }

}
