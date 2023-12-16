package reschikov.test.nytimes.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Multimedia(@SerializedName("url") @Expose val url: String,
                      @SerializedName("format") @Expose val format: String,
                      @SerializedName("caption") @Expose val caption: String)
