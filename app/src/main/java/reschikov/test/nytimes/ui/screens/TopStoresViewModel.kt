package reschikov.test.nytimes.ui.screens

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopStoresViewModel @Inject constructor(val iStoring: IStoring) : ViewModel(){

    private val stateUi = mutableStateOf(UiState(isLoading = true))

    init {
        takeTopStoresWord()
    }

    fun getStateUi() = stateUi

    fun takeTopStoresWord() {
        viewModelScope.launch {
            stateUi.value = stateUi.value.copy(isLoading = true,  error = null)
            iStoring.getTopStoresWord().run {
                stateUi.value = stateUi.value
                    .copy(isLoading = false, arts = first.ifEmpty { stateUi.value.arts }, error = second)
            }
        }
    }
}