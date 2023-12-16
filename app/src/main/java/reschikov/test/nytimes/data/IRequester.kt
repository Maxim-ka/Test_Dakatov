package reschikov.test.nytimes.data

import reschikov.test.nytimes.data.network.models.Reply
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

interface IRequester {

    suspend fun getWordArticles() : Pair<Reply?, Throwable?>

    fun <T> getCallBack(continuation: Continuation<T>): Callback<T> {
        return object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                continuation.resumeWithException(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if(response.isSuccessful){
                    response.body()?.let { continuation.resume(it) }
                } else {
                    response.errorBody()?.let { continuation.resumeWithException(Throwable(it.string())) }
                }
            }
        }
    }
}