package com.adyen.android.assignment.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.adyen.android.assignment.api.model.AstronomyPicture
import com.adyen.android.assignment.ui.AstronomyUiState
import com.adyen.android.assignment.ui.dialogs.ReorderDialog
import com.adyen.android.assignment.ui.dialogs.ReorderOption

@Composable
fun AstronomyListScreen(
    state: AstronomyUiState,
    currentSelection: ReorderOption,
    showDialog: Boolean,
    onItemClicked: (pic: AstronomyPicture) -> Unit,
    onFabClicked: () -> Unit,
    onDismissDialog: () -> Unit,
    refreshCall: () -> Unit,
    onSelection: (ReorderOption) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            if (state is AstronomyUiState.Topics) {
                ReorderListFab(onClick = onFabClicked) // FAB composable with the action passed
            }
        }
    ) {

        if (showDialog) {
            ReorderDialog(
                currentSelection = currentSelection,
                onDismissRequest = { },
                onSelect = { option ->
                    onSelection(option)
                }
            )
        }
        when (state) {
            is AstronomyUiState.Topics -> {
                LazyColumn(
                    contentPadding = it, // Ensure content is not overlapped by FAB
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.list) { picture ->
                        ListItemRow(picture) {
                            onItemClicked(picture)
                        }
                    }
                }
            }

            is AstronomyUiState.Empty -> {
                // Handle Empty State
            }

            is AstronomyUiState.Loading -> {
                // Handle Loading State
            }

            else -> {
                GenericMessageScreen() {
                    refreshCall()
                }
            }
        }
    }
}

@Composable
fun ListItemRow(
    picture: AstronomyPicture,
    modifier: Modifier = Modifier,
    onItemClicked: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .padding(16.dp)
            .clickable { onItemClicked() }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(picture.url)
                .crossfade(true)
                .build(),
            contentDescription = "image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Column {
            Text(picture.title, fontSize = 20.sp)
            Text("${picture.date}", fontSize = 16.sp)
        }
    }
}
