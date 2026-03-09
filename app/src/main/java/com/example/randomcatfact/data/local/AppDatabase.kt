package com.example.randomcatfact.data.local
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteFact::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteFactDao(): FavoriteFactDao
}