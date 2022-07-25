package com.example.marvelapplication

import com.marvel.model.Character
import com.marvel.model.Result

import com.marvel.usecases.GetCharacterById
import com.marvel.repositories.CharactersRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.doReturn

class GetCharacterByIdTest {
    private val mockedCharactersRepo = Mockito.mock(CharactersRepository::class.java)
    private val fakeCharacter = Character(0, "", "", "")
    private val fakeResult = Result.Success(fakeCharacter)

    @Test
    fun `should return single Character by id`() = runTest {
        doReturn(fakeResult).`when`(mockedCharactersRepo).getCharacterById(0)

        val character = GetCharacterById(mockedCharactersRepo)(0)
        Assert.assertEquals(character, fakeResult)
    }
}