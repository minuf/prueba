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

    private val initialSize = 50
    private val initialSkip = 0

    private val expectedResult = listOf(Character(1, "", "", Image("", "")))
    private val mockedLocalDataSource: LocalCharactersDataSource =
        Mockito.mock(LocalCharactersDataSource::class.java)

    private val mockedRemoteDataSource: RemoteCharactersDataSource =
        Mockito.mock(RemoteCharactersDataSource::class.java)

    @Test
    fun `Should return local characters if has saved characters in local (db)`() = runTest {
        doReturn(expectedResult).`when`(mockedLocalDataSource).getCharacters(initialSize, initialSkip)
        val repository = CharactersRepositoryImpl(mockedRemoteDataSource, mockedLocalDataSource)
        assertEquals(expectedResult, repository.getCharacters(initialSize, initialSkip))
    }

    @Test
    fun `Should return remote characters when no saved characters in local (db)`() = runTest {
        val emptyList = listOf<Character>()
        doReturn(emptyList).`when`(mockedLocalDataSource).getCharacters(initialSize, initialSkip)
        doReturn(expectedResult).`when`(mockedRemoteDataSource).getCharacters(initialSize, initialSkip)

        val repository = CharactersRepositoryImpl(mockedRemoteDataSource, mockedLocalDataSource)
        assertEquals(expectedResult, repository.getCharacters(initialSize, initialSkip))
    }

    @Test
    fun `Should save characters in local (db) when fetch remote characters`() = runTest {
        val localDataSource = LocalCharactersDataSource()
        doReturn(expectedResult).`when`(mockedRemoteDataSource).getCharacters(initialSize, initialSkip)

        val repository = CharactersRepositoryImpl(mockedRemoteDataSource, localDataSource)
        repository.getCharacters(initialSize, initialSkip)

        assertEquals(expectedResult, localDataSource.getCharacters(initialSize, initialSkip))
    }
}