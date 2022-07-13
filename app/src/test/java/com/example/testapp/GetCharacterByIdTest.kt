package com.example.marvelapplication

import com.marvel.entities.Image

import com.marvel.usecases.GetCharacterById
import com.marvel.repository.CharactersRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.doReturn

class GetCharacterByIdTest {
    private val mockedCharactersRepo = Mockito.mock(CharactersRepository::class.java)
    private val fakeCharacter = Character(0, "", "", Image("", ""))

    @Test
    fun `should return single Character by id`() = runTest {
        doReturn(fakeCharacter).`when`(mockedCharactersRepo).getCharacterById(0)

        val character = GetCharacterById(mockedCharactersRepo).run(0)
        Assert.assertEquals(character, fakeCharacter)
    }
}