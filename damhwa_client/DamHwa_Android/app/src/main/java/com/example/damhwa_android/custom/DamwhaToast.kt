package com.example.damhwa_android.custom

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.damhwa_android.R
import com.example.damhwa_android.databinding.DamwhaToastBinding

object DamwhaToast {

    fun createToast(context: Context, message: String): Toast? {
        val inflater = LayoutInflater.from(context)
        val binding: DamwhaToastBinding =
            DataBindingUtil.inflate(inflater, R.layout.damwha_toast, null, false)

        binding.toastMsg.text = message

        return Toast(context).apply {
            setGravity(Gravity.TOP or Gravity.CENTER, 0, 16.toPx())
            duration = Toast.LENGTH_LONG
            view = binding.root
        }
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}