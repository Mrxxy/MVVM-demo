package com.goldmantis.mvvm.core.base

import android.util.SparseArray

class DataBindingConfig(val layoutId: Int, val viewModelId: Int) {

    val bindingParams = SparseArray<Any>()

    fun addBindingParam(variableId: Int, variableValue: Any): DataBindingConfig {
        if (bindingParams[variableId] == null) {
            bindingParams.put(variableId, variableValue)
        }
        return this
    }

}