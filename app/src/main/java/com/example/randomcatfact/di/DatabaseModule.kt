package com.example.randomcatfact.di

import android.app.Application
import androidx.room.Room
import com.example.randomcatfact.data.local.AppDatabase
import com.example.randomcatfact.data.local.FavoriteFactDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "cat_db"
        ).build()
    }

    @Provides
    fun provideFavoriteFactDao(db: AppDatabase): FavoriteFactDao {
        return db.favoriteFactDao()
    }
}