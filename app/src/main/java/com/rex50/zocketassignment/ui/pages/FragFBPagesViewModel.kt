package com.rex50.zocketassignment.ui.pages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rex50.zocketassignment.data.models.PageData
import com.rex50.zocketassignment.data.repos.meta.page.PagesRepo
import com.rex50.zocketassignment.utils.Data
import com.rex50.zocketassignment.utils.Result
import com.rex50.zocketassignment.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragFBPagesViewModel
@Inject
constructor(
    private val pagesRepo: PagesRepo
) : ViewModel() {

    private val _pagesFlow: MutableStateFlow<Data<List<PageData>>> = MutableStateFlow(Data(responseType = Status.LOADING))
    val pagesFlow = _pagesFlow.asStateFlow()

    fun fetchPages() = viewModelScope.launch {
        _pagesFlow.emit(Data(responseType = Status.LOADING))
        when(val result = pagesRepo.fetchPages()) {
            is Result.Success -> {
                pagesRepo.cachePages(result.data)
                _pagesFlow.emit(
                    Data(
                        responseType = Status.SUCCESSFUL,
                        result.data
                    )
                )
            }
            is Result.Failure -> _pagesFlow.emit(
                Data(
                    responseType = Status.ERROR,
                    error = result.exception
                )
            )
        }
    }

    suspend fun setSelectedPage(pageData: PageData) {
        // set pageId in sharedPrefs
        pagesRepo.setSelectedPage(pageData.id)
    }
}