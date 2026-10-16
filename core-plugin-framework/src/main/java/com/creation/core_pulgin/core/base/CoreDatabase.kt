package com.creation.core_pulgin.core.base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.creation.core_pulgin.payment.in_app_purchase.data.local.IapBillingDao
import com.creation.core_pulgin.payment.in_app_purchase.data.local.entity.InAppPurchaseEntity

@Database(
    entities = [InAppPurchaseEntity::class],
    version = 1
)
//@TypeConverters(Converters::class)
abstract class CoreDatabase: RoomDatabase() {
    companion object {
        const val DB_NAME = "core_db"
    }

    abstract val inAppPurchaseDao: IapBillingDao

}