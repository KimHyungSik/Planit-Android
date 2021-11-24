package com.ctu.planitstudy.di

import com.ctu.planitstudy.core.util.CoreData
import com.ctu.planitstudy.core.util.network.NullOnEmptyConverterFactory
import com.ctu.planitstudy.feature.data.remote.StudyApi
import com.ctu.planitstudy.feature.data.repository.StudyRepositoryImp
import com.ctu.planitstudy.feature.domain.repository.StudyRepository
import com.ctu.planitstudy.feature.domain.use_case.study.AddStudyUseCase
import com.ctu.planitstudy.feature.domain.use_case.study.GetStudyListUseCase
import com.ctu.planitstudy.feature.domain.use_case.study.StudyValidatedTitleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StudyModule {

    @Provides
    @Singleton
    fun providerStudyApi(okHttpClient: OkHttpClient): StudyApi =
        Retrofit.Builder()
            .baseUrl(CoreData.BASE_SERVER_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory().nullOnEmptyConverterFactory)
            .build()
            .create(StudyApi::class.java)

    @Provides
    @Singleton
    fun providerStudyRepository(studyApi: StudyApi): StudyRepository =
        StudyRepositoryImp(studyApi)

    @Provides
    @Singleton
    fun providerStudyValidatedTitleUseCase(studyRepository: StudyRepository): StudyValidatedTitleUseCase =
        StudyValidatedTitleUseCase(studyRepository)

    @Provides
    @Singleton
    fun providerAddStudyUseCase(studyRepository: StudyRepository): AddStudyUseCase =
        AddStudyUseCase(studyRepository)

    @Provides
    @Singleton
    fun providerGetListStudyUseCase(studyRepository: StudyRepository): GetStudyListUseCase =
        GetStudyListUseCase(studyRepository)
}
