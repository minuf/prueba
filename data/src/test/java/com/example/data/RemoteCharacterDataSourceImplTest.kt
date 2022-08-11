package com.example.data

import com.marvel.remote.RemoteCharactersDataSource
import com.marvel.remote.RemoteCharactersDataSourceImpl
import com.marvel.remote.retrofit.data.Data
import com.marvel.remote.retrofit.data.Image
import com.marvel.remote.retrofit.data.MarvelResponse
import com.marvel.remote.retrofit.data.RemoteCharacterModel
import com.marvel.remote.retrofit.services.CharactersService
import com.marvel.util.toDomainModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.reset
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import retrofit2.Response
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class RemoteCharacterDataSourceImplTest {
    private val charactersService: CharactersService = Mockito.mock(CharactersService::class.java)
    private val fakeRemoteCharacter = RemoteCharacterModel(1, "", "", Image("", ""))
    private val fakeRemoteList = listOf(fakeRemoteCharacter)
    private val fakeRemoteResult: MarvelResponse<List<RemoteCharacterModel>> = MarvelResponse(Data(fakeRemoteList))

    private var size = 0
    private var skip = 0
    private var fakeId = 0
    private lateinit var remoteDataSource: RemoteCharactersDataSource

    @Before
    fun makeSut() {
        size = Random.nextInt()
        skip = Random.nextInt()
        fakeId = Random.nextInt()

        remoteDataSource = RemoteCharactersDataSourceImpl(charactersService)
    }

    @After
    fun resetMocks() {
        reset(charactersService)
    }

    @Test
    fun `Should call characterService getCharacters with correct params`() = runTest {
        doReturn(Response.success(fakeRemoteResult)).`when`(charactersService).fetchAllCharacters(size, skip)
        remoteDataSource.getCharacters(size, skip)

        verify(charactersService, times(1)).fetchAllCharacters(size, skip)
    }

    @Test
    fun `Should return list of Characters when call getCharacters`() = runTest {
        doReturn(Response.success(fakeRemoteResult)).`when`(charactersService).fetchAllCharacters(size, skip)

        val expectedResult = fakeRemoteResult.data.results.map { it.toDomainModel() }
        val result = remoteDataSource.getCharacters(size, skip)

        assertEquals(expectedResult, result)
    }

    @Test
    fun `Should call characterService getCharacterById with correct params`() = runTest {
        doReturn(Response.success(fakeRemoteResult)).`when`(charactersService).fetCharacterById(fakeId)
        remoteDataSource.getCharacterById(fakeId)

        verify(charactersService, times(1)).fetCharacterById(fakeId)
    }

    @Test
    fun `Should return single Character when call characterService getCharacters(id) with id param`() = runTest {
        doReturn(Response.success(fakeRemoteResult)).`when`(charactersService).fetCharacterById(fakeId)

        val expectedResult = fakeRemoteResult.data.results[0].toDomainModel()
        val result = remoteDataSource.getCharacterById(fakeId)

        assertEquals(expectedResult, result)
    }
}