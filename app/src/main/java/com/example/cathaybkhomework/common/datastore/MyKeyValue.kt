package com.example.cathaybkhomework.common.datastore

import java.util.function.Supplier
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface MyKeyValueProperty<T> {
    fun set(key: String, value: T)
    fun get(key: String, defValue: T): T
}

class KeyValuePropertyDelegate<T>(
    private val property: MyKeyValueProperty<T>,
    private val keyName: String,
    private val defValueFetcher: Supplier<T>
) : ReadWriteProperty<Any?, T>, MyKeyValueProperty<T> by property {

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        set(key = keyName, value)
    }

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return get(key = keyName, defValueFetcher.get())
    }
}