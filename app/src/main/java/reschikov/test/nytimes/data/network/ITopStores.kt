package reschikov.test.nytimes.data.network

import reschikov.test.nytimes.data.network.models.Reply
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ITopStores {

    @GET("{section}.json")
    fun requestStores(@Path("section") section: String, @Query("api-key") key: String): Call<Reply>
}