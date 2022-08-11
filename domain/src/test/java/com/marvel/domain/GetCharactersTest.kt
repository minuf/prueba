package com.marvel.domain

import com.marvel.domain.model.Character
import com.marvel.domain.repositories.CharactersRepository
import com.marvel.domain.model.Result
import com.marvel.domain.usecases.GetCharactersUseCaseImpl
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.doReturn


class GetCharactersTest {

    private val mockedCharactersRepo = Mockito.mock(CharactersRepository::class.java)
    private val fakeCharacter = Character(0, "", "", "")
    private val fakeCharacters: List<Character> = listOf(fakeCharacter)

    private val initialSize = 50
    private val initialSkip = 0
    private val getCharacters = GetCharactersUseCaseImpl(mockedCharactersRepo)

    @Test
    fun `should return Result class with list of Characters`() = runTest {
        doReturn(Result.Success(fakeCharacters)).`when`(mockedCharactersRepo)
            .getCharacters(initialSize, initialSkip)
        val expectedResult = Result.Success(fakeCharacters)

        val charactersResult = getCharacters(initialSize, initialSkip)
        Assert.assertEquals(expectedResult, charactersResult)
    }
}