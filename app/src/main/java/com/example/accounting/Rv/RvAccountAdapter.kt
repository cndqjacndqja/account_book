package com.example.accounting.Rv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.accounting.R
import com.example.accounting.db.AccountData
import kotlinx.android.synthetic.main.fragment_billing_history.view.*

class RvAccountAdapter(private val context: Context): RecyclerView.Adapter<RvAccountAdapter.RvAccountHolder>() {

    //일단 임시 변수를 어디에 선언해야 좋을지는 몰라서 임시로 만들음
    var data = mutableListOf<AccountData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvAccountHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_bliing_history_item, parent, false)

        return RvAccountHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RvAccountHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class RvAccountHolder(view: View):RecyclerView.ViewHolder(view){
        val txt_rv_item_category: TextView = view.findViewById(R.id.txt_rv_item_category)
        val txt_rv_item_money: TextView = view.findViewById(R.id.txt_rv_item_money)
        val txt_rv_item_detail: TextView = view.findViewById(R.id.txt_rv_item_detail)
        val txt_rv_item_date: TextView = view.findViewById(R.id.txt_rv_item_date)

        fun bind(data: AccountData){
            txt_rv_item_category.text = data.category
            txt_rv_item_money.text = data.money.toString()
            txt_rv_item_detail.text = data.detail
            txt_rv_item_date.text = data.date_time
        }
    }
}