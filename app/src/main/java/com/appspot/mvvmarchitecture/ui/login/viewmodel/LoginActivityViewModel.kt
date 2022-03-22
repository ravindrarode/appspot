package com.appspot.mvvmarchitecture.ui.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appspot.mvvmarchitecture.R
import com.appspot.mvvmarchitecture.data.model.LoginRequestModel
import com.appspot.mvvmarchitecture.data.repository.LoginActivityMainRepository
import com.mindorks.retrofit.coroutines.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivityViewModel(private val loginActivityMainRepository: LoginActivityMainRepository) : ViewModel() {

    val resourceLogin = MutableLiveData<Resource<Any>>()

    fun getLoginDetails(data: LoginRequestModel) = viewModelScope.launch {
        resourceLogin.postValue(Resource.loading(data = null))
        try {
            val response = loginActivityMainRepository.getLoginDetails(data)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    resourceLogin.postValue(Resource.success(data = response))
                } else {

                    var errorStr = ""
                    errorStr = try {
                        response.errorBody()!!.string()
                    } catch (e: java.lang.Exception) {
                        e.toString()
                    }
                    //Below two Error as per the old code. And add Delay because live
                    // data update string very fast so not able to show 2 toast message.
                    resourceLogin.postValue(
                        Resource.error(
                            data = null,
                            message = errorStr
                        )
                    )
                }
            }

        } catch (e: Exception) {
            resourceLogin.postValue(
                Resource.error(
                    data = null,
                    message = e.message ?: "Error Occurred!"
                )
            )
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            resourceLogin.postValue(
                Resource.error(
                    data = null,
                    message = R.string.invalid_username.toString()
                )
            )
            //_loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            resourceLogin.postValue(
                Resource.error(
                    data = null,
                    message = R.string.invalid_password.toString()
                )
            )
            //_loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            resourceLogin.postValue(
                Resource.error(
                    data = null,
                    message = R.string.invalid_password.toString()
                )
            )
            //_loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return username.length>3
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}