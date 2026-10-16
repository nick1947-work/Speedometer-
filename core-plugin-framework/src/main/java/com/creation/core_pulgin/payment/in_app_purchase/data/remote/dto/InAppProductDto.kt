/*

import androidx.annotation.Keep
import com.android.billingclient.api.ProductDetails
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class InAppProductDto(
        @SerializedName("Active")
        @Expose
        var active: String?,

        @SerializedName("Pack Name")
        @Expose
        var packName: String?,

        @SerializedName("ProductID_Price")
        @Expose
        var productID: String?,

        @SerializedName("ProductID_Purchase")
        @Expose
        var packId: String?,


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

        @SerializedName("sortSequence")
        @Expose
        var sortSequence: Int,

        var productDetails: ProductDetails

){
        fun toInAppProductData(): InAppProductData {
                return InAppProductData(
                        appId = "",
                        productId = packId ?: "",
                        productType = subType ?: "",
                        productName = packName ?: "",
                        active = active ?: "",
                        isPurchased = false,
                        sortSequence = 0
                )
        }
}

*/
