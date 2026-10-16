package com.creation.core_pulgin.payment.in_app_purchase.presentation;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00a2\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\n\b\u0007\u0018\u0000 P2\u00020\u00012\u00020\u00022\u00020\u0003:\u0002PQB\u0005\u00a2\u0006\u0002\u0010\u0004J\b\u00102\u001a\u000203H\u0002J\"\u00104\u001a\u0002032\u0006\u00105\u001a\u0002062\u0006\u00107\u001a\u0002062\b\u00108\u001a\u0004\u0018\u000109H\u0014J\b\u0010:\u001a\u000203H\u0016J\u0012\u0010;\u001a\u0002032\b\u0010<\u001a\u0004\u0018\u00010=H\u0016J\b\u0010>\u001a\u000203H\u0002J\u0012\u0010?\u001a\u0002032\b\u0010@\u001a\u0004\u0018\u00010AH\u0014J\b\u0010B\u001a\u000203H\u0002J,\u0010C\u001a\u0002032\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00070\u001b2\u0014\b\u0002\u0010E\u001a\u000e\u0012\u0004\u0012\u00020G\u0012\u0004\u0012\u00020H0FH\u0002J\b\u0010I\u001a\u000203H\u0002J\b\u0010J\u001a\u000203H\u0002J\b\u0010K\u001a\u000203H\u0002J\b\u0010L\u001a\u000203H\u0002J\b\u0010M\u001a\u000203H\u0002J\u0010\u0010N\u001a\u0002032\u0006\u0010O\u001a\u00020\tH\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0014\u001a\u0004\u0018\u00010\u00158BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00070\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082.\u00a2\u0006\u0002\n\u0000R\u001c\u0010\"\u001a\u0004\u0018\u00010\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u000e\u0010\'\u001a\u00020(X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020(X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020(X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020(X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020(X\u0082.\u00a2\u0006\u0002\n\u0000R\u001b\u0010-\u001a\u00020.8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b1\u0010\u0019\u001a\u0004\b/\u00100\u00a8\u0006R"}, d2 = {"Lcom/creation/core_pulgin/payment/in_app_purchase/presentation/IapBillingActivity;", "Lcom/creation/core_pulgin/core/base/CoreBaseActivity;", "Landroid/view/View$OnClickListener;", "Lcom/creation/core_pulgin/payment/in_app_purchase/presentation/IapBillingAdapter$ItemCallbackListener;", "()V", "dataItemsList", "Ljava/util/ArrayList;", "Lcom/creation/core_pulgin/payment/in_app_purchase/data/model/ProductListingData;", "fromNotification", "", "getFromNotification", "()Z", "setFromNotification", "(Z)V", "iapBillingAdapter", "Lcom/creation/core_pulgin/payment/in_app_purchase/presentation/IapBillingAdapter;", "ivClose", "Landroid/widget/ImageView;", "llPacksContainer", "Landroid/widget/LinearLayout;", "params", "Lcom/creation/core_pulgin/payment/in_app_purchase/presentation/IapBillingActivity$Params;", "getParams", "()Lcom/creation/core_pulgin/payment/in_app_purchase/presentation/IapBillingActivity$Params;", "params$delegate", "Lkotlin/Lazy;", "productDataList", "", "progressBarCyclic", "Landroid/widget/ProgressBar;", "purchaseCompleteDialog", "Lcom/creation/core_pulgin/payment/in_app_purchase/presentation/IapBillingCompleteDialog;", "purchaseProductBtn", "Landroid/widget/Button;", "selectedProduct", "getSelectedProduct", "()Lcom/creation/core_pulgin/payment/in_app_purchase/data/model/ProductListingData;", "setSelectedProduct", "(Lcom/creation/core_pulgin/payment/in_app_purchase/data/model/ProductListingData;)V", "selected_pack_name", "Landroid/widget/TextView;", "subTitle", "title", "tvContinueWithAds", "tvFreeTrial", "viewModel", "Lcom/creation/core_pulgin/payment/in_app_purchase/presentation/IapBillingViewModel;", "getViewModel", "()Lcom/creation/core_pulgin/payment/in_app_purchase/presentation/IapBillingViewModel;", "viewModel$delegate", "initUi", "", "onActivityResult", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "onBackPressed", "onClick", "view", "Landroid/view/View;", "onClickBuyProduct", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onSetResult", "setAdapter", "dataList", "productStatusMap", "", "", "Lcom/creation/core_pulgin/payment/in_app_purchase/data/model/InAppProductData;", "setFont", "setListener", "setupDataList", "showAppSpecificFeature", "showPurchaseCompleteDialog", "togglePurchaseBtn", "buttonStatus", "Companion", "Params", "core-plugin-framework_debug"})
public final class IapBillingActivity extends com.creation.core_pulgin.core.base.CoreBaseActivity implements android.view.View.OnClickListener, com.creation.core_pulgin.payment.in_app_purchase.presentation.IapBillingAdapter.ItemCallbackListener {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy params$delegate = null;
    
    /**
     * Always start this activity as a sub activity
     */
    private boolean fromNotification = false;
    public static final int RequestCode = 10000;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PARAMS = "params";
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.creation.core_pulgin.payment.in_app_purchase.data.model.ProductListingData> dataItemsList;
    private com.creation.core_pulgin.payment.in_app_purchase.presentation.IapBillingAdapter iapBillingAdapter;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    private android.widget.Button purchaseProductBtn;
    private android.widget.TextView tvContinueWithAds;
    private android.widget.TextView selected_pack_name;
    private android.widget.TextView title;
    private android.widget.TextView subTitle;
    private android.widget.TextView tvFreeTrial;
    private android.widget.ProgressBar progressBarCyclic;
    private android.widget.LinearLayout llPacksContainer;
    private android.widget.ImageView ivClose;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.creation.core_pulgin.payment.in_app_purchase.data.model.ProductListingData> productDataList;
    
    /**
     * IT is called when item is clicked or Upgrade now btn is clicked
     */
    @org.jetbrains.annotations.Nullable()
    private com.creation.core_pulgin.payment.in_app_purchase.data.model.ProductListingData selectedProduct;
    private com.creation.core_pulgin.payment.in_app_purchase.presentation.IapBillingCompleteDialog purchaseCompleteDialog;
    @org.jetbrains.annotations.NotNull()
    public static final com.creation.core_pulgin.payment.in_app_purchase.presentation.IapBillingActivity.Companion Companion = null;
    
    public IapBillingActivity() {
        super();
    }
    
    private final com.creation.core_pulgin.payment.in_app_purchase.presentation.IapBillingActivity.Params getParams() {
        return null;
    }
    
    /**
     * Always start this activity as a sub activity
     */
    public final boolean getFromNotification() {
        return false;
    }
    
    /**
     * Always start this activity as a sub activity
     */
    public final void setFromNotification(boolean p0) {
    }
    
    private final com.creation.core_pulgin.payment.in_app_purchase.presentation.IapBillingViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initUi() {
    }
    
    private final void setFont() {
    }
    
    @java.lang.Override()
    public void onBackPressed() {
    }
    
    private final void setupDataList() {
    }
    
    private final void setListener() {
    }
    
    private final void setAdapter(java.util.List<com.creation.core_pulgin.payment.in_app_purchase.data.model.ProductListingData> dataList, java.util.Map<java.lang.String, com.creation.core_pulgin.payment.in_app_purchase.data.model.InAppProductData> productStatusMap) {
    }
    
    /**
     * IT is called when item is clicked or Upgrade now btn is clicked
     */
    @org.jetbrains.annotations.Nullable()
    public final com.creation.core_pulgin.payment.in_app_purchase.data.model.ProductListingData getSelectedProduct() {
        return null;
    }
    
    /**
     * IT is called when item is clicked or Upgrade now btn is clicked
     */
    public final void setSelectedProduct(@org.jetbrains.annotations.Nullable()
    com.creation.core_pulgin.payment.in_app_purchase.data.model.ProductListingData p0) {
    }
    
    private final void onClickBuyProduct() {
    }
    
    @java.lang.Override()
    public void togglePurchaseBtn(boolean buttonStatus) {
    }
    
    private final void showPurchaseCompleteDialog() {
    }
    
    private final void onSetResult() {
    }
    
    private final void showAppSpecificFeature() {
    }
    
    @java.lang.Override()
    public void onClick(@org.jetbrains.annotations.Nullable()
    android.view.View view) {
    }
    
    @java.lang.Override()
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J \u0010\r\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00062\b\b\u0002\u0010\u000b\u001a\u00020\fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/creation/core_pulgin/payment/in_app_purchase/presentation/IapBillingActivity$Companion;", "", "()V", "PARAMS", "", "RequestCode", "", "getLaunchIntent", "Landroid/content/Intent;", "context", "Landroid/content/Context;", "params", "Lcom/creation/core_pulgin/payment/in_app_purchase/presentation/IapBillingActivity$Params;", "launchForResult", "", "Landroid/app/Activity;", "requestCode", "core-plugin-framework_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final void launchForResult(@org.jetbrains.annotations.NotNull()
        android.app.Activity context, int requestCode, @org.jetbrains.annotations.NotNull()
        com.creation.core_pulgin.payment.in_app_purchase.presentation.IapBillingActivity.Params params) {
        }
        
        private final android.content.Intent getLaunchIntent(android.content.Context context, com.creation.core_pulgin.payment.in_app_purchase.presentation.IapBillingActivity.Params params) {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\t\u0010\t\u001a\u00020\nH\u00d6\u0001J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u00d6\u0003J\t\u0010\u000f\u001a\u00020\nH\u00d6\u0001J\t\u0010\u0010\u001a\u00020\u0003H\u00d6\u0001J\u0019\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\nH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0016"}, d2 = {"Lcom/creation/core_pulgin/payment/in_app_purchase/presentation/IapBillingActivity$Params;", "Landroid/os/Parcelable;", "defaultLocalPackJson", "", "(Ljava/lang/String;)V", "getDefaultLocalPackJson", "()Ljava/lang/String;", "component1", "copy", "describeContents", "", "equals", "", "other", "", "hashCode", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "core-plugin-framework_debug"})
    @kotlinx.parcelize.Parcelize()
    public static final class Params implements android.os.Parcelable {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String defaultLocalPackJson = null;
        
        public Params(@org.jetbrains.annotations.NotNull()
        java.lang.String defaultLocalPackJson) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getDefaultLocalPackJson() {
            return null;
        }
        
        public Params() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.creation.core_pulgin.payment.in_app_purchase.presentation.IapBillingActivity.Params copy(@org.jetbrains.annotations.NotNull()
        java.lang.String defaultLocalPackJson) {
            return null;
        }
        
        @java.lang.Override()
        public int describeContents() {
            return 0;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
        
        @java.lang.Override()
        public void writeToParcel(@org.jetbrains.annotations.NotNull()
        android.os.Parcel parcel, int flags) {
        }
    }
}