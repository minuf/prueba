package com.example.data

import com.example.CharactersRepositoryImpl
import com.example.local.LocalCharactersDataSource
import com.example.remote.RemoteCharactersDataSource
import com.marvel.model.Character
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CharactersRepositoryImplTest {

    private val initialSize = 50
    private val initialSkip = 0

    private val expectedResult = listOf(Character(1, "", "", ""))
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
        doReturn(listOf<Character>()).`when`(mockedLocalDataSource).getCharacters(initialSize, initialSkip)
        doReturn(expectedResult).`when`(mockedRemoteDataSource).getCharacters(initialSize, initialSkip)

        val repository = CharactersRepositoryImpl(mockedRemoteDataSource, mockedLocalDataSource)
        repository.getCharacters(initialSize, initialSkip)

        verify(mockedRemoteDataSource, times(1)).getCharacters(initialSize, initialSkip)
        verify(mockedLocalDataSource, times(1)).saveCharacters(expectedResult)
    }

    @Test
    fun `Should fetch remote characters when paginated list (size, skip) not in local (db)`() = runTest {
        val skip = initialSkip + initialSize
        val remoteResult = listOf(Character(2, "", "", ""))
        doReturn(listOf<Character>()).`when`(mockedLocalDataSource).getCharacters(initialSize, skip)
        doReturn(remoteResult).`when`(mockedRemoteDataSource).getCharacters(initialSize, skip)

        val repository = CharactersRepositoryImpl(mockedRemoteDataSource, mockedLocalDataSource)
        val charactersResult = repository.getCharacters(initialSize, skip)

        verify(mockedRemoteDataSource, times(1)).getCharacters(initialSize, skip)
        assertTrue(charactersResult.contains(remoteResult[0]))
    }
}