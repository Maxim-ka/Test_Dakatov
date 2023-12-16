package reschikov.test.nytimes.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Reply(@SerializedName("section") @Expose val section: String,
                 @SerializedName("results") @Expose val articles: List<Article>)