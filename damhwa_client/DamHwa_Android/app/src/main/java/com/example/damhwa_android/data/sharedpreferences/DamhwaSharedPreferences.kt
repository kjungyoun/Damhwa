package com.example.damhwa_android.data.sharedpreferences

interface DamhwaSharedPreferences {

    fun saveUserNo(userNo: Long)

    fun getUserNo(): Long
}