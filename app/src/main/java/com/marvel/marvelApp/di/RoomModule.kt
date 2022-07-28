package com.marvel.marvelApp.di

import android.app.Application
import androidx.room.Room
import com.marvel.local.room.CharacterDao
import com.marvel.local.room.CharacterDb
import dagger.Module
import dagger.Provides

import javax.inject.Singleton


@Module
class RoomModule {
    // App will be injected here by Dagger
    // Dagger knows that App instance will fit here based on the @Binds in the AppModule
    @Singleton
    @Provides
    fun provideAppDatabase(app: Application): CharacterDb {
        return Room
            .databaseBuilder(app.applicationContext, CharacterDb::class.java, DB_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun provideCharactersDao(db: CharacterDb): CharacterDao {
        return db.characterDao()
    }

    companion object {
        private const val DB_NAME = "marvel_db"
    }
}