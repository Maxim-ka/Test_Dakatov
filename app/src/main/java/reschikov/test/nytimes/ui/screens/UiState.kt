package reschikov.test.nytimes.ui.screens

import reschikov.test.nytimes.domain.Art

data class UiState(val isLoading: Boolean = false, val arts: List<Art> = emptyList(), val error : Throwable? = null)
