package com.example.testapp.core.infra.adapter.retrofit

import com.example.domain.Character
import androidx.paging.*
import com.example.testapp.core.domain.repository.CharactersRepository
import com.example.testapp.core.infra.adapter.retrofit.services.CharactersService
import kotlinx.coroutines.flow.Flow

private const val PAGE_SIZE = 30

class RetrofitCharactersRepository constructor(private val charactersService: CharactersService) :
    CharactersRepository {

    override suspend fun getCharacters(size: Int, skip: Int): List<com.example.domain.Character> {
        val response =
            charactersService.fetchAllCharacters(limit = size, offset = skip)
        return response.body()?.data?.results ?: listOf()
    }

    override suspend fun getCharacterById(id: Int): Character {
        val response =
            charactersService.fetCharacterById(id)
        return response.body()!!.data.results[0]
    }

    fun getAllCharactersAsFlow(): Flow<PagingData<Character>> {
        /*return Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                CharacterPagingSource(charactersService)
            }
            , initialKey = 1
        ).flow*/
        println("pasa por aki")

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = PAGE_SIZE + (PAGE_SIZE * 2),
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CharacterPagingSource(charactersService)
            }
        ).flow
        /*return Pager(
            // Configure how data is loaded by passing additional properties to
            // PagingConfig, such as prefetchDistance.
            PagingConfig(pageSize = 20)
        ) {
            CharacterPagingSource(charactersService)
        }.flow*/
    }
}

class CharacterPagingSource(private val charactersService: CharactersService) :
    PagingSource<Int, Character>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        println("Entro 0")

        return try {
            val position = params.key ?: 1

            println(params.key)
            val response = charactersService.fetchAllCharacters(offset = params.loadSize * position)
            println("Entro 1" + response.body())
            return LoadResult.Page(
                data = response.body()!!.data.results,
                prevKey = if (position == 1) null else position - 1,
                nextKey = position + 1
            )
        } catch (e: Exception) {
            println("Entro 2")

            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}