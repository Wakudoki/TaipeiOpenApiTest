package com.example.cathaybkhomework.common.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.cathaybkhomework.MyApp
import com.example.cathaybkhomework.common.MyConst
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

class MyDataStore : CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.IO + SupervisorJob()

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = MyConst.MY_APP_NAME)

    val dataStore by lazy {
        MyApp.koinContext.dataStore
    }
}

// 下面全域func都使用inline，其在compile後會被建構在呼叫端下，而不會再產生出一個instance來處理。

/**
 * Sync Version
 * @param keyName 索引名稱
 * @param def 預設值
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T> MyDataStore.property(keyName: String, def: T): KeyValuePropertyDelegate<T> {
    val preferenceKey = getPreferenceKey<T>(keyName) as Preferences.Key<T>
    return KeyValuePropertyDelegate(
        object : MyKeyValueProperty<T> {
            override fun set(key: String, value: T) {
                runBlocking {
                    dataStore.edit {
                        it[preferenceKey] = value
                    }
                }
            }

            override fun get(key: String, defValue: T): T {
                return runBlocking {
                    dataStore.data.map { it[preferenceKey] }.first() ?: defValue
                }
            }
        },
        keyName
    ) { def }
}

/**
 * 根據需求分辨類型
 * @param keyName 儲存名稱
 */
inline fun <reified T> getPreferenceKey(keyName: String): Preferences.Key<*> {
    val preferenceKey = when (T::class) {
        Int::class -> {
            ::intPreferencesKey
        }

        Boolean::class -> {
            ::booleanPreferencesKey
        }

        String::class -> {
            ::stringPreferencesKey
        }

        Long::class -> {
            ::longPreferencesKey
        }

        Float::class -> {
            ::floatPreferencesKey
        }

        else -> {
            throw IllegalStateException("Unsupported Type")
        }
    }
    return preferenceKey(keyName)
}