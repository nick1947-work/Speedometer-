package com.creation.core_pulgin.payment.in_app_purchase.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b>\b\u0087\b\u0018\u00002\u00020\u0001B\u00a9\u0001\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0013\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0015\u00a2\u0006\u0002\u0010\u0016J\u000b\u0010>\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010?\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010@\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010A\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010B\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010C\u001a\u00020\u0011H\u00c6\u0003J\u000b\u0010D\u001a\u0004\u0018\u00010\u0013H\u00c6\u0003J\t\u0010E\u001a\u00020\u0015H\u00c6\u0003J\u000b\u0010F\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010G\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010H\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010I\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010J\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010K\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010L\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010M\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u00c5\u0001\u0010N\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u00112\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0015H\u00c6\u0001J\u0013\u0010O\u001a\u00020\u00152\b\u0010P\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010Q\u001a\u00020\u0011H\u00d6\u0001J\t\u0010R\u001a\u00020\u0003H\u00d6\u0001R \u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u0014\u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u001b\"\u0004\b\u001c\u0010\u001dR \u0010\u000b\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0018\"\u0004\b\u001f\u0010\u001aR \u0010\t\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0018\"\u0004\b!\u0010\u001aR \u0010\f\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0018\"\u0004\b#\u0010\u001aR \u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u0018\"\u0004\b%\u0010\u001aR \u0010\u000e\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u0018\"\u0004\b\'\u0010\u001aR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R \u0010\u0005\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u0018\"\u0004\b-\u0010\u001aR \u0010\u0006\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u0018\"\u0004\b/\u0010\u001aR \u0010\b\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b0\u0010\u0018\"\u0004\b1\u0010\u001aR \u0010\u000f\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b2\u0010\u0018\"\u0004\b3\u0010\u001aR\u001e\u0010\u0010\u001a\u00020\u00118\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b4\u00105\"\u0004\b6\u00107R \u0010\u0007\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b8\u0010\u0018\"\u0004\b9\u0010\u001aR \u0010\r\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b:\u0010\u0018\"\u0004\b;\u0010\u001aR \u0010\n\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b<\u0010\u0018\"\u0004\b=\u0010\u001a\u00a8\u0006S"}, d2 = {"Lcom/creation/core_pulgin/payment/in_app_purchase/data/model/ProductListingData;", "", "active", "", "packName", "productIdPrice", "productIdPurchase", "subType", "save", "offerName", "uiFlag", "offerDays", "offerStartTime", "trialText", "price", "showPrice", "sortSequence", "", "productDetails", "Lcom/android/billingclient/api/ProductDetails;", "isSelected", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILcom/android/billingclient/api/ProductDetails;Z)V", "getActive", "()Ljava/lang/String;", "setActive", "(Ljava/lang/String;)V", "()Z", "setSelected", "(Z)V", "getOfferDays", "setOfferDays", "getOfferName", "setOfferName", "getOfferStartTime", "setOfferStartTime", "getPackName", "setPackName", "getPrice", "setPrice", "getProductDetails", "()Lcom/android/billingclient/api/ProductDetails;", "setProductDetails", "(Lcom/android/billingclient/api/ProductDetails;)V", "getProductIdPrice", "setProductIdPrice", "getProductIdPurchase", "setProductIdPurchase", "getSave", "setSave", "getShowPrice", "setShowPrice", "getSortSequence", "()I", "setSortSequence", "(I)V", "getSubType", "setSubType", "getTrialText", "setTrialText", "getUiFlag", "setUiFlag", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "core-plugin-framework_debug"})
@androidx.annotation.Keep()
public final class ProductListingData {
    @com.google.gson.annotations.SerializedName(value = "Active")
    @com.google.gson.annotations.Expose()
    @org.jetbrains.annotations.Nullable()
    private java.lang.String active;
    @com.google.gson.annotations.SerializedName(value = "Pack Name")
    @com.google.gson.annotations.Expose()
    @org.jetbrains.annotations.Nullable()
    private java.lang.String packName;
    @com.google.gson.annotations.SerializedName(value = "ProductID_Price")
    @com.google.gson.annotations.Expose()
    @org.jetbrains.annotations.Nullable()
    private java.lang.String productIdPrice;
    @com.google.gson.annotations.SerializedName(value = "ProductID_Purchase")
    @com.google.gson.annotations.Expose()
    @org.jetbrains.annotations.Nullable()
    private java.lang.String productIdPurchase;
    @com.google.gson.annotations.SerializedName(value = "SUB_TYPE")
    @com.google.gson.annotations.Expose()
    @org.jetbrains.annotations.Nullable()
    private java.lang.String subType;
    @com.google.gson.annotations.SerializedName(value = "Offer")
    @com.google.gson.annotations.Expose()
    @org.jetbrains.annotations.Nullable()
    private java.lang.String save;
    @com.google.gson.annotations.SerializedName(value = "Offer_Name")
    @com.google.gson.annotations.Expose()
    @org.jetbrains.annotations.Nullable()
    private java.lang.String offerName;
    @com.google.gson.annotations.SerializedName(value = "Pack_type")
    @com.google.gson.annotations.Expose()
    @org.jetbrains.annotations.Nullable()
    private java.lang.String uiFlag;
    @com.google.gson.annotations.SerializedName(value = "offer_days")
    @com.google.gson.annotations.Expose()
    @org.jetbrains.annotations.Nullable()
    private java.lang.String offerDays;
    @com.google.gson.annotations.SerializedName(value = "offer_start_time")
    @com.google.gson.annotations.Expose()
    @org.jetbrains.annotations.Nullable()
    private java.lang.String offerStartTime;
    @com.google.gson.annotations.SerializedName(value = "trial")
    @com.google.gson.annotations.Expose()
    @org.jetbrains.annotations.Nullable()
    private java.lang.String trialText;
    @com.google.gson.annotations.SerializedName(value = "price")
    @com.google.gson.annotations.Expose()
    @org.jetbrains.annotations.Nullable()
    private java.lang.String price;
    @com.google.gson.annotations.SerializedName(value = "showPrice")
    @com.google.gson.annotations.Expose()
    @org.jetbrains.annotations.Nullable()
    private java.lang.String showPrice;
    @com.google.gson.annotations.SerializedName(value = "sort_order")
    @com.google.gson.annotations.Expose()
    private int sortSequence;
    @org.jetbrains.annotations.Nullable()
    private com.android.billingclient.api.ProductDetails productDetails;
    private boolean isSelected;
    
