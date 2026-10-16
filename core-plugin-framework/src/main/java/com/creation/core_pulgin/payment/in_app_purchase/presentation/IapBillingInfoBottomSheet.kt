package com.creation.core_pulgin.payment.in_app_purchase.presentation

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
 import com.creation.core_pulgin.core.base.extention.FONT
import com.creation.core_pulgin.core.base.extention.hide
import com.creation.core_pulgin.core.base.extention.setTextFont
import com.creation.core_pulgin.R

object IapBillingInfoBottomSheet {
    fun show(
        activity: Activity, featureIcon: Int, featureTitle: String,
        description: String,
        detailDescription: String,
        detailInfo:String
    ) {
        var mBottomSheetDialog = BottomSheetDialog(activity, R.style.CustomBottomSheetDialogTheme)
        val view =
            activity.layoutInflater.inflate(R.layout.view_iap_billing_info_bottom_sheet, null)
        mBottomSheetDialog?.setContentView(view)
        mBottomSheetDialog?.show()
        mBottomSheetDialog?.setCanceledOnTouchOutside(true)
        initViews(view, featureIcon, featureTitle, description,detailDescription,detailInfo)

        view.findViewById<TextView>(R.id.tvGotItLabel).setOnClickListener {
            mBottomSheetDialog?.dismiss()
        }
    }

    private fun initViews(
        view: View,
        featureIcon: Int,
        featureTitle: String,
        description: String,
        detailDescription: String,
        detailInfo: String
    ) {
        var ivFeatureIcon = view.findViewById<ImageView>(R.id.ivFeatureIcon)
        ivFeatureIcon.setImageResource(featureIcon)
        var tvFeatureTitle = view.findViewById<TextView>(R.id.tvFeatureTitle)
        tvFeatureTitle.setTextFont(FONT.BOLD)
        tvFeatureTitle.text = featureTitle
        var tvDescription = view.findViewById<TextView>(R.id.tvDescription)
        tvDescription.text = description
        tvDescription.setTextFont(FONT.BOLD)

        var tvDetailDescription = view.findViewById<TextView>(R.id.tvDetailDescription)
        tvDetailDescription.text = detailDescription
        tvDetailDescription.setTextFont(FONT.BOLD)
        if(detailDescription.isNullOrEmpty()){
            view.findViewById<LinearLayout>(R.id.ll2).hide()
        }

        var tvInfo = view.findViewById<TextView>(R.id.tvInfo)
        tvInfo.text = detailInfo
        tvInfo.setTextFont(FONT.BOLD)
        if(detailInfo.isNullOrEmpty()){
            view.findViewById<LinearLayout>(R.id.ll3).hide()
        }

        var tvGotItLabel = view.findViewById<TextView>(R.id.tvGotItLabel)
        tvGotItLabel.setTextFont(FONT.BOLD)
    }

}