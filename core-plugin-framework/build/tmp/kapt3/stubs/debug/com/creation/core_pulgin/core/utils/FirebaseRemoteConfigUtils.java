package com.creation.core_pulgin.core.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\fJ\u001a\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/creation/core_pulgin/core/utils/FirebaseRemoteConfigUtils;", "", "()V", "FREE_TRIAL_PACK_STATUS", "", "PREMIUM_PACK_DATA_KEY", "TAG", "getInAppProducts", "", "Lcom/creation/core_pulgin/payment/in_app_purchase/data/model/ProductListingData;", "defaultLocalPackJson", "context", "Landroid/content/Context;", "init", "", "jsonParseAppDataList", "dataJsonArray", "Lorg/json/JSONArray;", "core-plugin-framework_debug"})
public final class FirebaseRemoteConfigUtils {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "RemoteConfigUtils";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREMIUM_PACK_DATA_KEY = "premium_pack_data";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String FREE_TRIAL_PACK_STATUS = "is_free_trial_enabled";
    @org.jetbrains.annotations.NotNull()
    public static final com.creation.core_pulgin.core.utils.FirebaseRemoteConfigUtils INSTANCE = null;
    
    private FirebaseRemoteConfigUtils() {
        super();
    }
    
    public final void init(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.creation.core_pulgin.payment.in_app_purchase.data.model.ProductListingData> getInAppProducts(@org.jetbrains.annotations.NotNull()
    java.lang.String defaultLocalPackJson, @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    private final java.util.List<com.creation.core_pulgin.payment.in_app_purchase.data.model.ProductListingData> jsonParseAppDataList(org.json.JSONArray dataJsonArray) {
        return null;
    }
}