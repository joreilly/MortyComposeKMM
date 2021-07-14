package dev.johnoreilly.mortycomposekmm.ui.locations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.johnoreilly.mortycomposekmm.fragment.EpisodeDetail
import dev.johnoreilly.mortycomposekmm.fragment.LocationDetail


@Composable
fun LocationsListRowView(location: LocationDetail, locationSelected: (location: LocationDetail) -> Unit) {

    Row(modifier = Modifier.fillMaxWidth()
        .clickable(onClick = { locationSelected(location) })
        .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(modifier = Modifier.weight(1f)) {
            Text(
                location.name,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                maxLines = 1, overflow = TextOverflow.Ellipsis,)

            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    "${location.residents.size} resident(s)",
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
    Divider()
}