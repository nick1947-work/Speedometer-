package com.creation.core_pulgin.core.base;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.creation.core_pulgin.payment.in_app_purchase.data.local.IapBillingDao;
import com.creation.core_pulgin.payment.in_app_purchase.data.local.IapBillingDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CoreDatabase_Impl extends CoreDatabase {
  private volatile IapBillingDao _iapBillingDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `InAppPurchaseEntity` (`appId` TEXT NOT NULL, `productId` TEXT NOT NULL, `productType` TEXT NOT NULL, `productName` TEXT NOT NULL, `active` TEXT NOT NULL, `isPurchased` INTEGER NOT NULL, `sortSequence` INTEGER NOT NULL, `startDate` INTEGER NOT NULL, `endDate` INTEGER NOT NULL, `couponCode` TEXT NOT NULL, `couponProductId` TEXT NOT NULL, PRIMARY KEY(`appId`, `productId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '66d396ff1284c4c4e8b31fc3e0a5edc2')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `InAppPurchaseEntity`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsInAppPurchaseEntity = new HashMap<String, TableInfo.Column>(11);
        _columnsInAppPurchaseEntity.put("appId", new TableInfo.Column("appId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInAppPurchaseEntity.put("productId", new TableInfo.Column("productId", "TEXT", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInAppPurchaseEntity.put("productType", new TableInfo.Column("productType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInAppPurchaseEntity.put("productName", new TableInfo.Column("productName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInAppPurchaseEntity.put("active", new TableInfo.Column("active", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInAppPurchaseEntity.put("isPurchased", new TableInfo.Column("isPurchased", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInAppPurchaseEntity.put("sortSequence", new TableInfo.Column("sortSequence", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInAppPurchaseEntity.put("startDate", new TableInfo.Column("startDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInAppPurchaseEntity.put("endDate", new TableInfo.Column("endDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInAppPurchaseEntity.put("couponCode", new TableInfo.Column("couponCode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInAppPurchaseEntity.put("couponProductId", new TableInfo.Column("couponProductId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysInAppPurchaseEntity = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesInAppPurchaseEntity = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoInAppPurchaseEntity = new TableInfo("InAppPurchaseEntity", _columnsInAppPurchaseEntity, _foreignKeysInAppPurchaseEntity, _indicesInAppPurchaseEntity);
        final TableInfo _existingInAppPurchaseEntity = TableInfo.read(db, "InAppPurchaseEntity");
        if (!_infoInAppPurchaseEntity.equals(_existingInAppPurchaseEntity)) {
          return new RoomOpenHelper.ValidationResult(false, "InAppPurchaseEntity(com.creation.core_pulgin.payment.in_app_purchase.data.local.entity.InAppPurchaseEntity).\n"
                  + " Expected:\n" + _infoInAppPurchaseEntity + "\n"
                  + " Found:\n" + _existingInAppPurchaseEntity);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "66d396ff1284c4c4e8b31fc3e0a5edc2", "c1ed70749a78e18724d28944a75e8d30");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "InAppPurchaseEntity");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `InAppPurchaseEntity`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(IapBillingDao.class, IapBillingDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public IapBillingDao getInAppPurchaseDao() {
    if (_iapBillingDao != null) {
      return _iapBillingDao;
    } else {
      synchronized(this) {
        if(_iapBillingDao == null) {
          _iapBillingDao = new IapBillingDao_Impl(this);
        }
        return _iapBillingDao;
      }
    }
  }
}
