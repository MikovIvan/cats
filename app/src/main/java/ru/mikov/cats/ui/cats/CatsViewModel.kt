package ru.mikov.cats.ui.cats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import ru.mikov.cats.data.models.Cat
import ru.mikov.cats.data.repositories.CatsRepository

class CatsViewModel : ViewModel() {
    private val catsRepository = CatsRepository()

    fun fetchCats(): Flow<PagingData<Cat>> {
        return catsRepository.fetchCats().cachedIn(viewModelScope)
    }
}
