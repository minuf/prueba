package com.example.marvelapplication

import com.marvel.entities.Character
import com.marvel.usecases.GetCharacters
import com.marvel.repositories.CharactersRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import com.marvel.entities.Image
import org.mockito.Mockito.doReturn


class GetCharactersTest {

    private val mockedCharactersRepo = Mockito.mock(CharactersRepository::class.java)
    private val fakeCharacter = Character(0, "", "", Image("", ""))
    private val fakeCharacters: List<Character> = listOf(fakeCharacter)

    private val initialSize = 50
    private val initialSkip = 0

    @Test
    fun `should return list of Characters`() = runTest {
        doReturn(fakeCharacters).`when`(mockedCharactersRepo).getCharacters(initialSize, initialSkip)

        val characters = GetCharacters(mockedCharactersRepo).run()
        Assert.assertEquals(characters, fakeCharacters)
    }
}