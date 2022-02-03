package com.example.todoapp

import android.content.Context
import androidx.core.content.ContextCompat

class DarkTheme : MyAppTheme {
    override fun firstActivityBackgroundColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.bgDark)
    }

    override fun firstActivityTextColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.bgDark)
    }

    override fun id() = 1
}