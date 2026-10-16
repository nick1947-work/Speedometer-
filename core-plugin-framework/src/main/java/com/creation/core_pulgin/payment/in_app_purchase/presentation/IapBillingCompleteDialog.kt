package com.creation.core_pulgin.payment.in_app_purchase.presentation

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.TextView
 import com.creation.core_pulgin.core.base.extention.FONT
import com.creation.core_pulgin.core.base.extention.setOnTouchAnimationListener
import com.creation.core_pulgin.core.base.extention.setTextFont
import com.creation.core_pulgin.R


class IapBillingCompleteDialog(val activity: Activity, val callbackListener: () -> Unit) :
    Dialog(activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(false)
        setupUi()
    }

    private fun setupUi() {
        setContentView(R.layout.view_iap_billing_purchase_complete_dialog)
        findViewById<TextView>(R.id.tvTitle).setTextFont(FONT.SEMI_BOLD)
        findViewById<TextView>(R.id.tvSubTitle).setTextFont(FONT.MEDIUM)
        var btnContinue = findViewById<TextView>(R.id.btn_continue)
        btnContinue.setTextFont(FONT.SEMI_BOLD)
        btnContinue.setOnTouchAnimationListener {
            dismiss()
            //CoreUtils.openTargetActivity(activity, "ACTION_FILE_MANAGER_MAIN_SCREEN")
            callbackListener()
        }
    }

}