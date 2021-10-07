package com.f5.damhwa_android.data.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import io.reactivex.processors.BehaviorProcessor

object DamhwaSharedPreferencesImpl : DamhwaSharedPreferences {
    private const val PREFERENCE_NAME = "damhwa_preferences"
    private lateinit var sharedPreferences: SharedPreferences

    private const val KEY_USER_NO = "user_no"
    private val _userNoProcessor: BehaviorProcessor<Long> = BehaviorProcessor.create()

    fun init(applicationContext: Context) {
        sharedPreferences =
            applicationContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

        sharedPreferences.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            emitValue(sharedPreferences, key)
        }

        emitInitialValue()
    }

    private fun emitInitialValue() {
        emitValue(sharedPreferences, KEY_USER_NO)
    }

    private fun emitValue(sharedPreferences: SharedPreferences, key: String) {
        when (key) {
            KEY_USER_NO -> {
                val userNo = sharedPreferences.getLong(key, 0L)
                _userNoProcessor.offer(userNo)
            }
        }
    }

    override fun saveUserNo(userNo: Long) {
        sharedPreferences.edit {
            if (userNo == null) {
                remove(KEY_USER_NO)
            } else {
                putLong(KEY_USER_NO, userNo)
            }
        }
    }

    override fun getUserNo(): Long = sharedPreferences.getLong(KEY_USER_NO, 0L)
}