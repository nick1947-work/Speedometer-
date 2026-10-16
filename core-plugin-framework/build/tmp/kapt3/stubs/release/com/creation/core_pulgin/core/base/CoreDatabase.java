package com.creation.core_pulgin.core.base;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\'\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0005\u00a2\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\b"}, d2 = {"Lcom/creation/core_pulgin/core/base/CoreDatabase;", "Landroidx/room/RoomDatabase;", "()V", "inAppPurchaseDao", "Lcom/creation/core_pulgin/payment/in_app_purchase/data/local/IapBillingDao;", "getInAppPurchaseDao", "()Lcom/creation/core_pulgin/payment/in_app_purchase/data/local/IapBillingDao;", "Companion", "core-plugin-framework_release"})
@androidx.room.Database(entities = {com.creation.core_pulgin.payment.in_app_purchase.data.local.entity.InAppPurchaseEntity.class}, version = 1)
public abstract class CoreDatabase extends androidx.room.RoomDatabase {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DB_NAME = "core_db";
    @org.jetbrains.annotations.NotNull()
    public static final com.creation.core_pulgin.core.base.CoreDatabase.Companion Companion = null;
    
    public CoreDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.creation.core_pulgin.payment.in_app_purchase.data.local.IapBillingDao getInAppPurchaseDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/creation/core_pulgin/core/base/CoreDatabase$Companion;", "", "()V", "DB_NAME", "", "core-plugin-framework_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}