package com.appspot.mvvmarchitecture.ui.dashboard.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appspot.mvvmarchitecture.config.client.RetrofitClent
import com.appspot.mvvmarchitecture.data.api.ApiHelper
import com.appspot.mvvmarchitecture.data.model.BalanceResponseModel
import com.appspot.mvvmarchitecture.data.model.LoginResponseModel
import com.appspot.mvvmarchitecture.data.model.TransactionData
import com.appspot.mvvmarchitecture.data.model.TransactionDetailsResponseModel
import com.appspot.mvvmarchitecture.databinding.ActivityDashboardBinding
import com.appspot.mvvmarchitecture.ui.base.DashboardActivityModelFactory
import com.appspot.mvvmarchitecture.ui.dashboard.adapter.TransactionAdapter
import com.appspot.mvvmarchitecture.ui.dashboard.viewmodel.DashboardViewModel
import com.mindorks.retrofit.coroutines.utils.Status
import retrofit2.Response

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var dashboardViewModel: DashboardViewModel
    lateinit var txtBalance: TextView
    lateinit var txtAccountNumber: TextView
    lateinit var txtAccountName: TextView
    lateinit var progressBar: ProgressBar
    lateinit var recyclerTransaction: RecyclerView
    lateinit var loginData: LoginResponseModel
    private lateinit var adapter:TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setLayoutID()
        setViewModel()
        setUpObserver()

        loginData = (intent.getSerializableExtra("loginData") as? LoginResponseModel)!!

        txtAccountNumber.text = loginData.accountNo
        txtAccountName.text = loginData.username

        dashboardViewModel.getBalance(loginData.token)

    }

    private fun setLayoutID(){
        txtBalance = binding.txtBalance
        txtAccountNumber = binding.txtAccountNumber
        txtAccountName = binding.txtAccountName
        progressBar = binding.loading
        recyclerTransaction = binding.recyclerTransaction

        recyclerTransaction.layoutManager = LinearLayoutManager(this)
        adapter = TransactionAdapter(arrayListOf())
        recyclerTransaction.addItemDecoration(
            DividerItemDecoration(
                recyclerTransaction.context,
                0
            )
        )
        recyclerTransaction.adapter = adapter
    }

    private fun setViewModel(){
        var retrofitClent = RetrofitClent()
        dashboardViewModel = ViewModelProvider(this, DashboardActivityModelFactory(ApiHelper(retrofitClent.getAPIInterface())))
            .get(DashboardViewModel::class.java)
    }

    private fun setUpObserver(){

        dashboardViewModel.resourceBalance.observe(this , Observer {

            it?.let { resource ->
                when(resource.status){
                    Status.SUCCESS ->{
                        progressBar.visibility = View.GONE
                        resource.data?.let { response ->
                            val balanceDataResponse = response as Response<BalanceResponseModel>
                            val balanceData = balanceDataResponse.body()
                            if (balanceData!!.status == "success"){
                                txtBalance.text = "SGD "+balanceData.balance
                                dashboardViewModel.getTransaction(loginData.token)
                            }

                        }
                    }

                    Status.ERROR ->{
                        progressBar.visibility = View.GONE
                    }

                    Status.LOADING ->{
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })

        dashboardViewModel.resourceTransaction.observe(this , Observer {

            it?.let { resource ->

                when(resource.status){

                    Status.SUCCESS->{
                        progressBar.visibility = View.GONE
                        resource.data?.let { response ->
                            val transactionDataResponse = response as Response<TransactionDetailsResponseModel>
                            val transactionData = transactionDataResponse.body()
                            if (transactionData!!.status == "success"){
                                retrieveList(transactionData.data)
                            }

                        }
                    }

                    Status.ERROR->{
                        progressBar.visibility = View.GONE
                    }

                    Status.LOADING->{
                        progressBar.visibility = View.VISIBLE
                    }
                }

            }

        })
    }

    private fun retrieveList(transactionData: List<TransactionData>) {
        adapter.apply {
            addUsers(transactionData)
            notifyDataSetChanged()
        }
    }
}