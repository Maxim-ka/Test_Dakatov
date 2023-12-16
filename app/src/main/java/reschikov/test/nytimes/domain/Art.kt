package reschikov.test.nytimes.domain

import reschikov.test.nytimes.data.network.models.Multimedia
import java.util.Date

data class Art(val subsection : String,
               val title : String,
               val description : String,
               val url : String,
               val publishDate : Date,
               val isUpDated : Boolean,
               val multiMedia : Multimedia)
