package com.swalisoft.payer.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

@Database(
  entities = [],
  version = 1,
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class BarikDatabase : RoomDatabase() {
  companion object {
    const val DB_NAME = "local_db.db"
  }
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<BarikDatabase> {
  override fun initialize(): BarikDatabase
}
