package com.example.randomcatfact.data.local
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_facts")
data class FavoriteFact(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fact: String
)