    public ProductListingData(@org.jetbrains.annotations.Nullable()
    java.lang.String active, @org.jetbrains.annotations.Nullable()
    java.lang.String packName, @org.jetbrains.annotations.Nullable()
    java.lang.String productIdPrice, @org.jetbrains.annotations.Nullable()
    java.lang.String productIdPurchase, @org.jetbrains.annotations.Nullable()
    java.lang.String subType, @org.jetbrains.annotations.Nullable()
    java.lang.String save, @org.jetbrains.annotations.Nullable()
    java.lang.String offerName, @org.jetbrains.annotations.Nullable()
    java.lang.String uiFlag, @org.jetbrains.annotations.Nullable()
    java.lang.String offerDays, @org.jetbrains.annotations.Nullable()
    java.lang.String offerStartTime, @org.jetbrains.annotations.Nullable()
    java.lang.String trialText, @org.jetbrains.annotations.Nullable()
    java.lang.String price, @org.jetbrains.annotations.Nullable()
    java.lang.String showPrice, int sortSequence, @org.jetbrains.annotations.Nullable()
    com.android.billingclient.api.ProductDetails productDetails, boolean isSelected) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getActive() {
        return null;
    }
    
    public final void setActive(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPackName() {
        return null;
    }
    
    public final void setPackName(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getProductIdPrice() {
        return null;
    }
    
    public final void setProductIdPrice(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getProductIdPurchase() {
        return null;
    }
    
    public final void setProductIdPurchase(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSubType() {
        return null;
    }
    
    public final void setSubType(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSave() {
        return null;
    }
    
    public final void setSave(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getOfferName() {
        return null;
    }
    
    public final void setOfferName(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getUiFlag() {
        return null;
    }
    
    public final void setUiFlag(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getOfferDays() {
        return null;
    }
    
    public final void setOfferDays(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getOfferStartTime() {
        return null;
    }
    
    public final void setOfferStartTime(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getTrialText() {
        return null;
    }
    
    public final void setTrialText(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPrice() {
        return null;
    }
    
    public final void setPrice(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getShowPrice() {
        return null;
    }
    
    public final void setShowPrice(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    public final int getSortSequence() {
        return 0;
    }
    
    public final void setSortSequence(int p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.android.billingclient.api.ProductDetails getProductDetails() {
        return null;
    }
    
    public final void setProductDetails(@org.jetbrains.annotations.Nullable()
    com.android.billingclient.api.ProductDetails p0) {
    }
    
    public final boolean isSelected() {
        return false;
    }
    
    public final void setSelected(boolean p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component13() {
        return null;
    }
    
    public final int component14() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.android.billingclient.api.ProductDetails component15() {
        return null;
    }
    
    public final boolean component16() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.creation.core_pulgin.payment.in_app_purchase.data.model.ProductListingData copy(@org.jetbrains.annotations.Nullable()
    java.lang.String active, @org.jetbrains.annotations.Nullable()
    java.lang.String packName, @org.jetbrains.annotations.Nullable()
    java.lang.String productIdPrice, @org.jetbrains.annotations.Nullable()
    java.lang.String productIdPurchase, @org.jetbrains.annotations.Nullable()
    java.lang.String subType, @org.jetbrains.annotations.Nullable()
    java.lang.String save, @org.jetbrains.annotations.Nullable()
    java.lang.String offerName, @org.jetbrains.annotations.Nullable()
    java.lang.String uiFlag, @org.jetbrains.annotations.Nullable()
    java.lang.String offerDays, @org.jetbrains.annotations.Nullable()
    java.lang.String offerStartTime, @org.jetbrains.annotations.Nullable()
    java.lang.String trialText, @org.jetbrains.annotations.Nullable()
    java.lang.String price, @org.jetbrains.annotations.Nullable()
    java.lang.String showPrice, int sortSequence, @org.jetbrains.annotations.Nullable()
    com.android.billingclient.api.ProductDetails productDetails, boolean isSelected) {
        return null;
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
}