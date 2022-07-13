package com.example.marvelapplication

import com.marvel.usecases.GetCharacters
import com.marvel.repository.CharactersRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import com.marvel.entities.Image


class GetCharactersTest {

    private val mockedCharactersRepo = Mockito.mock(CharactersRepository::class.java)
    private val fakeCharacter = Character(0, "", "", Image("", ""))
    private val fakeCharacters: List<Character> = listOf(fakeCharacter)

    @Test
    fun `should return list of Characters`() = runTest {
        //doReturn(fakeCharacters).`when`(mockedCharactersRepo).getCharacters()

        val characters = GetCharacters(mockedCharactersRepo).run()
        Assert.assertEquals(characters, fakeCharacters)
    }
}