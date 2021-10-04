package com.silverytitan.mvvmframe.utils

import androidx.lifecycle.ViewModel
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author Created by yingjie.zhao on 2021/4/20 0020.
 *          Desc:
 */
object ClassUtil {
    fun <T> getViewModel(obj: Any): Class<T>? {
        val currentClass: Class<*> = obj.javaClass
        val tClass: Class<T>? = getGenericClass(currentClass, ViewModel::class.java)
        return if (tClass == null || tClass == ViewModel::class.java) {
            null
        } else tClass
    }

    private fun <T> getGenericClass(clazz: Class<*>, filterClass: Class<*>): Class<T>? {
        val type: Type? = clazz.genericSuperclass
        if (type == null || type !is ParameterizedType) return null
        val types: Array<Type> = type.actualTypeArguments
        for (t in types) {
            val tClass = t as Class<T>
            if (filterClass.isAssignableFrom(tClass)) {
                return tClass
            }
        }
        return null
    }
}