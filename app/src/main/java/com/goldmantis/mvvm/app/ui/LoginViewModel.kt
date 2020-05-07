package com.goldmantis.mvvm.app.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.goldmantis.mvvm.app.api.UserRoleBean
import com.goldmantis.mvvm.core.base.BaseViewModel

class LoginViewModel : BaseViewModel() {

    val userName = ObservableField<String>()
    val password = ObservableField("")
    val loadingVisible = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()

    private val _data = MutableLiveData<List<UserRoleBean>>()
    val data: LiveData<List<UserRoleBean>>
        get() = _data

    private val loginRepository by lazy { LoginRepository() }

    fun login() {
        when {
            userName.get().isNullOrEmpty() -> message.value = "请输入用户名"
            password.get().isNullOrEmpty() -> message.value = "请输入密码"
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