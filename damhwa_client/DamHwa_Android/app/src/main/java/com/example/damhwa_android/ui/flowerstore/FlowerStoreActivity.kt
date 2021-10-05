package com.example.damhwa_android.ui.flowerstore

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.damhwa_android.R
import com.example.damhwa_android.data.sharedpreferences.DamhwaSharedPreferencesImpl
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermissionResult
import com.gun0912.tedpermission.rx2.TedPermission
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.addTo
import java.util.*


class FlowerStoreActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flower_store)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val webView: WebView = findViewById(R.id.flowerStoreWebView)
        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(WebAppInterface(this), "FlowerStore")

        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Got last known location. In some rare situations this can be null.
                    Log.d("로그", location?.longitude.toString())
                }
            return
        }
        webView.loadUrl("http://192.168.0.7:8080/StoreInfo/")
        sendLatLan(webView)
    }



    private fun sendLatLan(webView: WebView) {
        webView.post(Runnable {
            val userno = DamhwaSharedPreferencesImpl.getUserNo()
            webView.evaluateJavascript(
                "sendLatLan($userno)",
                null
            )
        })
    }


}