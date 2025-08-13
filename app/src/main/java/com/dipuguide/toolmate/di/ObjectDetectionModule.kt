package com.dipuguide.toolmate.di

import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.ObjectDetector
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ObjectDetectionModule {

    @Provides
    @Singleton
    fun provideObjectDetectionOptions(): ObjectDetectorOptions {
        return ObjectDetectorOptions.Builder()
            .setDetectorMode(ObjectDetectorOptions.STREAM_MODE)
            .enableClassification()
            .build()
    }

    @Provides
    @Singleton
    fun provideObjectDetection(): ObjectDetector {
        return ObjectDetection.getClient(provideObjectDetectionOptions())
    }
}