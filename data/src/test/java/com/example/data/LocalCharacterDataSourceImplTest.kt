package com.example.data

import com.marvel.local.LocalCharactersDataSource
import com.marvel.local.LocalCharactersDataSourceImpl
import com.marvel.local.room.CharacterDao
import com.marvel.local.room.DbCharacterModel
import com.marvel.util.toDomainModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.reset
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class LocalCharacterDataSourceImplTest {
    private val characterDao: CharacterDao = Mockito.mock(CharacterDao::class.java)
    private val fakeDbCharacter = DbCharacterModel(1, "", "", "")
    private val fakeDbList = listOf(fakeDbCharacter)
    private var size = 0
    private var skip = 0
    private var fakeId = 0
    private lateinit var localDataSource: LocalCharactersDataSource

    @Before
    fun makeSut() {
        size = Random.nextInt()
        skip = Random.nextInt()
        fakeId = Random.nextInt()

        localDataSource = LocalCharactersDataSourceImpl(characterDao)
    }

    @After
    fun resetMocks() {
        reset(characterDao)
    }

    @Test
    fun `Should call characterDao getCharacters with correct params`() = runTest {
        doReturn(fakeDbList).`when`(characterDao).getCharacters(size, skip)
        localDataSource.getCharacters(size, skip)

        verify(characterDao, times(1)).getCharacters(size, skip)
    }

    @Test
    fun `Should return list of Characters when call characterDao getCharacters`() = runTest {
        doReturn(fakeDbList).`when`(characterDao).getCharacters(size, skip)
        val expectedResult = fakeDbList.map { it.toDomainModel() }
        val result = localDataSource.getCharacters(size, skip)

        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Should call characterDao getCharacterById with correct params`() = runTest {
        doReturn(fakeDbCharacter).`when`(characterDao).getCharacterById(fakeId)
        localDataSource.getCharacterById(fakeId)

        verify(characterDao, times(1)).getCharacterById(fakeId)
    }

    @Test
    fun `Should return single Character when call characterDao getCharacterById`() = runTest {
        doReturn(fakeDbCharacter).`when`(characterDao).getCharacterById(fakeId)
        val expectedResult = fakeDbCharacter.toDomainModel()
        val result = localDataSource.getCharacterById(fakeId)

        Assert.assertEquals(expectedResult, result)
    }
}