package com.creation.core_pulgin.payment.in_app_purchase.presentation

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.creation.core_pulgin.core.base.CoreDatabase
import com.creation.core_pulgin.payment.in_app_purchase.data.model.MainState
import com.creation.core_pulgin.payment.in_app_purchase.data.model.ProductListingData
import com.creation.core_pulgin.core.utils.Resource
import com.creation.core_pulgin.payment.in_app_purchase.data.local.entity.InAppPurchaseEntity
import com.creation.core_pulgin.payment.in_app_purchase.data.model.InAppProductData
import com.creation.core_pulgin.payment.in_app_purchase.domain.repository.IapBillingDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IapBillingViewModel @Inject constructor(
    private val application: Application,
    private val coreDatabase: CoreDatabase
) :
    AndroidViewModel(application) {

    private val TAG = "InAppPurchaseViewModel"

    var selectedProduct: ProductDetails? = null

    var billingClient: IapBillingClientWrapper = IapBillingClientWrapper(application, coreDatabase)

    private var repo: IapBillingDataRepository =
        IapBillingDataRepository(application, billingClient, coreDatabase)

    private val _billingConnectionState = MutableLiveData(false)
    val billingConnectionState: LiveData<Boolean> = _billingConnectionState

    private val _destinationScreen = MutableLiveData<DestinationScreen>()
    val destinationScreen: LiveData<DestinationScreen> = _destinationScreen


    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    //Start the billing connection when the viewModel is initialized.
    private fun startBillingConnection(
        isPurchaseRestoreCall: Boolean,
        rcProductItemList: List<ProductListingData> = emptyList()
    ) {
        if (rcProductItemList.isNullOrEmpty()) {
            //We can emit empty data list here //TODO KP
        } else {
            billingClient.startBillingConnection(
                billingConnectionState = _billingConnectionState,
                rcProductItemList = rcProductItemList,
                isPurchaseRestoreCall = isPurchaseRestoreCall
            )
        }
    }


    fun resetAcknowledge() {
        billingClient.resetAcknowledge()
    }


    /**
     * Prepare ProductItemList from Remote config
     */
    private val _rcProductItemList =
        MutableStateFlow<List<ProductListingData>>(listOf())
    val rcProductItemList = _rcProductItemList.asStateFlow()

    fun getInAppPacksFromRc(
        defaultLocalPackJson: String,
        isPurchaseRestoreCall: Boolean
    ) {
        viewModelScope.launch(Dispatchers.IO){
            repo.getInAppProducts(defaultLocalPackJson).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        var productList = result.data ?: emptyList()
                        productList.sortedBy {
                            it.sortSequence
                        }
                        _rcProductItemList.value = productList
                        startBillingConnection(
                            isPurchaseRestoreCall,
                            _rcProductItemList.value
                        )
                    }
                    is Resource.Error -> {
                        _eventFlow.emit(
                            UIEvent.ShowSnackbar(
                                result.message ?: "Unknown error"
                            )
                        )
                    }
                    is Resource.Loading -> {
                    }
                }
            }.launchIn(this)
        }
    }

    fun persistLtoProduct(productDataItem : ProductListingData){
        CoroutineScope(Dispatchers.IO).launch {
            // save Products to Db for future use
            val inAppPurchaseDao = coreDatabase.inAppPurchaseDao
            val dbProduct = inAppPurchaseDao.getInAppPurchaseById(
                application.packageName,
                productDataItem.productIdPrice ?: ""
            )
            val isPurchased = if (dbProduct != null) dbProduct.isPurchased else false
            val inAppPurchaseEntity = InAppPurchaseEntity(
                appId = application.packageName,
                productId = productDataItem.productIdPurchase ?: "",
                productType = productDataItem.subType ?: "",
                productName = productDataItem.packName ?: "",
                active = productDataItem.active ?: "",
                isPurchased = isPurchased,
                sortSequence = productDataItem.sortSequence
            )
            if(dbProduct != null){
                inAppPurchaseDao.insert(inAppPurchaseEntity)
            }else{
                inAppPurchaseDao.update(inAppPurchaseEntity)
            }

        }
    }


    fun startBillingConnectionForLto(
        isPurchaseRestoreCall: Boolean = false,
        rcProductItemList: List<ProductListingData> = emptyList()
    ) {
        Log.d("DataFetched", "startBillingConnectionForLto\t" + "started")

        startBillingConnection(
            isPurchaseRestoreCall,
            rcProductItemList
        )
    }


    /**
     * Fetched already purchased product from db
     * or iab billing restore data
     */
    private val _purchasedProductMap = billingClient._purchasedProductMap

    //        MutableStateFlow<Map<String, InAppProductData>>(emptyMap())
    val purchasedProductMap =
        _purchasedProductMap.asStateFlow()

    fun getPurchasedItemsList() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getPurchasedItemsList().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        //Result list
                        var resultList = result.data ?: emptyList<InAppProductData>()
                        //Create map of purchased products
                        var productMap = emptyMap<String, InAppProductData>()
                        productMap = resultList.associateBy {
                            it.productId
                        }
                        _purchasedProductMap.value = productMap
                    }
                    is Resource.Error -> {
//                        _state.value = state.value.copy(
//                            inAppProductItems = result.data ?: emptyList(),
//                            isLoading = false
//                        )
                        _eventFlow.emit(
                            UIEvent.ShowSnackbar(
                                result.message ?: "Unknown error"
                            )
                        )
                    }
                    is Resource.Loading -> {
//                        _state.value = state.value.copy(
//                            inAppProductItems = result.data ?: emptyList(),
//                            isLoading = true
//                        )
                    }
                }
            }.launchIn(this)
        }

    }

    val productsForSaleFlows = billingClient.purchasesDetailNew
