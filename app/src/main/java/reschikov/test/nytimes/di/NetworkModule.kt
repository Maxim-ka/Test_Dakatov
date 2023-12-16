package reschikov.test.nytimes.di

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import reschikov.test.nytimes.data.IRequester
import reschikov.test.nytimes.data.network.ITopStores
import reschikov.test.nytimes.data.network.NetWorkProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val URL_NYT_TOP_STORIES = "https://api.nytimes.com/svc/topstories/v2/"
private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideConnectManager(@ApplicationContext context: Context): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().setDateFormat(DATE_FORMAT).create()

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(URL_NYT_TOP_STORIES)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Singleton
    @Provides
    fun provideTopStores(retrofit: Retrofit): ITopStores = retrofit.create(ITopStores::class.java)

}