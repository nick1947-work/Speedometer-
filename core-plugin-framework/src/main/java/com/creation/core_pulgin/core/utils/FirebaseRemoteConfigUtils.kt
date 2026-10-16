package com.creation.core_pulgin.core.utils

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.creation.core_pulgin.BuildConfig
import com.google.android.gms.tasks.Tasks
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.creation.core_pulgin.payment.in_app_purchase.data.model.ProductListingData
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object FirebaseRemoteConfigUtils {
    private const val TAG = "RemoteConfigUtils"
    private const val PREMIUM_PACK_DATA_KEY = "premium_pack_data"
    const val FREE_TRIAL_PACK_STATUS = "is_free_trial_enabled"

    fun init(context: Context) {
        try {
            FirebaseApp.initializeApp(context.applicationContext)
            val remoteConfig = FirebaseRemoteConfig.getInstance()
            val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(if (BuildConfig.DEBUG) 0L else 3600L)
                .setFetchTimeoutInSeconds(10L)
                .build()
            remoteConfig.setConfigSettingsAsync(configSettings)
            remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Firebase Remote Config fetched and activated successfully.")
                } else {
                    Log.w(TAG, "Firebase Remote Config fetch failed: ${task.exception?.message}")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to init Firebase Remote Config", e)
        }
    }

    fun getInAppProducts(
        defaultLocalPackJson: String,
        context: Context,
    ): List<ProductListingData>? {
        return try {
            FirebaseApp.initializeApp(context.applicationContext)
            val mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

            var json = mFirebaseRemoteConfig.getString(PREMIUM_PACK_DATA_KEY)

            // If empty, fetch and activate safely without blocking UI thread
            if (TextUtils.isEmpty(json)) {
                if (android.os.Looper.myLooper() == android.os.Looper.getMainLooper()) {
                    // Never block main thread; trigger async fetch and use local fallback
                    mFirebaseRemoteConfig.fetchAndActivate()
                } else {
                    try {
                        val configSettings = FirebaseRemoteConfigSettings.Builder()
                            .setMinimumFetchIntervalInSeconds(if (BuildConfig.DEBUG) 0L else 3600L)
                            .setFetchTimeoutInSeconds(3L)
                            .build()
                        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings)
                        val task = mFirebaseRemoteConfig.fetchAndActivate()
                        Tasks.await(task, 3, TimeUnit.SECONDS)
                        json = mFirebaseRemoteConfig.getString(PREMIUM_PACK_DATA_KEY)
                    } catch (fetchEx: Exception) {
                        Log.w(TAG, "Synchronous fetchAndActivate timed out or failed: ${fetchEx.message}")
                        json = mFirebaseRemoteConfig.getString(PREMIUM_PACK_DATA_KEY)
                    }
                }
            }

            if (TextUtils.isEmpty(json) && defaultLocalPackJson.isNullOrEmpty().not()) {
                json = defaultLocalPackJson
            }
            if (TextUtils.isEmpty(json)) {
                Log.w(TAG, "Remote config premium_pack_data and local fallback are both empty!")
                return emptyList()
            }

            val jsonObject = JSONObject(json)
            val jsondataArray = jsonParseAppDataList(jsonObject.optJSONArray("data"))
            return jsondataArray
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "Error in getInAppProducts", e)
            emptyList()
        }
    }

    private fun jsonParseAppDataList(dataJsonArray: JSONArray?): List<ProductListingData>? {
        var tempList=ArrayList<ProductListingData>()
        if (dataJsonArray != null && dataJsonArray.length() > 0) {
            for (i in 0 until dataJsonArray.length()) {
                val dataJsonObject = dataJsonArray[i] as JSONObject
                var active = dataJsonObject.optString("Active")
                var package_name = dataJsonObject.optString("Pack Name")
                var ProductID_Price = dataJsonObject.optString("ProductID_Price")
                var ProductID_Purchase = dataJsonObject.optString("ProductID_Purchase")
                var SUB_TYPE = dataJsonObject.optString("SUB_TYPE")
                var Offer = dataJsonObject.optString("Offer")
                var Offer_Name = dataJsonObject.optString("Offer_Name")
                var Pack_type = dataJsonObject.optString("Pack_type")
                var offer_days = dataJsonObject.optString("offer_days")
                var offer_start_time = dataJsonObject.optString("offer_start_time")
                var trial = dataJsonObject.optString("trial")
                var price = dataJsonObject.optString("price")
                var showPrice = dataJsonObject.optString("showPrice")
                var sort_order = dataJsonObject.optInt("sort_order")

                val temp = ProductListingData(active, package_name,
                    ProductID_Price, ProductID_Purchase, SUB_TYPE, Offer,
                    Offer_Name, Pack_type, offer_days, offer_start_time, trial, price, showPrice,
                    sort_order)

                tempList.add(temp)
            }

        }
        return tempList
    }
}