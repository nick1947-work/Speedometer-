package com.creation.core_pulgin.payment.in_app_purchase.data.model

import androidx.annotation.Keep
import com.android.billingclient.api.ProductDetails
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class ProductListingData(
        @SerializedName("Active")
        @Expose
        var active: String?,

        @SerializedName("Pack Name")
        @Expose
        var packName: String?,

        @SerializedName("ProductID_Price")
        @Expose
        var productIdPrice: String?,

        @SerializedName("ProductID_Purchase")
        @Expose
        var productIdPurchase: String?,


        @SerializedName("SUB_TYPE")
        @Expose
        var subType: String?,

        @SerializedName("Offer")
        @Expose
        var save: String?,

        @SerializedName("Offer_Name")
        @Expose
        var offerName: String?,

        @SerializedName("Pack_type")
        @Expose
        var uiFlag: String?,

        @SerializedName("offer_days")
        @Expose
        var offerDays: String?,

        @SerializedName("offer_start_time")
        @Expose
        var offerStartTime: String?,

        @SerializedName("trial")
        @Expose
        var trialText: String?,

        @SerializedName("price")
        @Expose
        var price: String? ="",

        @SerializedName("showPrice")
        @Expose
        var showPrice: String? ="",

        @SerializedName("sort_order")
        @Expose
        var sortSequence: Int,

        var productDetails: ProductDetails? = null,

        //Using this key in adapter for managing View status (selected/unselected)
        var isSelected:Boolean = false

)

