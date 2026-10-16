package com.creation.core_pulgin.payment.in_app_purchase.domain.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 &2\u00020\u0001:\u0001&B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ \u0010 \u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\u001d0!0\f2\u0006\u0010#\u001a\u00020\nJ\u0018\u0010$\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0\u001d0!0\fR\u000e\u0010\t\u001a\u00020\nX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00110\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000fR\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00110\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000fR\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00110\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000fR\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00110\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u000fR\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u000fR\u001d\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u001d0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u000f\u00a8\u0006\'"}, d2 = {"Lcom/creation/core_pulgin/payment/in_app_purchase/domain/repository/IapBillingDataRepository;", "", "application", "Landroid/app/Application;", "billingClientWrapper", "Lcom/creation/core_pulgin/payment/in_app_purchase/presentation/IapBillingClientWrapper;", "coreDatabase", "Lcom/creation/core_pulgin/core/base/CoreDatabase;", "(Landroid/app/Application;Lcom/creation/core_pulgin/payment/in_app_purchase/presentation/IapBillingClientWrapper;Lcom/creation/core_pulgin/core/base/CoreDatabase;)V", "TAG", "", "basicProductDetails", "Lkotlinx/coroutines/flow/Flow;", "Lcom/android/billingclient/api/ProductDetails;", "getBasicProductDetails", "()Lkotlinx/coroutines/flow/Flow;", "hasPrepaidBasic", "", "getHasPrepaidBasic", "hasPrepaidPremium", "getHasPrepaidPremium", "hasRenewableBasic", "getHasRenewableBasic", "hasRenewablePremium", "getHasRenewablePremium", "isNewPurchaseAcknowledged", "premiumProductDetails", "getPremiumProductDetails", "purchases", "", "Lcom/android/billingclient/api/Purchase;", "getPurchases", "getInAppProducts", "Lcom/creation/core_pulgin/core/utils/Resource;", "Lcom/creation/core_pulgin/payment/in_app_purchase/data/model/ProductListingData;", "defaultLocalPackJson", "getPurchasedItemsList", "Lcom/creation/core_pulgin/payment/in_app_purchase/data/model/InAppProductData;", "Companion", "core-plugin-framework_release"})
public final class IapBillingDataRepository {
    @org.jetbrains.annotations.NotNull()
    private final android.app.Application application = null;
    @org.jetbrains.annotations.NotNull()
    private final com.creation.core_pulgin.payment.in_app_purchase.presentation.IapBillingClientWrapper billingClientWrapper = null;
    @org.jetbrains.annotations.NotNull()
    private final com.creation.core_pulgin.core.base.CoreDatabase coreDatabase = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String TAG = "SubscriptionDataRepository";
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> hasRenewableBasic = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> hasPrepaidBasic = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> hasRenewablePremium = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> hasPrepaidPremium = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<com.android.billingclient.api.ProductDetails> basicProductDetails = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<com.android.billingclient.api.ProductDetails> premiumProductDetails = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.android.billingclient.api.Purchase>> purchases = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> isNewPurchaseAcknowledged = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String BASIC_SUB = "up_basic_sub";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREMIUM_SUB = "up_premium_sub";
    @org.jetbrains.annotations.NotNull()
    public static final com.creation.core_pulgin.payment.in_app_purchase.domain.repository.IapBillingDataRepository.Companion Companion = null;
    
    public IapBillingDataRepository(@org.jetbrains.annotations.NotNull()
    android.app.Application application, @org.jetbrains.annotations.NotNull()
    com.creation.core_pulgin.payment.in_app_purchase.presentation.IapBillingClientWrapper billingClientWrapper, @org.jetbrains.annotations.NotNull()
    com.creation.core_pulgin.core.base.CoreDatabase coreDatabase) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.creation.core_pulgin.core.utils.Resource<java.util.List<com.creation.core_pulgin.payment.in_app_purchase.data.model.InAppProductData>>> getPurchasedItemsList() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.creation.core_pulgin.core.utils.Resource<java.util.List<com.creation.core_pulgin.payment.in_app_purchase.data.model.ProductListingData>>> getInAppProducts(@org.jetbrains.annotations.NotNull()
    java.lang.String defaultLocalPackJson) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> getHasRenewableBasic() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> getHasPrepaidBasic() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> getHasRenewablePremium() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> getHasPrepaidPremium() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.android.billingclient.api.ProductDetails> getBasicProductDetails() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.android.billingclient.api.ProductDetails> getPremiumProductDetails() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.android.billingclient.api.Purchase>> getPurchases() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> isNewPurchaseAcknowledged() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/creation/core_pulgin/payment/in_app_purchase/domain/repository/IapBillingDataRepository$Companion;", "", "()V", "BASIC_SUB", "", "PREMIUM_SUB", "core-plugin-framework_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}