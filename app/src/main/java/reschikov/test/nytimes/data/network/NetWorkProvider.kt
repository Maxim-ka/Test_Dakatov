package reschikov.test.nytimes.data.network

import android.net.ConnectivityManager
import android.os.Build
import kotlinx.coroutines.coroutineScope
import reschikov.test.nytimes.data.IRequester
import reschikov.test.nytimes.data.network.models.Reply
import reschikov.test.nytimes.domain.AppException
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

private const val SECTION_WORD = "world"
private const val API_KEY = "AgPpVjRAVHRzG4aK43aqVqHAJsnKZJ94"

class NetWorkProvider @Inject constructor(private val iTopStores: ITopStores,
                                          private val cm: ConnectivityManager) : IRequester {


    override suspend fun getWordArticles(): Pair<Reply?, Throwable?> {
        if (checkLackOfNetwork()) return Pair(null, AppException.NoInternet)
        return  try {
            Pair(requestWordStores(), null)
        } catch (e: Throwable) {
            Pair(null, e)
        }
    }

    @Throws(Throwable::class)
    private suspend fun requestWordStores() = coroutineScope {
        suspendCoroutine<Reply> {
            iTopStores.requestStores(SECTION_WORD, API_KEY).enqueue(getCallBack(it))
        }
    }

    private fun checkLackOfNetwork(): Boolean {
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
           return cm.activeNetwork == null
       }
       return !cm.isDefaultNetworkActive
    }
}