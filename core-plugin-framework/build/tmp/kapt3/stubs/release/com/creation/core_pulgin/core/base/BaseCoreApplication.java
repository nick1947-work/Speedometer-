package com.creation.core_pulgin.core.base;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016\u00a8\u0006\u0006"}, d2 = {"Lcom/creation/core_pulgin/core/base/BaseCoreApplication;", "Landroid/app/Application;", "()V", "onCreate", "", "Companion", "core-plugin-framework_release"})
public class BaseCoreApplication extends android.app.Application {
    @org.jetbrains.annotations.Nullable()
    private static android.app.Application _instance;
    @org.jetbrains.annotations.NotNull()
    public static final com.creation.core_pulgin.core.base.BaseCoreApplication.Companion Companion = null;
    
    public BaseCoreApplication() {
        super();
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0004J\u000e\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0004R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/creation/core_pulgin/core/base/BaseCoreApplication$Companion;", "", "()V", "_instance", "Landroid/app/Application;", "getInstance", "initFirebase", "", "context", "initialize", "application", "core-plugin-framework_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final android.app.Application getInstance() {
            return null;
        }
        
        public final void initialize(@org.jetbrains.annotations.NotNull()
        android.app.Application application) {
        }
        
        public final void initFirebase(@org.jetbrains.annotations.NotNull()
        android.app.Application context) {
        }
    }
}