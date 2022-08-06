package com.marvel.marvelApp.di.data.libs

import android.app.Application
import androidx.room.Room
import com.marvel.local.room.CharacterDao
import com.marvel.local.room.MarvelDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class RoomModule {
    @Provides
    fun provideAppDatabase(app: Application): MarvelDb {
        return Room
            .databaseBuilder(app.applicationContext, MarvelDb::class.java, DB_NAME)
            .build()
    }

    @Provides
    fun provideCharactersDao(db: MarvelDb): CharacterDao {
        return db.characterDao()
    }

    companion object {
        private const val DB_NAME = "marvel_db"
    }
}