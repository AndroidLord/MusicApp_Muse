package com.example.musicappmuse.data.repository

import com.example.musicappmuse.data.ContentResolverHelper
import com.example.musicappmuse.data.model.Audio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AudioRepository @Inject
constructor(
    private val contentResolverHelper: ContentResolverHelper
){

    suspend fun getAudioData(): List<Audio> = withContext(Dispatchers. IO){
        contentResolverHelper.getAudioData()
    }

}