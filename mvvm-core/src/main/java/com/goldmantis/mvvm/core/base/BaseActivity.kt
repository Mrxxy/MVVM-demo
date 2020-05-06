package com.goldmantis.mvvm.core.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.forEach
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


abstract class BaseActivity<VM : ViewModel> : AppCompatActivity() {

    protected lateinit var mViewModel: VM
    private lateinit var mBinging: ViewDataBinding
    protected val TAG: String = this::class.java.simpleName

    private var mActivityProvider: ViewModelProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = initViewModel()

        val dataBindingConfig = getDataBindingConfig()
        val binding: ViewDataBinding =
            DataBindingUtil.setContentView(this, dataBindingConfig.layoutId)
        mBinging = binding.apply {
            lifecycleOwner = this@BaseActivity
            setVariable(dataBindingConfig.viewModelId, mViewModel)
            val bindingParams = dataBindingConfig.bindingParams
            bindingParams.forEach { key, value -> setVariable(key, value) }
        }
        initData()
    }

    abstract fun initViewModel(): VM

    abstract fun getDataBindingConfig(): DataBindingConfig

    abstract fun initData()

    protected open fun <T : ViewModel> getActivityViewModel(modelClass: Class<T>): T {
        if (mActivityProvider == null) {
            mActivityProvider = ViewModelProvider(this)
        }
        return mActivityProvider!!.get(modelClass)
    }

    protected fun showMessage(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }

}