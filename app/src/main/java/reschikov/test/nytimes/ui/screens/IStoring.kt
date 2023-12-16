package reschikov.test.nytimes.ui.screens

import reschikov.test.nytimes.domain.Art

interface IStoring {

    suspend fun getTopStoresWord() : Pair<List<Art>, Throwable?>
}