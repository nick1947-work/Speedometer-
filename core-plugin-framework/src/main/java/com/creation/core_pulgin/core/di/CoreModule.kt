package com.creation.core_pulgin.core.di

import android.app.Application
import androidx.room.Room
import com.creation.core_pulgin.core.base.CoreDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    fun provideCoreDatabase(app: Application): CoreDatabase {
        return Room.databaseBuilder(
            app, CoreDatabase::class.java, CoreDatabase.DB_NAME
        )//.addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

}