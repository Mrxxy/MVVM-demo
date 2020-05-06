package com.goldmantis.mvvm.app.ui

import android.util.Log
import androidx.lifecycle.Observer
import com.goldmantis.mvvm.app.BR
import com.goldmantis.mvvm.app.R
import com.goldmantis.mvvm.core.base.BaseActivity
import com.goldmantis.mvvm.core.base.DataBindingConfig

class LoginActivity : BaseActivity<LoginViewModel>() {

    override fun initViewModel(): LoginViewModel {
        return getActivityViewModel(LoginViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(BR.viewModel, R.layout.activity_login)
            .addBindingParam(BR.click, ClickProxy())
    }

    override fun initData() {
        mViewModel.apply {
            data.observe(this@LoginActivity, Observer {
                showMessage("${it.size}")
            })
            loadingVisible.observe(this@LoginActivity, Observer {
                if (it) {
                    Log.d(TAG, "loading")
                } else {
                    Log.d(TAG, "hide loading")
                }
            })
        }
    }

    open inner class ClickProxy {
        fun login() {
            when {
                mViewModel.userName.get().isNullOrEmpty() -> showMessage("请输入用户名")
                mViewModel.password.get().isNullOrEmpty() -> showMessage("请输入密码")
                else -> mViewModel.login()
            }
        }
    }

}