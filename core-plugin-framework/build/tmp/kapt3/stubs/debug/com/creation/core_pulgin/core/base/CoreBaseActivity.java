package com.creation.core_pulgin.core.base;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\b\u0016\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0006H\u0014J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0004H\u0002J\u0012\u0010\u000e\u001a\u00020\t2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0014J\u0006\u0010\u0011\u001a\u00020\tJ\u0018\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J&\u0010\u0017\u001a\u00020\t2\b\u0010\u0018\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010\u0019\u001a\u00020\u00042\b\b\u0002\u0010\u001a\u001a\u00020\u0004H\u0016J\u0010\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u0006H\u0002J\u0018\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u001e\u001a\u00020\fH\u0002\u00a8\u0006\u001f"}, d2 = {"Lcom/creation/core_pulgin/core/base/CoreBaseActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "GetSharedPreference", "", "ctx", "Landroid/content/Context;", "Key", "attachBaseContext", "", "newBase", "createLocaleFromLanguage", "Ljava/util/Locale;", "language", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setFullScreenMode", "setWindowFlag", "bits", "", "on", "", "showPopupMessage", "message", "title", "buttonMessage", "updateContextWithLocale", "context", "updateResourcesWithLocale", "locale", "core-plugin-framework_debug"})
public class CoreBaseActivity extends androidx.appcompat.app.AppCompatActivity {
    
    public CoreBaseActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void attachBaseContext(@org.jetbrains.annotations.NotNull()
    android.content.Context newBase) {
    }
    
    private final android.content.Context updateContextWithLocale(android.content.Context context) {
        return null;
    }
    
    private final java.util.Locale createLocaleFromLanguage(java.lang.String language) {
        return null;
    }
    
    private final android.content.Context updateResourcesWithLocale(android.content.Context context, java.util.Locale locale) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String GetSharedPreference(@org.jetbrains.annotations.NotNull()
    android.content.Context ctx, @org.jetbrains.annotations.Nullable()
    java.lang.String Key) {
        return null;
    }
    
    public final void setFullScreenMode() {
    }
    
    private final void setWindowFlag(int bits, boolean on) {
    }
    
    public void showPopupMessage(@org.jetbrains.annotations.Nullable()
    java.lang.String message, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String buttonMessage) {
    }
}