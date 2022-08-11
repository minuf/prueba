package com.example.marvelapplication

import com.marvel.domain.model.Character
import com.marvel.domain.model.Result
import com.marvel.domain.repositories.CharactersRepository
import com.marvel.domain.usecases.GetCharacterByIdUseCaseImpl
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.doReturn

class GetCharacterByIdUseCaseTest {
    private val mockedCharactersRepo = Mockito.mock(CharactersRepository::class.java)
    private val fakeCharacter = Character(0, "", "", "")
    private val fakeResult = Result.Success(fakeCharacter)

    @Test
    fun `should return single Character by id`() = runTest {
        doReturn(fakeResult).`when`(mockedCharactersRepo).getCharacterById(0)

        val character = GetCharacterByIdUseCaseImpl(mockedCharactersRepo)(0)
        Assert.assertEquals(character, fakeResult)
    }
}