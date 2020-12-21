package dev.johnoreilly.mortyuicomposekmp.ui.episodes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.johnoreilly.mortyuicomposekmp.GetEpisodesQuery

@Composable
fun EpisodesListRowView(episode: GetEpisodesQuery.Result) {

    Row(modifier = Modifier.fillMaxWidth().clickable(onClick = {  }).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
            Text(episode.name ?: "", style = MaterialTheme.typography.h6)
            Text(episode.episode ?: "",
                style = MaterialTheme.typography.subtitle2)
        }

        Column(horizontalAlignment = Alignment.End,
            modifier = Modifier.fillMaxWidth().padding(end = 16.dp))
        {
            Text(episode.air_date ?: "",
                style = MaterialTheme.typography.subtitle2, color = Color.Gray)
        }

    }
    Divider()
}