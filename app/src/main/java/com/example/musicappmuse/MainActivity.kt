package com.example.musicappmuse

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicappmuse.ui.audio.AudioViewModel
import com.example.musicappmuse.ui.audio.HomeScreen
import com.example.musicappmuse.ui.theme.MusicAppMuseTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusicAppMuseTheme {

                val permissions = listOf(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        Manifest.permission.READ_MEDIA_AUDIO
                    } else {
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    },
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        Manifest.permission.FOREGROUND_SERVICE
                    } else {
                        null
                    }
                ).filterNotNull()

                val permissionStates = rememberMultiplePermissionsState(permissions = permissions)

                val lifecycleOwner = LocalLifecycleOwner.current

                DisposableEffect(key1 = lifecycleOwner) {
                    val observer = LifecycleEventObserver { _, event ->

                        when (event) {
                            Lifecycle.Event.ON_RESUME -> {
                                permissionStates.launchMultiplePermissionRequest()
                            }

                            else -> Unit
                        }

                    }
                    lifecycleOwner.lifecycle.addObserver(observer)
                    onDispose {
                        lifecycleOwner.lifecycle.removeObserver(observer)
                    }

                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    when (permissionStates.allPermissionsGranted) {
                        true -> {
                            MusicAppMuseApp()
                        }

                        else -> {
                            PermissionScreen()
                        }
                    }

                }

            }
        }
    }

}


@Composable
fun PermissionScreen(modifier: Modifier = Modifier) {

    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Grant Permission Required to Use the App"
        )
    }

}

@Composable
fun MusicAppMuseApp(modifier: Modifier = Modifier) {


    val audioViewModel = viewModel(modelClass = AudioViewModel::class.java)

    val audioList = audioViewModel.audioList

    Scaffold { innerpadding ->

        Box(modifier = modifier.padding(innerpadding)) {

            HomeScreen(
                progress = audioViewModel.currentAudioProgress.value,
                onProgressChange = {
                    audioViewModel.seekTo(it)
                },
                isAudioPlaying = audioViewModel.isAudioPlaying,
                audioList = audioList,
                currentPlayingAudio = audioViewModel.currentPlayingAudio.value,
                onStart = { audioViewModel.playAudio(it) },
                onItemClick = { audioViewModel.playAudio(it) },
                onNext = { audioViewModel.skipToNext() }
            )

        }

    }

}