package com.creation.core_pulgin.core.di;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u0007"}, d2 = {"Lcom/creation/core_pulgin/core/di/CoreModule;", "", "()V", "provideCoreDatabase", "Lcom/creation/core_pulgin/core/base/CoreDatabase;", "app", "Landroid/app/Application;", "core-plugin-framework_release"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class CoreModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.creation.core_pulgin.core.di.CoreModule INSTANCE = null;
    
    private CoreModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.creation.core_pulgin.core.base.CoreDatabase provideCoreDatabase(@org.jetbrains.annotations.NotNull()
    android.app.Application app) {
        return null;
    }
}