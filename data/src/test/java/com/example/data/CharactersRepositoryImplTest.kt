package com.example.data

import com.example.CharactersRepositoryImpl
import com.example.local.LocalCharactersDataSource
import com.example.remote.RemoteCharactersDataSource
import com.marvel.entities.Character
import com.marvel.entities.Image
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito
import org.mockito.kotlin.doReturn

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CharactersRepositoryImplTest {
    private val expectedResult = listOf(Character(1, "", "", Image("", "")))
    private val mockedLocalDataSource: LocalCharactersDataSource =
        Mockito.mock(LocalCharactersDataSource::class.java)

    private val mockedRemoteDataSource: RemoteCharactersDataSource =
        Mockito.mock(RemoteCharactersDataSource::class.java)

    @Test
    fun shouldReturnLocalCharacters() = runTest {
        doReturn(expectedResult).`when`(mockedLocalDataSource).getCharacters(50, 0)
        val repository = CharactersRepositoryImpl(mockedRemoteDataSource, mockedLocalDataSource)
        assertEquals(expectedResult, repository.getCharacters(50, 0))
    }

    @Test
    fun shouldReturnRemoteCharactersWhenNoLocalCharacters() = runTest {
        val emptyList = listOf<Character>()
        doReturn(emptyList).`when`(mockedLocalDataSource).getCharacters(50, 0)
        doReturn(expectedResult).`when`(mockedRemoteDataSource).getCharacters(50, 0)
        val repository = CharactersRepositoryImpl(mockedRemoteDataSource, mockedLocalDataSource)
        assertEquals(expectedResult, repository.getCharacters(50, 0))
    }
}