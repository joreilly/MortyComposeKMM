package dev.johnoreilly.mortyuicomposekmp.ui.episodes

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.johnoreilly.mortyuicomposekmp.GetEpisodesQuery


@Composable
fun EpisodesListRowView(episode: GetEpisodesQuery.Result) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxWidth()) {

        Column(modifier = Modifier.weight(1f)) {
            Text(episode.name ?: "",
                style = MaterialTheme.typography.h6,
                maxLines = 1, overflow = TextOverflow.Ellipsis,)
            Text(episode.episode ?: "",
                style = MaterialTheme.typography.subtitle2)
        }

        Text(episode.air_date ?: "", modifier = Modifier.padding(start = 16.dp))
    }
}