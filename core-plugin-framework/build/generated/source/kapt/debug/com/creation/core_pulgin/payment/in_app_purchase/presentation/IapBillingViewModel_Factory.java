package com.creation.core_pulgin.payment.in_app_purchase.presentation;

import android.app.Application;
import com.creation.core_pulgin.core.base.CoreDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
    "KotlinInternalInJava",
    "cast"
})
public final class IapBillingViewModel_Factory implements Factory<IapBillingViewModel> {
  private final Provider<Application> applicationProvider;

  private final Provider<CoreDatabase> coreDatabaseProvider;

  public IapBillingViewModel_Factory(Provider<Application> applicationProvider,
      Provider<CoreDatabase> coreDatabaseProvider) {
    this.applicationProvider = applicationProvider;
    this.coreDatabaseProvider = coreDatabaseProvider;
  }

  @Override
  public IapBillingViewModel get() {
    return newInstance(applicationProvider.get(), coreDatabaseProvider.get());
  }

  public static IapBillingViewModel_Factory create(Provider<Application> applicationProvider,
      Provider<CoreDatabase> coreDatabaseProvider) {
    return new IapBillingViewModel_Factory(applicationProvider, coreDatabaseProvider);
  }

  public static IapBillingViewModel newInstance(Application application,
      CoreDatabase coreDatabase) {
    return new IapBillingViewModel(application, coreDatabase);
  }
}
