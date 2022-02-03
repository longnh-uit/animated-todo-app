package com.example.todoapp

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
import androidx.savedstate.ViewTreeSavedStateRegistryOwner

class LightTheme : MyAppTheme {
    override fun firstActivityBackgroundColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.bgLight)
    }

    override fun firstActivityTextColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.bgDark)
    }

    override fun id() = 0
}