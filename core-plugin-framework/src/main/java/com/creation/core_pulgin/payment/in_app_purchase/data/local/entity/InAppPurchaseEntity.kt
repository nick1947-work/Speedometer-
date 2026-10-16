package com.creation.core_pulgin.payment.in_app_purchase.data.local.entity

import androidx.room.Entity
import com.creation.core_pulgin.payment.in_app_purchase.data.model.InAppProductData

@Entity(primaryKeys = ["appId", "productId"])
data class InAppPurchaseEntity(
    var appId: String = "", //it is package name of application
    var productId: String = "",
    var productType: String = "",
    var productName: String = "",
    var active: String = "",
    var isPurchased: Boolean = false,
    var sortSequence: Int,
    var startDate : Long = 0L,
    var endDate : Long = 0L,
    var couponCode: String = "",
    var couponProductId : String =""

) {
    fun toInAppProductData(): InAppProductData {
        return InAppProductData(
            appId = appId,
            productId = productId,
            productType = productType,
            productName = productName,
            active = active,
            isPurchased = isPurchased,
            sortSequence = sortSequence,
            startDate = startDate,
            endDate = endDate

        )
    }
}
