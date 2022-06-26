package dev.johnoreilly.mortycomposekmm.ui.locations

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.johnoreilly.mortycomposekmm.fragment.LocationDetail
import dev.johnoreilly.mortycomposekmm.ui.MainViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LocationDetailView(viewModel: MainViewModel, locationId: String, popBack: () -> Unit) {
    val (location, setLocation) = remember { mutableStateOf<LocationDetail?>(null) }

    LaunchedEffect(locationId) {
        setLocation(viewModel.getLocation(locationId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(location?.name ?: "") },
                navigationIcon = {
                    IconButton(onClick = { popBack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        })
    {
        Surface(color = Color.LightGray) {

            LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                location?.let {

                    item {

                        Text(
                            "Residents",
                            style = MaterialTheme.typography.h5,
                            color = LocalContentColor.current,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                        )

                        Surface(color = Color.White) {
                            LocationResidentList(location)
                        }

                    }
                }
            }
        }
    }

}

@Composable
private fun LocationResidentList(location: LocationDetail) {

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        location.residents.filterNotNull().forEach { resident ->
            Row(modifier = Modifier.padding(vertical = 8.dp)) {

                AsyncImage(
                    model = resident.image,
                    contentDescription = resident.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(28.dp).clip(CircleShape)
                )

                Text(
                    resident.name,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                    style = MaterialTheme.typography.h6
                )
            }
            Divider()
        }
    }
}

