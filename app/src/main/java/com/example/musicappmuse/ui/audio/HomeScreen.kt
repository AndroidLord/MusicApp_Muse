package com.example.musicappmuse.ui.audio

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.musicappmuse.data.model.Audio
import com.example.musicappmuse.ui.theme.MusicAppMuseTheme
import javax.annotation.meta.When
import kotlin.math.floor


val dummyAudioList = listOf(
    Audio(
        id = 1L,
        uri = "".toUri(),
        displayName = "Dummy Audio",
        artist = "Dummy Artist",
        album = "Dummy Album",
        data = "Dummy Data",
        duration = 100,
        title = "Dummy Title"
    ),
    Audio(
        id = 2L,
        uri = "".toUri(),
        displayName = "Dummy Audio2",
        artist = "Dummy Artist2",
        album = "Dummy Album2",
        data = "Dummy Data2",
        duration = 5300,
        title = "Dummy Title2"
    ),
    Audio(
        id = 3L,
        uri = "".toUri(),
        displayName = "Dummy Audio3",
        artist = "Dummy Artist3",
        album = "Dummy Album3",
        data = "Dummy Data3",
        duration = 1000,
        title = "Dummy Title3"
    ),
    Audio(
        id = 4L,
        uri = "".toUri(),
        displayName = "Dummy Audio4",
        artist = "Dummy Artist4",
        album = "Dummy Album4",
        data = "Dummy Data4",
        duration = 1000,
        title = "Dummy Title4"
    ),
    Audio(
        id = 5L,
        uri = "".toUri(),
        displayName = "Dummy Audio5",
        artist = "Dummy Artist5",
        album = "Dummy Album5",
        data = "Dummy Data5",
        duration = 1000,
        title = "Dummy Title5"
    ),
    Audio(
        id = 6L,
        uri = "".toUri(),
        displayName = "Dummy Audio6",
        artist = "Dummy Artist6",
        album = "Dummy Album6",
        data = "Dummy Data6",
        duration = 1000,
        title = "Dummy Title6"
    )

)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    progress: Float,
    onProgressChange: (Float) -> Unit,
    isAudioPlaying: Boolean,
    audioList: List<Audio>,
    currentPlayingAudio: Audio?,
    onStart: (Audio) -> Unit,
    onItemClick: (Audio) -> Unit,
    onNext: () -> Unit
) {

    val scaffoldState = rememberBottomSheetScaffoldState()
    val animatedHeight by animateDpAsState(
        targetValue = if (currentPlayingAudio == null) 0.dp else BottomSheetDefaults.SheetPeekHeight
    )

    BottomSheetScaffold(
        sheetContent = {
            currentPlayingAudio?.let {
                BottomBarPlayer(
                    progress = progress,
                    onProgressChange = onProgressChange,
                    audio = it,
                    isAudioPlaying = isAudioPlaying,
                    onStart = { onStart.invoke(currentPlayingAudio) },
                    onNext = { onNext.invoke() }
                )
            }
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = animatedHeight,

    ) {

        LazyColumn(

            contentPadding = PaddingValues(bottom = 56.dp),
        ) {
            items(audioList) { audio ->
                AudioItem(audio = audio, onItemClick = { onItemClick.invoke(audio) })
            }
        }

    }

}

@Composable
fun BottomBarPlayer(
    progress: Float,
    onProgressChange: (Float) -> Unit,
    audio: Audio,
    isAudioPlaying: Boolean,
    onStart: () -> Unit,
    onNext: () -> Unit,
) {

    Column {
        Row(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            ArtistInfo(audio = audio, modifier = Modifier.weight(1f))

            MediaPlayerController(
                isAudioPlaying = isAudioPlaying,
                onStart = { onStart.invoke() },
                onNext = { onNext.invoke() }
            )
        }
        Slider(
            value = progress,
            onValueChange = { onProgressChange.invoke(it) },
            valueRange = 0f..100f
        )
    }

}

@Composable
fun ArtistInfo(
    modifier: Modifier = Modifier,
    audio: Audio
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        PlayerIconItem(
            icon = Icons.Default.MusicNote,
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface
            )
        ) {}

        Spacer(modifier = Modifier.size(4.dp))

        Column {
            Text(
                text = audio.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Clip,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = audio.artist,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Clip,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )
        }

    }
}

@Composable
fun PlayerIconItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    border: BorderStroke? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    color: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit
) {

    Surface(
        shape = CircleShape,
        border = border,
        modifier = Modifier
            .clip(CircleShape)
            .clickable { onClick.invoke() },
        contentColor = color,
        color = backgroundColor
    ) {

        Box(
            modifier = Modifier.padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null)
        }

    }

}

@Composable
fun MediaPlayerController(
    isAudioPlaying: Boolean,
    onStart: () -> Unit,
    onNext: () -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(56.dp)
            .padding(4.dp)
    ) {
        PlayerIconItem(
            icon = when (isAudioPlaying) {
                true -> Icons.Default.Pause
                else -> Icons.Default.PlayArrow
            },
            backgroundColor = MaterialTheme.colorScheme.primary
        ) {
            onStart.invoke()
        }
        Spacer(modifier = Modifier.size(8.dp))
        Icon(
            imageVector = Icons.Default.SkipNext,
            contentDescription = null,
            modifier = Modifier.clickable { onNext.invoke() }
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun BottomBarPrev() {
    MusicAppMuseTheme {
        BottomBarPlayer(
            progress = 50f,
            onProgressChange = {},
            audio = dummyAudioList[0],
            isAudioPlaying = true,
            onStart = {},
            onNext = {}
        )
    }
}

@Composable
fun AudioItem(
    audio: Audio,
    onItemClick: (id: Long) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick.invoke(audio.id) },
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
        )
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = audio.displayName,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Clip,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = audio.artist,
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Clip,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
            Text(
                text = timeStampToDuration(audio.duration.toLong()),
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.size(8.dp))
        }

    }

}

private fun timeStampToDuration(position:Long): String{
    val totalSeconds = floor(position/1E3).toInt()
    val minutes = totalSeconds/60
    val remainingSeconds = totalSeconds - (minutes*60)
    return if(position < 0) "--:--" else "%d:%02d".format(minutes, remainingSeconds)
}