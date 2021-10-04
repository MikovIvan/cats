package ru.mikov.cats.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.mikov.cats.data.models.Cat
import ru.mikov.cats.data.remote.CatsService

const val NUMBER_OF_CATS_PER_PAGE = 15

class CatsPagingSource(private val catsService: CatsService) : PagingSource<Int, Cat>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> {
        return try {
            val nextPageNumber = params.key ?: 0
            val response = catsService.getCats(NUMBER_OF_CATS_PER_PAGE, nextPageNumber)
            LoadResult.Page(
                data = response.body()!!.toList(),
                prevKey = null,
                nextKey = (response.raw().header("Pagination-Page")?.toInt())!! + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Cat>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
