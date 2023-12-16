package reschikov.test.nytimes.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import reschikov.test.nytimes.data.network.models.Multimedia
import reschikov.test.nytimes.domain.AppException
import reschikov.test.nytimes.domain.Art
import reschikov.test.nytimes.ui.theme.NyTimesTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopStoresWordScreen(
    viewModel: TopStoresViewModel = hiltViewModel()
){

    val formatter = remember { SimpleDateFormat("dd MMM yyyy", Locale.getDefault()) }
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState = viewModel.getStateUi()

    LaunchedEffect(key1 = uiState.value.error) {
        if (uiState.value.error != null) {
            snackbarHostState.showSnackbar(
                message = if (uiState.value.error == AppException.NoInternet) {
                    "Нет интернета"
                } else {
                    uiState.value.error?.message ?: "Ошибка"
                },
                actionLabel = "Закрыть",
                withDismissAction = true,
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Мировые новости", modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                    style = MaterialTheme.typography.titleLarge)
            })
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                ShowMessage(data = data) { viewModel.takeTopStoresWord() }
            }
        }
    ) { pv ->
        Box(contentAlignment = Alignment.Center) {
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(pv)) {
                items(uiState.value.arts.size) {
                    Item(art = uiState.value.arts[it], formatter)
                }
            }
            if (uiState.value.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun ShowMessage(data: SnackbarData, onClose: () -> Unit){

    Snackbar(
        modifier = Modifier
            .border(1.dp, MaterialTheme.colorScheme.secondary)
            .padding(12.dp),
        action = {
            TextButton(
                onClick = {
                    onClose()
                    data.performAction()
                }
            ) { Text(data.visuals.actionLabel ?: "") }
        }
    ) {
        Text(data.visuals.message)
    }
}

@Stable
@Composable
fun Item(art: Art, format: SimpleDateFormat) {

    ElevatedCard(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Header(art = art, formatter = format)
            Spacer(modifier = Modifier.height(8.dp))
            if (art.title.isNotEmpty()) {
                Text(text = art.title, style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(8.dp))
            }
            if (art.description.isNotEmpty()) {
                Text(text = art.description, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
            }
            Row {
                if (art.multiMedia.url.isNotEmpty() && art.multiMedia.url.isNotBlank()) {
                    Image(url = art.multiMedia.url)
                    Spacer(modifier = Modifier.width(16.dp))
                }
                if (art.multiMedia.caption.isNotEmpty()) Text(text = art.multiMedia.caption, style = MaterialTheme.typography.labelMedium)
            }
            if (art.url.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = art.url, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun Header(art: Art, formatter: SimpleDateFormat){
    Row(Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(text = art.subsection, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Start)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "${if (art.isUpDated) "Обновлено:" else "Опубликовано:"} ${formatter.format(art.publishDate)}"   ,
            style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.End)
    }
}

@Composable
fun Image(url: String){
    SubcomposeAsyncImage(
        model = url,
        loading = {
            CircularProgressIndicator()
        },
        contentDescription = null,
        modifier = Modifier.size(150.dp)
    )
} 

@Preview(showBackground = true)
@Composable
fun ItemPreview() {
    NyTimesTheme {
        Item(art = Art(subsection = "europe",
                       title = "What I’m reading: Historical memory edition",
                       description = "Finding escapism and insight in two novels and Masha Gessen’s new essay.",
                       url = "https://www.nytimes.com/2023/12/15/world/europe/what-im-reading-historical-memory-edition.html",
                       publishDate = Date(),
                       isUpDated = false,
                       multiMedia = Multimedia(
                           url = "https://static01.nyt.com/images/2023/12/15/multimedia/15InterpreterNL-HistoricalMemory-1-pqhg/15InterpreterNL-HistoricalMemory-1-pqhg-thumbLarge.jpg",
                           format = "",
                           caption = "The Memorial to the Murdered Jews of Europe, in Berlin. A new essay by Masha Gessen in The New Yorker explores the politics of memory in Europe and its implications for current events in Gaza, tracing history back via the lens of their own Jewish family."
                       )
            ), SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        )
    }
}