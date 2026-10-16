package com.creation.core_pulgin.payment.in_app_purchase.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\bg\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u001e\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001c\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\nJ0\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\t2\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000f\u00a8\u0006\u0010"}, d2 = {"Lcom/creation/core_pulgin/payment/in_app_purchase/data/local/IapBillingDao;", "Lcom/creation/core_pulgin/core/base/BaseDao;", "Lcom/creation/core_pulgin/payment/in_app_purchase/data/local/entity/InAppPurchaseEntity;", "getInAppPurchaseById", "appId", "", "productId", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getInAppPurchases", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isPremiumUser", "isActive", "isPurchased", "", "(Ljava/lang/String;Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "core-plugin-framework_release"})
@androidx.room.Dao()
public abstract interface IapBillingDao extends com.creation.core_pulgin.core.base.BaseDao<com.creation.core_pulgin.payment.in_app_purchase.data.local.entity.InAppPurchaseEntity> {
    
    @androidx.room.Query(value = "SELECT * FROM InAppPurchaseEntity where appId = :appId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getInAppPurchases(@org.jetbrains.annotations.NotNull()
    java.lang.String appId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.creation.core_pulgin.payment.in_app_purchase.data.local.entity.InAppPurchaseEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM InAppPurchaseEntity where appId = :appId and productId = :productId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getInAppPurchaseById(@org.jetbrains.annotations.NotNull()
    java.lang.String appId, @org.jetbrains.annotations.NotNull()
    java.lang.String productId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.creation.core_pulgin.payment.in_app_purchase.data.local.entity.InAppPurchaseEntity> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM InAppPurchaseEntity where appId = :appId and active = :isActive and isPurchased = :isPurchased")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object isPremiumUser(@org.jetbrains.annotations.NotNull()
    java.lang.String appId, @org.jetbrains.annotations.NotNull()
    java.lang.String isActive, boolean isPurchased, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.creation.core_pulgin.payment.in_app_purchase.data.local.entity.InAppPurchaseEntity>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}