package com.appspot.mvvmarchitecture.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appspot.mvvmarchitecture.R
import com.appspot.mvvmarchitecture.data.model.TransactionData

class TransactionAdapter(private val transactionData: ArrayList<TransactionData>): RecyclerView.Adapter<TransactionAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var txtDate:TextView? = null
        private var txtAccountName:TextView? = null
        private var txtAccountNumber:TextView? = null
        private var txtBalance:TextView? = null


        init {
            txtDate = itemView.findViewById<View>(R.id.txt_date) as TextView?
            txtAccountName = itemView.findViewById<View>(R.id.txt_account_name) as TextView?
            txtAccountNumber = itemView.findViewById<View>(R.id.txt_account_number) as TextView?
            txtBalance = itemView.findViewById<View>(R.id.txt_balance) as TextView?
        }

        fun bind(transactionData: TransactionData) {
            txtDate!!.text = transactionData.transactionDate
            txtAccountName!!.text = transactionData.receipient.accountHolder
            txtAccountNumber!!.text = transactionData.receipient.accountNo
            txtBalance!!.text = transactionData.amount
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false))

    override fun getItemCount(): Int = transactionData.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(transactionData[position])
    }

    fun addUsers(transactionData: List<TransactionData>) {
        this.transactionData.apply {
            clear()
            addAll(transactionData)
        }

    }
}
