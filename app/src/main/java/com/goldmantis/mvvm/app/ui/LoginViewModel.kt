package com.goldmantis.mvvm.app.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.goldmantis.mvvm.app.api.UserRoleBean
import com.goldmantis.mvvm.core.base.BaseViewModel

class LoginViewModel() : BaseViewModel() {

    val userName = ObservableField<String>()
    val password = ObservableField("")
    val loadingVisible = MutableLiveData<Boolean>()

    private val _data = MutableLiveData<List<UserRoleBean>>()
    val data: LiveData<List<UserRoleBean>>
        get() = _data

    private val loginRepository by lazy { LoginRepository() }

    fun login() {
        when {
            else -> launch({
                loginRepository.login(userName.get()!!, password.get()!!)
            }, onStart = {
                loadingVisible.value = true
            }, onSuccess = {
                _data.value = it
            }, onError = {

            }, onFinish = {
                loadingVisible.value = false
            })
        }
    }

}