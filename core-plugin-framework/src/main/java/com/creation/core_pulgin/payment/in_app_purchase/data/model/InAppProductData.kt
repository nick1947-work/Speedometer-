package com.creation.core_pulgin.payment.in_app_purchase.data.model

data class InAppProductData(
    var appId: String, //it is package name of application
    var productId: String,
    var productType: String,
    var productName: String,
    var active: String,
    var isPurchased: Boolean,
    var sortSequence: Int,
    var startDate : Long,
    var endDate : Long
)

