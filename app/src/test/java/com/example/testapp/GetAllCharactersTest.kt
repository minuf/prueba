package com.example.marvelapplication

import com.example.marvelapplication.core.domain.model.Character
import com.example.marvelapplication.core.domain.model.Image
import com.example.marvelapplication.core.domain.ports.GetAllCharacters
import com.example.marvelapplication.core.domain.repository.CharactersRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.doReturn

class GetAllCharactersTest {

    private val mockedCharactersRepo = Mockito.mock(CharactersRepository::class.java)
    private val fakeCharacter = Character(0, "", "", Image("", ""))
    private val fakeCharacters: List<Character> = listOf(fakeCharacter)

    @Test
    fun `should return list of Characters`() = runTest {
        doReturn(fakeCharacters).`when`(mockedCharactersRepo).getAllCharacters()

        val characters = GetAllCharacters(mockedCharactersRepo).run()
        Assert.assertEquals(characters, fakeCharacters)
    }
}