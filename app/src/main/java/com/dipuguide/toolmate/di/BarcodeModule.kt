package com.dipuguide.toolmate.di

import com.dipuguide.toolmate.data.repository.BarcodeRepositoryImpl
import com.dipuguide.toolmate.domain.repository.BarcodeRepository
import com.dipuguide.toolmate.presentation.screens.barcode.BarcodeScannerViewModel
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object BarcodeModule {


    @Singleton
    @Provides
    fun provideBarcodeScannerOptions(): BarcodeScannerOptions = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
        .build()

    @Singleton
    @Provides
    fun provideBarcodeScanner(): BarcodeScanner =
        BarcodeScanning.getClient(provideBarcodeScannerOptions())

    @Provides
    @Singleton
    fun provideBarcodeRepoIml(scanner: BarcodeScanner): BarcodeRepository  {
            return BarcodeRepositoryImpl(scanner = scanner)
    }

    @Provides
    @Singleton
    fun provideBarcodeRepo(barcodeRepository: BarcodeRepository)=
        BarcodeScannerViewModel(barcodeRepository)

}