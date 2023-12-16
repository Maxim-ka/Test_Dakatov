package reschikov.test.nytimes.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.Date

data class Article(@SerializedName("subsection") @Expose val subsection: String,
                   @SerializedName("title") @Expose val title: String,
                   @SerializedName("abstract") @Expose val abstract: String,
                   @SerializedName("url") @Expose val url: String,
                   @SerializedName("updated_date") @Expose val updatedDate: Date,
                   @SerializedName("created_date") @Expose val createdDate: Date,
                   @SerializedName("multimedia") @Expose val multiMedias : List<Multimedia>)


