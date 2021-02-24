package dev.johnoreilly.mortycomposekmm.ui.characters

import androidx.compose.animation.fadeIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.transform.CircleCropTransformation
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.johnoreilly.mortycomposekmm.GetCharactersQuery
import dev.johnoreilly.mortycomposekmm.fragment.CharacterDetail

@Composable
fun CharactersListRowView(character: CharacterDetail, characterSelected: (network: CharacterDetail) -> Unit) {

    Row(modifier = Modifier.fillMaxWidth().clickable(onClick = { characterSelected(character) }).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {
            character.image?.let {
                CoilImage(
                    data = it,
                    modifier = Modifier.size(50.dp),
                    requestBuilder = {
                        transformations(CircleCropTransformation())
                    },
                    contentDescription = character.name
                )
            }
        }

        Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {

            Text(character.name ?: "", style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    "${character.episode?.size ?: 0} episode(s)",
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
    Divider()
}

