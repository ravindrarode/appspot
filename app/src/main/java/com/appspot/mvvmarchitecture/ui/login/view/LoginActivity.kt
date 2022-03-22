package com.appspot.mvvmarchitecture.ui.login.view

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.appspot.mvvmarchitecture.config.client.RetrofitClent
import com.appspot.mvvmarchitecture.data.api.ApiHelper
import com.appspot.mvvmarchitecture.data.model.LoginRequestModel
import com.appspot.mvvmarchitecture.data.model.LoginResponseModel
import com.appspot.mvvmarchitecture.databinding.ActivityLoginBinding
import com.appspot.mvvmarchitecture.ui.base.LoginActivityModelFactory
import com.appspot.mvvmarchitecture.ui.dashboard.view.DashboardActivity
import com.appspot.mvvmarchitecture.ui.login.viewmodel.LoginActivityViewModel
import com.mindorks.retrofit.coroutines.utils.Status
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginActivityViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val password = binding.password
        val login = binding.login
        val loading = binding.loading

        setupViewModel()

        loginViewModel.resourceLogin.observe(this, Observer {
            it?.let {resource ->
                when(resource.status){
                    Status.SUCCESS->{
                        loading.visibility = View.GONE
                        resource.data?.let { response ->
                            val loginDataResponse = response as Response<LoginResponseModel>
                            val loginData = loginDataResponse.body()
                            if (loginData!!.status == "success"){
                                val intent = Intent(this, DashboardActivity::class.java)
                                intent.putExtra("loginData", loginData)
                                startActivity(intent)
                            }
                        }
                    }

                    Status.ERROR->{
                        loading.visibility = View.GONE
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }

                    Status.LOADING->{
                        loading.visibility = View.VISIBLE
                    }
                }
            }
        })

        /*loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })*/

        /*username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }*/

        password.apply {
            /*afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }*/

            /*setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }*/

            login.setOnClickListener {
                when {
                    username.text.toString().length < 3 -> {
                        username.error = "Username is required."
                    }
                    password.text.toString().length < 5 -> {
                        password.error = "Password is required."
                    }
                    else -> {
                        val loginRequestModel: LoginRequestModel = LoginRequestModel()
                        loginRequestModel.username = username.text.toString()
                        loginRequestModel.password = password.text.toString()
                        loginViewModel.getLoginDetails(loginRequestModel)
                    }
                }
            }
        }
    }

    private fun setupViewModel(){
        var retrofitClent = RetrofitClent()
        loginViewModel = ViewModelProvider(this, LoginActivityModelFactory(ApiHelper(retrofitClent.getAPIInterface())))
            .get(LoginActivityViewModel::class.java)
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}