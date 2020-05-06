package com.goldmantis.mvvm.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.forEach
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    protected lateinit var mViewModel: VM
    private lateinit var mBinging: ViewDataBinding

    lateinit var mActivity: AppCompatActivity
    private var mActivityProvider: ViewModelProvider? = null
    private var mFragmentProvider: ViewModelProvider? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBindingConfig = getDataBindingConfig()
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(inflater, dataBindingConfig.layoutId, container, false)
        mBinging = binding.apply {
            lifecycleOwner = this@BaseFragment
            setVariable(dataBindingConfig.viewModelId, mViewModel)
            val bindingParams = dataBindingConfig.bindingParams
            bindingParams.forEach { key, value -> setVariable(key, value) }
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
    }

    abstract fun initViewModel(): VM

    abstract fun getDataBindingConfig(): DataBindingConfig

    abstract fun initData()

    protected open fun <T : ViewModel> getFragmentViewModel(modelClass: Class<T>): T {
        if (mFragmentProvider == null) {
            mFragmentProvider = ViewModelProvider(this)
        }
        return mFragmentProvider!!.get(modelClass)
    }

    protected open fun <T : ViewModel> getActivityViewModel(modelClass: Class<T>): T {
        if (mActivityProvider == null) {
            mActivityProvider = ViewModelProvider(mActivity)
        }
        return mActivityProvider!![modelClass]
    }

    protected fun showMessage(message: String) {
        Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show()
    }

}