package com.creation.core_pulgin.core.di;

import android.app.Application;
import com.creation.core_pulgin.core.base.CoreDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class CoreModule_ProvideCoreDatabaseFactory implements Factory<CoreDatabase> {
  private final Provider<Application> appProvider;

  public CoreModule_ProvideCoreDatabaseFactory(Provider<Application> appProvider) {
    this.appProvider = appProvider;
  }

  @Override
  public CoreDatabase get() {
    return provideCoreDatabase(appProvider.get());
  }

  public static CoreModule_ProvideCoreDatabaseFactory create(Provider<Application> appProvider) {
    return new CoreModule_ProvideCoreDatabaseFactory(appProvider);
  }

  public static CoreDatabase provideCoreDatabase(Application app) {
    return Preconditions.checkNotNullFromProvides(CoreModule.INSTANCE.provideCoreDatabase(app));
  }
}
