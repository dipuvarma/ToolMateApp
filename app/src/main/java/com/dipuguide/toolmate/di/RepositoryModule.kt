package com.dipuguide.toolmate.di

import com.dipuguide.toolmate.data.repository.BarcodeRepositoryImpl
import com.dipuguide.toolmate.data.repository.DocumentScannerRepoImpl
import com.dipuguide.toolmate.data.repository.PermissionRepositoryImpl
import com.dipuguide.toolmate.data.repository.SharedPreferenceRepositoryImpl
import com.dipuguide.toolmate.data.repository.ThemeRepositoryImpl
import com.dipuguide.toolmate.domain.repository.BarcodeRepository
import com.dipuguide.toolmate.domain.repository.DocumentScannerRepo
import com.dipuguide.toolmate.domain.repository.PermissionRepository
import com.dipuguide.toolmate.domain.repository.SharedPreferenceRepository
import com.dipuguide.toolmate.domain.repository.ThemeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindPermissionRepository(permissionRepositoryImpl: PermissionRepositoryImpl): PermissionRepository

    @Binds
    @Singleton
    abstract fun bindThemeRepository(themeRepositoryImpl: ThemeRepositoryImpl): ThemeRepository

    @Binds
    @Singleton
    abstract fun bindSharedPreferenceRepository(sharedPreferenceRepositoryImpl: SharedPreferenceRepositoryImpl): SharedPreferenceRepository

    @Binds
    @Singleton
    abstract fun bindBarcodeScannerRepository(barcodeRepositoryImpl: BarcodeRepositoryImpl): BarcodeRepository

    @Binds
    @Singleton
    abstract fun bindDocumentScannerRepo(documentScannerRepoImpl: DocumentScannerRepoImpl): DocumentScannerRepo
}
