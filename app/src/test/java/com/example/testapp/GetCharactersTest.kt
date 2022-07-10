package com.example.marvelapplication

import com.example.testapp.core.domain.ports.GetCharacters
import com.example.testapp.core.domain.repository.CharactersRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import com.example.domain.Character
import com.example.domain.Image


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