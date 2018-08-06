package com.github.wkw.share.di

import dagger.MapKey
import android.arch.lifecycle.ViewModel

import kotlin.reflect.KClass

/**
 * @author GoGo on 2018/8/6.
 */
@MustBeDocumented
@Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)