package com.creation.core_pulgin.core.base;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0004"}, d2 = {"Lcom/creation/core_pulgin/core/base/NetworkUtils;", "", "()V", "Companion", "core-plugin-framework_release"})
public final class NetworkUtils {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String NO_NETWORK_MESSAGE = "No Internet Access! Please check your network settings and try again.";
    @org.jetbrains.annotations.NotNull()
    public static final com.creation.core_pulgin.core.base.NetworkUtils.Companion Companion = null;
    
    public NetworkUtils() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/creation/core_pulgin/core/base/NetworkUtils$Companion;", "", "()V", "NO_NETWORK_MESSAGE", "", "isDeviceOnline", "", "context", "Landroid/content/Context;", "core-plugin-framework_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final boolean isDeviceOnline(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return false;
        }
    }
}