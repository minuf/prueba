package com.example.data

import com.marvel.CharactersRepositoryImpl
import com.marvel.local.LocalCharactersDataSource
import com.marvel.remote.RemoteCharactersDataSource
import com.marvel.domain.model.Character
import com.marvel.domain.model.Result
import com.marvel.domain.repositories.CharactersRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.reset
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersRepositoryImplTest {
    private val remoteDataSourceMock: RemoteCharactersDataSource =
        Mockito.mock(RemoteCharactersDataSource::class.java)

    private val localDataSourceMock: LocalCharactersDataSource =
        Mockito.mock(LocalCharactersDataSource::class.java)

    private val fakeCharacters = listOf(Character(1, "", "", ""))

    private var initialSize = 0
    private var initialSkip = 0

    private lateinit var repository: CharactersRepository

    @Before
    fun makeSut() {
        initialSize = Random.nextInt()
        initialSkip = Random.nextInt()

        repository = CharactersRepositoryImpl(remoteDataSourceMock, localDataSourceMock)
    }

    @After
    fun resetMocks() {
        reset(remoteDataSourceMock)
        reset(localDataSourceMock)
    }

    @Test
    fun `Should return local characters if has saved characters in local (db)`() = runTest {
        doReturn(fakeCharacters).`when`(localDataSourceMock)
            .getCharacters(initialSize, initialSkip)

        val expectedResult = Result.Success(fakeCharacters)
        val result = repository.getCharacters(initialSize, initialSkip)
        assertEquals(expectedResult, result)
    }

    @Test
    fun `Should return remote characters when no saved characters in local (db)`() = runTest {
        doReturn(listOf<Character>()).`when`(localDataSourceMock)
            .getCharacters(initialSize, initialSkip)
        doReturn(fakeCharacters).`when`(remoteDataSourceMock)
            .getCharacters(initialSize, initialSkip)

        val expectedResult = Result.Success(fakeCharacters)
        val result = repository.getCharacters(initialSize, initialSkip)
        assertEquals(expectedResult, result)
    }

    @Test
    fun `Should save characters in local (db) when fetch remote characters`() = runTest {
        doReturn(listOf<Character>()).`when`(localDataSourceMock)
            .getCharacters(initialSize, initialSkip)
        doReturn(fakeCharacters).`when`(remoteDataSourceMock)
            .getCharacters(initialSize, initialSkip)

        repository.getCharacters(initialSize, initialSkip)

        verify(remoteDataSourceMock, times(1)).getCharacters(initialSize, initialSkip)
        verify(localDataSourceMock, times(1)).saveCharacters(fakeCharacters)
    }

    @Test
    fun `Should fetch remote characters when paginated list (size, skip) not in local (db)`() =
        runTest {
            val remoteResult = listOf(Character(2, "", "", ""))

            doReturn(listOf<Character>()).`when`(localDataSourceMock)
                .getCharacters(initialSize, initialSkip)

            doReturn(remoteResult).`when`(remoteDataSourceMock)
                .getCharacters(initialSize, initialSkip)

            val charactersResult = repository.getCharacters(initialSize, initialSkip)

            verify(remoteDataSourceMock, times(1)).getCharacters(initialSize, initialSkip)
            assertTrue(
                charactersResult is Result.Success && charactersResult.data.contains(
                    remoteResult[0]
                )
            )
        }
}