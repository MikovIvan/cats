package ru.mikov.cats.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.mikov.cats.data.models.Cat
import ru.mikov.cats.data.paging.CatsPagingSource
import ru.mikov.cats.data.remote.CatsService

class CatsRepository {

    private val catsService = CatsService.create()

    fun fetchCats(): Flow<PagingData<Cat>> {
        return Pager(
            PagingConfig(pageSize = 20, prefetchDistance = 2)
        ) {
            CatsPagingSource(catsService)
        }.flow
    }
}
