package com.adyen.android.assignment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.api.PlanetaryService
import com.adyen.android.assignment.api.model.AstronomyPicture
import com.adyen.android.assignment.ui.dialogs.ReorderOption
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface AstronomyUiState {
    data object Loading : AstronomyUiState

    data class Topics(
        val list: List<AstronomyPicture>,
    ) : AstronomyUiState

    data object Empty : AstronomyUiState

    data object Error : AstronomyUiState
}

class AstronomyViewModel : ViewModel() {

    // Expose screen UI state
    private val _uiState = MutableStateFlow<AstronomyUiState>(AstronomyUiState.Loading)
    val uiState: StateFlow<AstronomyUiState> = _uiState.asStateFlow()

    private var originalList: List<AstronomyPicture> = emptyList()

    init {
        fetchList()
    }

    private fun fetchList() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = PlanetaryService.instance.getPictures()
            val list = response.body()
            if (response.isSuccessful && list != null) {
                val state = if (list.isNotEmpty()) {
                    originalList = list
                    AstronomyUiState.Topics(list)
                } else {
                    AstronomyUiState.Empty
                }
                _uiState.update {
                    state
                }
            } else {
                _uiState.update {
                    AstronomyUiState.Error
                }
            }
        }
    }

    fun sort(reorderOption: ReorderOption) {
        val list = (_uiState.value as? AstronomyUiState.Topics)?.list.orEmpty()
        if (list.isNotEmpty()) {
            if (reorderOption == ReorderOption.BY_TITLE) {
                val sorted = list.sortedBy { it.title }
                _uiState.update {
                    AstronomyUiState.Topics(sorted)
                }
                return
            }

            if (reorderOption == ReorderOption.BY_DATE) {
                val sorted = list.sortedBy { it.date }
                _uiState.update {
                    AstronomyUiState.Topics(sorted)
                }
                return
            }

            if (reorderOption == ReorderOption.RESET) {
                _uiState.update {
                    AstronomyUiState.Topics(originalList)
                }
            }
        }
    }
}