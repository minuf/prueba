package com.example.marvelapplication

import com.example.marvelapplication.core.domain.model.Character
import com.example.marvelapplication.core.domain.ports.GetAllCharacters
import com.example.marvelapplication.core.domain.ports.GetCharacterById
import com.example.marvelapplication.core.domain.repository.CharactersRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito
import org.mockito.kotlin.doReturn

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    /*private val mockedCharactersRepo = Mockito.mock(CharactersRepository::class.java)
    private val fakeCharacter = Character(0, "", "")
    private val fakeCharacters: List<Character> = listOf(fakeCharacter)

    @Test
    fun `should return list of Characters`() = runTest {
        doReturn(fakeCharacters).`when`(mockedCharactersRepo).getAllCharacters()

        val characters = GetAllCharacters(mockedCharactersRepo).run()
        assertEquals(characters, fakeCharacters)
    }

    @Test
    fun `should return single Character by id`() = runTest {
        doReturn(fakeCharacter).`when`(mockedCharactersRepo).getCharacterById(0)

        val character = GetCharacterById(mockedCharactersRepo).run(0)
        assertEquals(character, fakeCharacter)
    }*/
}