package com.goldmantis.mvvm.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldmantis.mvvm.core.api.ApiException
import com.goldmantis.mvvm.core.api.BaseResponse
import com.goldmantis.mvvm.core.api.HttpException
import kotlinx.coroutines.*

open class BaseViewModel : ViewModel() {

    open class UiState<T>(
        val isLoading: Boolean = false,
        val isRefresh: Boolean = false,
        val isSuccess: T? = null,
        val isError: String?= null
    )

    protected fun <T> launch(
        call: suspend () -> BaseResponse<T>,
        onStart: (() -> Unit)? = null,
        onSuccess: ((T) -> Unit)? = null,
        onCancel: (() -> Unit)? = null,
        onFinish: (() -> Unit)? = null,
        onError: ((HttpException) -> Unit)? = null
    ): Job {
        return viewModelScope.launch {
            onStart?.invoke()
            try {
                val resp = call.invoke()
                onSuccess?.invoke(resp.handleResp())
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> onCancel?.invoke()
                    is ApiException -> {
                        when (e.status) {
                            "401" -> onError?.invoke(HttpException("授权已过期"))
                            "24378" -> onError?.invoke(HttpException("版本过低"))
                            else -> onError?.invoke(HttpException(e.message))
                        }
                    }
                    else -> onError?.invoke(HttpException(e.message))
                }
            } finally {
                onFinish?.invoke()
            }
        }
    }

    protected fun <T> async(block: () -> T): Deferred<T> {
        return viewModelScope.async {
            block.invoke()
        }
    }

    protected fun cancelJob(job: Job?) {
        if (job != null && job.isActive && !job.isCompleted && !job.isCancelled) {
            job.cancel()
        }
    }

}