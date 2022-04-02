package com.rex50.zocketassignment.ui.page

import android.util.Log
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
class FragPageDetailsViewModel
@Inject
constructor(
    private val pagesRepo: PagesRepo
) : ViewModel() {

    companion object {
        private const val TAG = "FragPageDetailsViewModel"
    }

    private val _pageData: MutableStateFlow<Data<PageData>> = MutableStateFlow(Data(responseType = Status.LOADING))
    val pageData = _pageData.asStateFlow()

    fun getPageInfo() = viewModelScope.launch {
        _pageData.emit(Data(responseType = Status.LOADING))
        when(val result = pagesRepo.getSelectedPage()) {
            is Result.Success -> _pageData.emit(
                Data(
                    responseType = Status.SUCCESSFUL,
                    data = result.data
                )
            )

            is Result.Failure -> _pageData.emit(
                Data(
                    responseType = Status.ERROR,
                    error = result.exception
                )
            )
        }
    }

    fun updatePageInfo(pageData: PageData) = viewModelScope.launch {
        _pageData.emit(Data(responseType = Status.LOADING))
        when(val result = pagesRepo.updatePage(pageData)) {
            is Result.Success -> {
                pagesRepo.cachePage(result.data)
                _pageData.emit(
                    Data(
                        responseType = Status.SUCCESSFUL,
                        data = result.data
                    )
                )
                sendDataToZocketServer(result.data)
            }
            is Result.Failure -> _pageData.emit(
                Data(
                    responseType = Status.ERROR,
                    error = result.exception
                )
            )
        }
    }

    private suspend fun sendDataToZocketServer(data: PageData) {
        val sendToServerResult = pagesRepo.sendPageData(data)
        if(sendToServerResult is Result.Success) {
            Log.i(TAG, "sendDataToZocketServer: Successfully updated data")
        } else {
            Log.i(TAG, "sendDataToZocketServer: Failed while updating data")
        }
    }

    fun createDetailsList(currentPageData: PageData): List<Pair<String, String>> {
        return listOf(
            Pair("About", currentPageData.about),
            Pair("Category", currentPageData.category),
            Pair("Email", currentPageData.emails.toString()),
            Pair("Fan count", currentPageData.fan_count.toString()),
            Pair("Followers", currentPageData.followers_count.toString()),
            Pair("Phone", currentPageData.phone),
            Pair("link", currentPageData.link),
            Pair("Rating count", currentPageData.rating_count.toString()),
            Pair("Website", currentPageData.website),
            Pair("Business", currentPageData.business.name),
            Pair("Engagement count", currentPageData.engagement.count.toString()),
        )
    }

}