//        MutableStateFlow<List<ProductDetails>>(listOf())
//    val purchasesDetailNew = _purchasesDetailNew.asStateFlow()

    // Tracks new purchases acknowledgement state.
    // Set to true when a purchase is acknowledged and false when not.
    val isNewPurchaseAcknowledged = billingClient.isNewPurchaseAcknowledged

    private val _isPremiumUserFlow = MutableStateFlow(false)
    val isPremiumUserFlow = _isPremiumUserFlow.asStateFlow()

    val isPurchasedRestored = billingClient.isPurchasedRestored

    fun isPremiumUser() {
        viewModelScope.launch(Dispatchers.IO){
            val inAppPurchaseDao = coreDatabase?.inAppPurchaseDao
            if (inAppPurchaseDao != null) {
                var purchasedItemsList = inAppPurchaseDao.isPremiumUser(application.packageName)
                _isPremiumUserFlow.value = purchasedItemsList.isNotEmpty()
            }
        }
    }


    // The userCurrentSubscriptionFlow object combines all the possible subscription flows into one
    // for emission.
    private val userCurrentSubscriptionFlow = combine(
        repo.hasRenewableBasic,
        repo.hasPrepaidBasic,
        repo.hasRenewablePremium,
        repo.hasPrepaidPremium
    ) { hasRenewableBasic,
        hasPrepaidBasic,
        hasRenewablePremium,
        hasPrepaidPremium
        ->
        MainState(
            hasRenewableBasic = hasRenewableBasic,
            hasPrepaidBasic = hasPrepaidBasic,
            hasRenewablePremium = hasRenewablePremium,
            hasPrepaidPremium = hasPrepaidPremium
        )
    }

    // Current purchases.
    val currentPurchasesFlow = repo.purchases

    init {
        viewModelScope.launch {
            userCurrentSubscriptionFlow.collectLatest { collectedSubscriptions ->
                when {
                    collectedSubscriptions.hasRenewableBasic == true &&
                            collectedSubscriptions.hasRenewablePremium == false -> {
                        _destinationScreen.postValue(DestinationScreen.BASIC_RENEWABLE_PROFILE)
                    }
                    collectedSubscriptions.hasRenewablePremium == true &&
                            collectedSubscriptions.hasRenewableBasic == false -> {
                        _destinationScreen.postValue(DestinationScreen.PREMIUM_RENEWABLE_PROFILE)
                    }
                    collectedSubscriptions.hasPrepaidBasic == true &&
                            collectedSubscriptions.hasPrepaidPremium == false -> {
                        _destinationScreen.postValue(DestinationScreen.BASIC_PREPAID_PROFILE_SCREEN)
                    }
                    collectedSubscriptions.hasPrepaidPremium == true &&
                            collectedSubscriptions.hasPrepaidBasic == false -> {
                        _destinationScreen.postValue(
                            DestinationScreen.PREMIUM_PREPAID_PROFILE_SCREEN
                        )
                    }
                    else -> {
                        _destinationScreen.postValue(DestinationScreen.SUBSCRIPTIONS_OPTIONS_SCREEN)
                    }
                }
            }

        }
    }


    fun buy(
        productDetails: ProductDetails?,
        currentPurchases: List<Purchase>?,
        activity: Activity,
        tag: String = "",
        isSubscriptionType: Boolean = false,
        isLtoProduct: Boolean = false,
        selectedOfferIndex: Int = 0 // You can pass offer index if needed for subscriptions
    ) {
        kotlin.runCatching {
//            billingClient.setProductType(isLtoProduct)

            if (productDetails == null) {
                Log.e("Billing", "Product details are null for tag: $tag")
                // You can call your listener or handle error here
                return
            }

            val productDetailsParamsList: List<BillingFlowParams.ProductDetailsParams>

            if (isSubscriptionType) {
                val offerDetails = productDetails.subscriptionOfferDetails
                if (offerDetails != null && selectedOfferIndex >= 0 && selectedOfferIndex < offerDetails.size) {
                    // Correct offer index, build parameters
                    productDetailsParamsList = listOf(
                        BillingFlowParams.ProductDetailsParams.newBuilder()
                            .setProductDetails(productDetails)
                            .setOfferToken(offerDetails[selectedOfferIndex].offerToken)
                            .build()
                    )
                } else {
                    Log.e(
                        "Billing",
                        "Invalid selectedOfferIndex: $selectedOfferIndex for product: ${productDetails.name}. Offer size: ${offerDetails?.size ?: "null"}"
                    )
                    // You can notify error to listener here
                    return
                }
            } else {
                // Non-subscription product
                productDetailsParamsList = listOf(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                        .setProductDetails(productDetails)
                        .build()
                )
            }

            val billingFlowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(productDetailsParamsList)
                .build()

            val billingResult = billingClient.launchBillingFlow(activity, billingFlowParams)
            Log.d("Billing", "Billing Flow launched with result code: ${billingResult}")
        }
    }

    // When an activity is destroyed the viewModel's onCleared is called, so we terminate the
    // billing connection.
    override fun onCleared() {
        billingClient.terminateBillingConnection()
    }

    fun persistRecentlyPurchasedProduct(purchasedProductId: String) {
        viewModelScope.launch {
            _isPremiumUserFlow.value = true
            billingClient.persistPurchasedProduct(purchasedProductId)
        }
    }

    /**
     * Enum representing the various screens a user can be redirected to.
     */
    enum class DestinationScreen {
        SUBSCRIPTIONS_OPTIONS_SCREEN,
        BASIC_PREPAID_PROFILE_SCREEN,
        BASIC_RENEWABLE_PROFILE,
        PREMIUM_PREPAID_PROFILE_SCREEN,
        PREMIUM_RENEWABLE_PROFILE;
    }

    companion object {
        private const val TAG: String = "MainViewModel"

        private const val MAX_CURRENT_PURCHASES_ALLOWED = 1
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
    }
}
