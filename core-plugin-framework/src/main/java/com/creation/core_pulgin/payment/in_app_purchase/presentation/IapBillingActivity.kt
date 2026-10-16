package com.creation.core_pulgin.payment.in_app_purchase.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.View.OnClickListener
import android.widget.*
import androidx.activity.viewModels
 import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
 import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
 import com.creation.core_pulgin.core.base.CoreBaseActivity
import com.creation.core_pulgin.core.base.extention.*
import com.creation.core_pulgin.core.utils.AppUtils
import com.creation.core_pulgin.core.utils.FirebaseRemoteConfigUtils
import com.creation.core_pulgin.payment.FeatureInfo
import com.creation.core_pulgin.payment.in_app_purchase.data.model.InAppProductData
import com.creation.core_pulgin.payment.in_app_purchase.data.model.ProductListingData
import com.creation.core_pulgin.R
import com.creation.core_pulgin.payment.in_app_purchase.IapBillingConstants.SET_CUSTOM_RESULT
import dagger.hilt.android.AndroidEntryPoint
 import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.parcelize.Parcelize
 const val FROM_NOTIFICATION_EXTRA = "FROM_NOTIFICATION"
const val FILE_MANAGER_SPLASH_SCREEN_ACTION = "ACTION_FILE_MANAGER_SPLASH_SCREEN_NEW"
@AndroidEntryPoint
class IapBillingActivity : CoreBaseActivity(), OnClickListener,
    IapBillingAdapter.ItemCallbackListener {

    @Parcelize
    data class Params(
        val defaultLocalPackJson: String = "",
    ) : Parcelable

    private val params: Params? by lazy {intent.getParcelableExtra(PARAMS) }

    /**
     * Always start this activity as a sub activity
     */

    var fromNotification = false
    companion object {
        const val RequestCode = 10000
         const val PARAMS = "params"
        fun launchForResult(context: Activity, requestCode: Int, params: Params = Params()) {

            if (context != null) {
                runCatching {
                context.startActivityForResult(getLaunchIntent(context, params), requestCode)
                }
            }
        }

        private fun getLaunchIntent(context: Context, params: Params): Intent {
            return Intent(context, IapBillingActivity::class.java).apply {
                this.putExtra(PARAMS, params)
                this.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
        }
    }


    private var dataItemsList = ArrayList<ProductListingData>()
    private lateinit var iapBillingAdapter: IapBillingAdapter
    private val viewModel: IapBillingViewModel by viewModels()

    private lateinit var purchaseProductBtn: Button
    private lateinit var tvContinueWithAds: TextView
    private lateinit var selected_pack_name: TextView
    private lateinit var title: TextView
    private lateinit var subTitle: TextView
    private lateinit var tvFreeTrial: TextView


//    private lateinit var tvPackLabel: TextView
//    private lateinit var tvTheme: TextView
//    private lateinit var tvNoAds: TextView
//    private lateinit var tvTrash: TextView
//    private lateinit var tvMusic: TextView
//    private lateinit var tvCrop: TextView
//    private lateinit var tvFilterDuplicate: TextView

    private lateinit var progressBarCyclic: ProgressBar
    private lateinit var llPacksContainer: LinearLayout
    private lateinit var ivClose: ImageView
    private var productDataList: List<ProductListingData> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setFullScreenMode()
        initUi()
        setupDataList()
        setListener()
        showAppSpecificFeature()
    }


    private fun initUi() {
        setContentView(R.layout.premium_screen_features)
        val recyclerView: RecyclerView = findViewById(R.id.pack_recyclerview)
        purchaseProductBtn = findViewById(R.id.btn_continue)
        tvContinueWithAds = findViewById(R.id.tvContinueWithAds)
        selected_pack_name = findViewById(R.id.selected_pack_name)
        progressBarCyclic = findViewById(R.id.progressBarCyclic)
        ivClose = findViewById(R.id.ivClose)
        iapBillingAdapter = IapBillingAdapter(
            this@IapBillingActivity, dataItemsList, this,
            onSelect = {
                selected_pack_name.text = it
            }
        )
        val layoutManager = GridLayoutManager(applicationContext,2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = iapBillingAdapter
        llPacksContainer = findViewById(R.id.llPacksContainer)
//        tvTheme = findViewById(R.id.tvTheme)
//        tvNoAds = findViewById(R.id.tvNoAds)
//        tvTrash = findViewById(R.id.tvTrash)
//        tvMusic = findViewById(R.id.tvMusic)
//        tvCrop = findViewById(R.id.tvCrop)
//        tvFilterDuplicate = findViewById(R.id.tvFilterDuplicate)
        title = findViewById(R.id.title)
        subTitle = findViewById(R.id.subTitle)
        tvFreeTrial  = findViewById(R.id.tvFreeTrail)
//        tvPackLabel = findViewById(R.id.tvPackLabel)
        fromNotification = intent.getBooleanExtra(FROM_NOTIFICATION_EXTRA,false)
        setFont()

        var tvTermsUsageMsg = findViewById<TextView>(R.id.tvTermsUsageMsg)
        CoroutineScope(Dispatchers.IO).launch {
            FirebaseApp.initializeApp(this@IapBillingActivity)
            val mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
            var isFreeTrialEnabled = 
                mFirebaseRemoteConfig.getBoolean(FirebaseRemoteConfigUtils.FREE_TRIAL_PACK_STATUS)
            withContext(Dispatchers.Main){
                if(isFreeTrialEnabled){
                    tvTermsUsageMsg.text = getString(R.string.new_billing_info)
                }else{
                    tvTermsUsageMsg.text = getString(R.string.billing_info_config)
                }
            }
        }
    }

    private fun setFont() {
        title.setTextFont(FONT.SEMI_BOLD)
        subTitle.setTextFont(FONT.MEDIUM)
        purchaseProductBtn.setTextFont(FONT.BOLD)
        tvContinueWithAds.setTextFont(FONT.BOLD)
//        tvTheme.setTextFont(FONT.BOLD)
//        tvNoAds.setTextFont(FONT.BOLD)
//        tvTrash.setTextFont(FONT.BOLD)
//        tvMusic.setTextFont(FONT.BOLD)
//        tvCrop.setTextFont(FONT.BOLD)
//        tvFilterDuplicate.setTextFont(FONT.BOLD)
//        tvPackLabel.setTextFont(FONT.BOLD)
//        findViewById<TextView>(R.id.tvUpcoming).setTextFont(FONT.BOLD)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if(fromNotification)
        {
            val mIntent = Intent(FILE_MANAGER_SPLASH_SCREEN_ACTION)
            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            mIntent.putExtra(FROM_NOTIFICATION_EXTRA, true)
            startActivity(mIntent)
            finish()
        }
    }
    private fun setupDataList() {
        viewModel.getInAppPacksFromRc(params?.defaultLocalPackJson?:"",false)

        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                viewModel.productsForSaleFlows.collect {
                    productDataList = it
                    setAdapter(productDataList)
                    //Get Already owned products
                    viewModel.getPurchasedItemsList()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isNewPurchaseAcknowledged.collect {
                if (it) {
                    viewModel.persistRecentlyPurchasedProduct(
                        selectedProduct?.productIdPurchase ?: ""
                    )
                    showPurchaseCompleteDialog()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.purchasedProductMap.collect { productStatusMap ->
                setAdapter(productDataList, productStatusMap)
            }
        }
    }

    private fun setListener() {
        purchaseProductBtn.setOnClickListener(this)
        tvContinueWithAds.setOnClickListener(this)
        ivClose.setOnClickListener(this)
        togglePurchaseBtn(false)//default
    }

    private fun setAdapter(
        dataList: List<ProductListingData>,
        productStatusMap: Map<String, InAppProductData> = emptyMap()
    ) {

        dataItemsList.clear()
        dataItemsList.addAll(dataList)

        iapBillingAdapter.productStatusMap = productStatusMap
        //  .setProductStatusMap(productStatusMap)
        iapBillingAdapter.notifyDataSetChanged()

        //set First item as selected
        if (dataItemsList.isNotEmpty()) {
            var dataItem = dataItemsList[0]
            dataItem.isSelected = false
            iapBillingAdapter.onClickViewItem(0, dataItem)
        }

        progressBarCyclic.postDelayed(kotlinx.coroutines.Runnable {
            llPacksContainer.visibility = View.VISIBLE
            progressBarCyclic.visibility = View.GONE
        }, 500)
    }

    /**
     * IT is called when item is clicked or Upgrade now btn is clicked
     */
    var selectedProduct: ProductListingData? = null
    private fun onClickBuyProduct(
    ) {
        selectedProduct = iapBillingAdapter.getSelectedProduct()
        selectedProduct?.productDetails?.let {
            viewModel.buy(
                productDetails = it, currentPurchases = null, tag = "", activity = this,
                isSubscriptionType = selectedProduct?.subType.equals("true", true)
            )
        }
    }


    override fun togglePurchaseBtn(buttonStatus: Boolean) {
        if(::purchaseProductBtn.isInitialized){
            if (buttonStatus) {
                purchaseProductBtn.isEnabled = true
                purchaseProductBtn.setOnClickListener(this)
                purchaseProductBtn.setBackgroundResource(R.drawable.shape_gradient_btn_bg_enabled)
            } else {
                purchaseProductBtn.isEnabled = true
                purchaseProductBtn.setBackgroundResource(R.drawable.shape_gradient_btn_bg_disabled)
            }
        }
    }

    private lateinit var purchaseCompleteDialog: IapBillingCompleteDialog
    private fun showPurchaseCompleteDialog() {
        if (!this::purchaseCompleteDialog.isInitialized) {
            purchaseCompleteDialog = IapBillingCompleteDialog(this, ::onSetResult)
        }
        AppUtils.isPremiumUserCache = -1
        purchaseCompleteDialog.show()
    }

    private fun onSetResult() {

        val intent = Intent()
        setResult(SET_CUSTOM_RESULT, intent);
        finish()
    }

    private fun showAppSpecificFeature() {
        var appFeatures: List<FeatureInfo> =
            FeatureInfo.getAppSpecificFeatures(applicationContext.packageName)
        appFeatures.forEach { appFeature ->
            when (appFeature) {
//                FeatureInfo.THEME -> {
//                    findViewById<LinearLayout>(R.id.llParentFilterDuplicate).show()
//                }
//                FeatureInfo.NO_ADS -> {
//                    findViewById<LinearLayout>(R.id.llParentNoAds).show()
//                }
//                FeatureInfo.TRASH -> {
//                    findViewById<LinearLayout>(R.id.llParentTrash).show()
//                }
//                FeatureInfo.LYRICS -> {
//                    findViewById<LinearLayout>(R.id.llParentTrash).show()
//                }
//                FeatureInfo.CROP -> {
//                    findViewById<LinearLayout>(R.id.llParentCrop).show()
//                }
//                FeatureInfo.FILTER_DUPLICATE -> {
//                    findViewById<LinearLayout>(R.id.llParentFilterDuplicate).show()
//                }
//                FeatureInfo.THEME -> {
//                    findViewById<LinearLayout>(R.id.llParentTheme).show()
//                }
//                FeatureInfo.UPCOMING -> {
//                    findViewById<LinearLayout>(R.id.llParentUpcoming).show()
//                }
                else -> {}
            }
        }

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            purchaseProductBtn.id -> {
                if (purchaseProductBtn.isEnabled)
                    onClickBuyProduct()
            }
            ivClose.id->{

                finish()
            }

            tvContinueWithAds.id -> {

                CoroutineScope(Dispatchers.IO).launch {

                    val defaultLocalPackJson = AppUtils.loadJSONFromAsset(this@IapBillingActivity, "DefaultPackJsonData")

                    IapBillingPurchaseRestoreActivity.launchForResult(this@IapBillingActivity, IapBillingPurchaseRestoreActivity.RequestCode, IapBillingPurchaseRestoreActivity.Params(defaultLocalPackJson))

                }

            }

           /* findViewById<ImageView>(R.id.llTheme).id -> {
                IapBillingInfoBottomSheet.show(
                    this,
                    R.drawable.ic_theme,
                    resources.getString(R.string.themes_title),
                    resources.getString(R.string.themes_description1),
                    resources.getString(R.string.themes_description2),
                    resources.getString(R.string.themes_description3)
                )
            }
            findViewById<ImageView>(R.id.llNoAds).id -> {
                IapBillingInfoBottomSheet.show(
                    this,
                    R.drawable.ic_no_ads,
                    resources.getString(R.string.ad_free_title),
                    resources.getString(R.string.ad_free_description1),
                    resources.getString(R.string.ad_free_description2),
                    resources.getString(R.string.ad_free_description3)
                )
            }
            findViewById<ImageView>(R.id.llTrash).id -> {
                IapBillingInfoBottomSheet.show(
                    this,
                    R.drawable.ic_trash,
                    resources.getString(R.string.trash_title),
                    resources.getString(R.string.trash_description1),
                    resources.getString(R.string.trash_description2),
                    resources.getString(R.string.trash_description3)
                )
            }
            findViewById<ImageView>(R.id.llLyrics).id -> {
                IapBillingInfoBottomSheet.show(
                    this,
                    R.drawable.ic_music,
                    resources.getString(R.string.lyrics_title),
                    resources.getString(R.string.lyrics_description1),
                    resources.getString(R.string.lyrics_description2),
                    resources.getString(R.string.lyrics_description3)
                )
            }
            findViewById<ImageView>(R.id.llCrop).id -> {
                IapBillingInfoBottomSheet.show(
                    this,
                    R.drawable.ic_crop,
                    resources.getString(R.string.crop_title),
                    resources.getString(R.string.crop_description1),
                    resources.getString(R.string.crop_description2),
                    resources.getString(R.string.crop_description3)
                )
            }
            findViewById<ImageView>(R.id.llFilterDuplicate).id -> {
                IapBillingInfoBottomSheet.show(
                    this,
                    R.drawable.ic_filter_duplicate,
                    resources.getString(R.string.filter_duplicate_title),
                    resources.getString(R.string.filter_duplicate_description1),
                    resources.getString(R.string.filter_duplicate_description2),
                    resources.getString(R.string.filter_duplicate_description3)
                )
            }

            findViewById<ImageView>(R.id.ivUpcoming).id -> {
                IapBillingInfoBottomSheet.show(
                    this,
                    R.drawable.ic_upcoming,
                    resources.getString(R.string.upcoming_feature_title),
                    resources.getString(R.string.upcoming_feature_description),
                    ""
                ,""
                )
            }*/
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == IapBillingPurchaseRestoreActivity.RequestCode && resultCode == RESULT_OK){

            onSetResult()

        }

    }
}

