package com.creation.core_pulgin.core.utils

 import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
 import com.creation.core_pulgin.BuildConfig
 import com.creation.core_pulgin.core.base.BaseCoreApplication
import com.creation.core_pulgin.payment.in_app_purchase.IapBillingConstants
import java.io.IOException
import java.nio.charset.Charset

const val MAIN_REWARDED_FREE_TIME = "MAIN_REWARDED_FREE_TIME"

object AppUtils {


    fun loadJSONFromAsset(context: Context, fileName: String = ""): String {
        var json: String = ""
        json = try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }
        return json
    }

    fun openTargetActivity(context: Context?, targetActivityIntentFilter: String) {
        val intent = Intent(targetActivityIntentFilter)
        context?.startActivity(intent)
    }

    fun GetLongSharedPreference(ctx: Context, Key: String?): Long {
             val pref: SharedPreferences =
                ctx?.getSharedPreferences("com.filefolder.newfilemanager", Context.MODE_PRIVATE)!!
            return if (pref.contains(Key)) {
                pref.getLong(Key, 0)
            } else 0


    }

    @JvmStatic
    var isPremiumUserCache = -1

    fun isPremiumUser(): Boolean {

        runCatching {


         /*   if (BuildConfig.DEBUG) {

                return true

            }*/



            if (isPremiumUserCache == -1) {


                val context = BaseCoreApplication.getInstance()


                val time = GetLongSharedPreference(context!!, MAIN_REWARDED_FREE_TIME)

                if (time > System.currentTimeMillis()) {

                    isPremiumUserCache = 1

                    return true

                }


                val appSpecificKey =
                    context.packageName + "-" + IapBillingConstants.PREMIUM_USER_PREFS_KEY
                val value = AppPreferences.getBoolean(appSpecificKey)

                isPremiumUserCache = if (value) {
                    1
                } else {
                    0
                }
                return value
            } else {

                return isPremiumUserCache == 1

            }

        }

        return false


    }





}