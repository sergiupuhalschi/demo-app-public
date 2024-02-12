package com.sergiupuhalschi.presentation.common.utils

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

interface ResourceProvider {

    fun getString(@StringRes resId: Int): String

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String

    fun getQuantityString(@PluralsRes resId: Int, count: Int, vararg formatArgs: Any): String

    @DrawableRes
    fun getDrawableResId(name: String): Int?
}

class ResourceProviderImplementation(
    private val context: Context
) : ResourceProvider {

    override fun getString(resId: Int): String {
        return context.getString(resId)
    }

    override fun getString(resId: Int, vararg formatArgs: Any): String {
        return context.getString(resId, *formatArgs)
    }

    override fun getQuantityString(resId: Int, count: Int, vararg formatArgs: Any): String {
        return context.resources.getQuantityString(resId, count, *formatArgs)
    }

    override fun getDrawableResId(name: String): Int? {
        return context.resources.getIdentifier(name, "drawable", context.packageName)
            .takeIf { it != 0 }
    }
}