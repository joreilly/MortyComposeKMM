package dev.johnoreilly.mortycomposekmm.ui.characters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.johnoreilly.mortycomposekmm.GetCharactersQuery

@Composable
fun CharactersListRowView(character: GetCharactersQuery.Result) {

    Row(modifier = Modifier.fillMaxWidth().clickable(onClick = {  }).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        val imageUrl = character.image
        if (imageUrl != null) {
            Card(modifier = Modifier.preferredSize(50.dp), shape = CircleShape) {
                CoilImage(data = imageUrl)
            }
        } else {
            Spacer(modifier = Modifier.preferredSize(50.dp))
        }

        Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
            Text(character.name ?: "", style = MaterialTheme.typography.h6)
            Text("${character.episode?.size ?: 0} episode(s)",
                style = MaterialTheme.typography.subtitle2, color = Color.Gray)
        }
    }
    Divider()
}

