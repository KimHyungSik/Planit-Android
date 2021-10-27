package com.ctu.planitstudy.feature.presentation.util

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

class ActivityLifeCycleObserver(private val lifecycle: Lifecycle): LifecycleObserver {

    companion object {
        const val TAG = "ActivityLife - 로그"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreated(source: LifecycleOwner) {
        Log.d(TAG, "onCreated")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Log.d(TAG, "onStart")
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            Log.d(TAG, "currentState is greater or equal to INITIALIZED")
        }
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
            Log.d(TAG, "currentState is greater or equal to CREATED")
        }
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            Log.d(TAG, "currentState is greater or equal to STARTED")
        }
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            Log.d(TAG, "currentState is greater or equal to RESUMED")
        }
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.DESTROYED)) {
            Log.d(TAG, "currentState is greater or equal to DESTROYED")
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Log.d(TAG, "onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.d(TAG, "onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        Log.d(TAG, "onStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Log.d(TAG, "onDestroy")
    }
}