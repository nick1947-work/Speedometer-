package com.creation.core_pulgin.payment.in_app_purchase.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.creation.core_pulgin.payment.in_app_purchase.data.local.entity.InAppPurchaseEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class IapBillingDao_Impl implements IapBillingDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<InAppPurchaseEntity> __insertionAdapterOfInAppPurchaseEntity;

  private final EntityDeletionOrUpdateAdapter<InAppPurchaseEntity> __deletionAdapterOfInAppPurchaseEntity;

  private final EntityDeletionOrUpdateAdapter<InAppPurchaseEntity> __updateAdapterOfInAppPurchaseEntity;

  public IapBillingDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfInAppPurchaseEntity = new EntityInsertionAdapter<InAppPurchaseEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `InAppPurchaseEntity` (`appId`,`productId`,`productType`,`productName`,`active`,`isPurchased`,`sortSequence`,`startDate`,`endDate`,`couponCode`,`couponProductId`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final InAppPurchaseEntity entity) {
        if (entity.getAppId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getAppId());
        }
        if (entity.getProductId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getProductId());
        }
        if (entity.getProductType() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getProductType());
        }
        if (entity.getProductName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getProductName());
        }
        if (entity.getActive() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getActive());
        }
        final int _tmp = entity.isPurchased() ? 1 : 0;
        statement.bindLong(6, _tmp);
        statement.bindLong(7, entity.getSortSequence());
        statement.bindLong(8, entity.getStartDate());
        statement.bindLong(9, entity.getEndDate());
        if (entity.getCouponCode() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getCouponCode());
        }
        if (entity.getCouponProductId() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getCouponProductId());
        }
      }
    };
    this.__deletionAdapterOfInAppPurchaseEntity = new EntityDeletionOrUpdateAdapter<InAppPurchaseEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `InAppPurchaseEntity` WHERE `appId` = ? AND `productId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final InAppPurchaseEntity entity) {
        if (entity.getAppId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getAppId());
        }
        if (entity.getProductId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getProductId());
        }
      }
    };
    this.__updateAdapterOfInAppPurchaseEntity = new EntityDeletionOrUpdateAdapter<InAppPurchaseEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `InAppPurchaseEntity` SET `appId` = ?,`productId` = ?,`productType` = ?,`productName` = ?,`active` = ?,`isPurchased` = ?,`sortSequence` = ?,`startDate` = ?,`endDate` = ?,`couponCode` = ?,`couponProductId` = ? WHERE `appId` = ? AND `productId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final InAppPurchaseEntity entity) {
        if (entity.getAppId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getAppId());
        }
        if (entity.getProductId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getProductId());
        }
        if (entity.getProductType() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getProductType());
        }
        if (entity.getProductName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getProductName());
        }
        if (entity.getActive() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getActive());
        }
        final int _tmp = entity.isPurchased() ? 1 : 0;
        statement.bindLong(6, _tmp);
        statement.bindLong(7, entity.getSortSequence());
        statement.bindLong(8, entity.getStartDate());
        statement.bindLong(9, entity.getEndDate());
        if (entity.getCouponCode() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getCouponCode());
        }
        if (entity.getCouponProductId() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getCouponProductId());
        }
        if (entity.getAppId() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getAppId());
        }
        if (entity.getProductId() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getProductId());
        }
      }
    };
  }

  @Override
  public Object insert(final InAppPurchaseEntity obj,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfInAppPurchaseEntity.insert(obj);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insert(final InAppPurchaseEntity[] obj,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfInAppPurchaseEntity.insert(obj);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final InAppPurchaseEntity obj,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfInAppPurchaseEntity.handle(obj);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final InAppPurchaseEntity obj,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfInAppPurchaseEntity.handle(obj);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getInAppPurchases(final String appId,
      final Continuation<? super List<InAppPurchaseEntity>> $completion) {
    final String _sql = "SELECT * FROM InAppPurchaseEntity where appId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (appId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, appId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<InAppPurchaseEntity>>() {
      @Override
      @NonNull
      public List<InAppPurchaseEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAppId = CursorUtil.getColumnIndexOrThrow(_cursor, "appId");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfProductType = CursorUtil.getColumnIndexOrThrow(_cursor, "productType");
          final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "productName");
          final int _cursorIndexOfActive = CursorUtil.getColumnIndexOrThrow(_cursor, "active");
          final int _cursorIndexOfIsPurchased = CursorUtil.getColumnIndexOrThrow(_cursor, "isPurchased");
          final int _cursorIndexOfSortSequence = CursorUtil.getColumnIndexOrThrow(_cursor, "sortSequence");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfCouponCode = CursorUtil.getColumnIndexOrThrow(_cursor, "couponCode");
          final int _cursorIndexOfCouponProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "couponProductId");
          final List<InAppPurchaseEntity> _result = new ArrayList<InAppPurchaseEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final InAppPurchaseEntity _item;
            final String _tmpAppId;
            if (_cursor.isNull(_cursorIndexOfAppId)) {
              _tmpAppId = null;
            } else {
              _tmpAppId = _cursor.getString(_cursorIndexOfAppId);
            }
            final String _tmpProductId;
            if (_cursor.isNull(_cursorIndexOfProductId)) {
              _tmpProductId = null;
            } else {
              _tmpProductId = _cursor.getString(_cursorIndexOfProductId);
            }
            final String _tmpProductType;
            if (_cursor.isNull(_cursorIndexOfProductType)) {
              _tmpProductType = null;
            } else {
              _tmpProductType = _cursor.getString(_cursorIndexOfProductType);
            }
            final String _tmpProductName;
            if (_cursor.isNull(_cursorIndexOfProductName)) {
              _tmpProductName = null;
            } else {
              _tmpProductName = _cursor.getString(_cursorIndexOfProductName);
            }
            final String _tmpActive;
            if (_cursor.isNull(_cursorIndexOfActive)) {
              _tmpActive = null;
            } else {
              _tmpActive = _cursor.getString(_cursorIndexOfActive);
            }
            final boolean _tmpIsPurchased;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPurchased);
            _tmpIsPurchased = _tmp != 0;
            final int _tmpSortSequence;
            _tmpSortSequence = _cursor.getInt(_cursorIndexOfSortSequence);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final long _tmpEndDate;
            _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            final String _tmpCouponCode;
            if (_cursor.isNull(_cursorIndexOfCouponCode)) {
              _tmpCouponCode = null;
            } else {
              _tmpCouponCode = _cursor.getString(_cursorIndexOfCouponCode);
            }
            final String _tmpCouponProductId;
            if (_cursor.isNull(_cursorIndexOfCouponProductId)) {
              _tmpCouponProductId = null;
            } else {
              _tmpCouponProductId = _cursor.getString(_cursorIndexOfCouponProductId);
            }
            _item = new InAppPurchaseEntity(_tmpAppId,_tmpProductId,_tmpProductType,_tmpProductName,_tmpActive,_tmpIsPurchased,_tmpSortSequence,_tmpStartDate,_tmpEndDate,_tmpCouponCode,_tmpCouponProductId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getInAppPurchaseById(final String appId, final String productId,
      final Continuation<? super InAppPurchaseEntity> $completion) {
    final String _sql = "SELECT * FROM InAppPurchaseEntity where appId = ? and productId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (appId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, appId);
    }
    _argIndex = 2;
    if (productId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, productId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<InAppPurchaseEntity>() {
      @Override
      @NonNull
      public InAppPurchaseEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAppId = CursorUtil.getColumnIndexOrThrow(_cursor, "appId");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfProductType = CursorUtil.getColumnIndexOrThrow(_cursor, "productType");
          final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "productName");
          final int _cursorIndexOfActive = CursorUtil.getColumnIndexOrThrow(_cursor, "active");
          final int _cursorIndexOfIsPurchased = CursorUtil.getColumnIndexOrThrow(_cursor, "isPurchased");
          final int _cursorIndexOfSortSequence = CursorUtil.getColumnIndexOrThrow(_cursor, "sortSequence");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfCouponCode = CursorUtil.getColumnIndexOrThrow(_cursor, "couponCode");
          final int _cursorIndexOfCouponProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "couponProductId");
          final InAppPurchaseEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpAppId;
            if (_cursor.isNull(_cursorIndexOfAppId)) {
              _tmpAppId = null;
            } else {
              _tmpAppId = _cursor.getString(_cursorIndexOfAppId);
            }
            final String _tmpProductId;
            if (_cursor.isNull(_cursorIndexOfProductId)) {
              _tmpProductId = null;
            } else {
              _tmpProductId = _cursor.getString(_cursorIndexOfProductId);
            }
            final String _tmpProductType;
            if (_cursor.isNull(_cursorIndexOfProductType)) {
              _tmpProductType = null;
            } else {
              _tmpProductType = _cursor.getString(_cursorIndexOfProductType);
            }
            final String _tmpProductName;
            if (_cursor.isNull(_cursorIndexOfProductName)) {
              _tmpProductName = null;
            } else {
              _tmpProductName = _cursor.getString(_cursorIndexOfProductName);
            }
            final String _tmpActive;
            if (_cursor.isNull(_cursorIndexOfActive)) {
              _tmpActive = null;
            } else {
              _tmpActive = _cursor.getString(_cursorIndexOfActive);
            }
            final boolean _tmpIsPurchased;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPurchased);
            _tmpIsPurchased = _tmp != 0;
            final int _tmpSortSequence;
            _tmpSortSequence = _cursor.getInt(_cursorIndexOfSortSequence);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final long _tmpEndDate;
            _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            final String _tmpCouponCode;
            if (_cursor.isNull(_cursorIndexOfCouponCode)) {
              _tmpCouponCode = null;
            } else {
              _tmpCouponCode = _cursor.getString(_cursorIndexOfCouponCode);
            }
            final String _tmpCouponProductId;
            if (_cursor.isNull(_cursorIndexOfCouponProductId)) {
              _tmpCouponProductId = null;
            } else {
              _tmpCouponProductId = _cursor.getString(_cursorIndexOfCouponProductId);
            }
            _result = new InAppPurchaseEntity(_tmpAppId,_tmpProductId,_tmpProductType,_tmpProductName,_tmpActive,_tmpIsPurchased,_tmpSortSequence,_tmpStartDate,_tmpEndDate,_tmpCouponCode,_tmpCouponProductId);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object isPremiumUser(final String appId, final String isActive, final boolean isPurchased,
      final Continuation<? super List<InAppPurchaseEntity>> $completion) {
    final String _sql = "SELECT * FROM InAppPurchaseEntity where appId = ? and active = ? and isPurchased = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    if (appId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, appId);
    }
    _argIndex = 2;
    if (isActive == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, isActive);
    }
    _argIndex = 3;
    final int _tmp = isPurchased ? 1 : 0;
    _statement.bindLong(_argIndex, _tmp);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<InAppPurchaseEntity>>() {
      @Override
      @NonNull
      public List<InAppPurchaseEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAppId = CursorUtil.getColumnIndexOrThrow(_cursor, "appId");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfProductType = CursorUtil.getColumnIndexOrThrow(_cursor, "productType");
          final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "productName");
          final int _cursorIndexOfActive = CursorUtil.getColumnIndexOrThrow(_cursor, "active");
          final int _cursorIndexOfIsPurchased = CursorUtil.getColumnIndexOrThrow(_cursor, "isPurchased");
          final int _cursorIndexOfSortSequence = CursorUtil.getColumnIndexOrThrow(_cursor, "sortSequence");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfCouponCode = CursorUtil.getColumnIndexOrThrow(_cursor, "couponCode");
          final int _cursorIndexOfCouponProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "couponProductId");
          final List<InAppPurchaseEntity> _result = new ArrayList<InAppPurchaseEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final InAppPurchaseEntity _item;
            final String _tmpAppId;
            if (_cursor.isNull(_cursorIndexOfAppId)) {
              _tmpAppId = null;
            } else {
              _tmpAppId = _cursor.getString(_cursorIndexOfAppId);
            }
            final String _tmpProductId;
            if (_cursor.isNull(_cursorIndexOfProductId)) {
              _tmpProductId = null;
            } else {
              _tmpProductId = _cursor.getString(_cursorIndexOfProductId);
            }
            final String _tmpProductType;
            if (_cursor.isNull(_cursorIndexOfProductType)) {
              _tmpProductType = null;
            } else {
              _tmpProductType = _cursor.getString(_cursorIndexOfProductType);
            }
            final String _tmpProductName;
            if (_cursor.isNull(_cursorIndexOfProductName)) {
              _tmpProductName = null;
            } else {
              _tmpProductName = _cursor.getString(_cursorIndexOfProductName);
            }
            final String _tmpActive;
            if (_cursor.isNull(_cursorIndexOfActive)) {
              _tmpActive = null;
            } else {
              _tmpActive = _cursor.getString(_cursorIndexOfActive);
            }
            final boolean _tmpIsPurchased;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsPurchased);
            _tmpIsPurchased = _tmp_1 != 0;
            final int _tmpSortSequence;
            _tmpSortSequence = _cursor.getInt(_cursorIndexOfSortSequence);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final long _tmpEndDate;
            _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            final String _tmpCouponCode;
            if (_cursor.isNull(_cursorIndexOfCouponCode)) {
              _tmpCouponCode = null;
            } else {
              _tmpCouponCode = _cursor.getString(_cursorIndexOfCouponCode);
            }
            final String _tmpCouponProductId;
            if (_cursor.isNull(_cursorIndexOfCouponProductId)) {
              _tmpCouponProductId = null;
            } else {
              _tmpCouponProductId = _cursor.getString(_cursorIndexOfCouponProductId);
            }
            _item = new InAppPurchaseEntity(_tmpAppId,_tmpProductId,_tmpProductType,_tmpProductName,_tmpActive,_tmpIsPurchased,_tmpSortSequence,_tmpStartDate,_tmpEndDate,_tmpCouponCode,_tmpCouponProductId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
