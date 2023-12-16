package reschikov.test.nytimes.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import reschikov.test.nytimes.data.network.models.Multimedia
import reschikov.test.nytimes.data.network.models.Reply
import reschikov.test.nytimes.domain.Art
import reschikov.test.nytimes.ui.screens.IStoring
import javax.inject.Inject
import javax.inject.Singleton

private const val FORMAT_FOR_LIST = "Large Thumbnail"

@Singleton
class Repository @Inject constructor(val iRequester: IRequester) : IStoring {

    override suspend fun getTopStoresWord(): Pair<List<Art>, Throwable?> = withContext(Dispatchers.IO) {
        iRequester.getWordArticles().run {
            first?.let { mapping(it) } ?: run { Pair(emptyList(), second) }
        }
    }

    private fun mapping(reply: Reply) : Pair<List<Art>, Throwable?> {
        return Pair(reply.articles.map { article ->
            Art(subsection = article.subsection,
                title = article.title,
                url = article.url,
                description = article.abstract,
                publishDate = if (article.updatedDate.time == article.createdDate.time) article.createdDate else article.updatedDate,
                isUpDated = article.updatedDate.time > article.createdDate.time,
                multiMedia = getMultimedia(article.multiMedias)) }.toList(),
            null)
    }

    private fun getMultimedia(list: List<Multimedia>) : Multimedia =
        list.first { multimedia -> multimedia.format == FORMAT_FOR_LIST }
}