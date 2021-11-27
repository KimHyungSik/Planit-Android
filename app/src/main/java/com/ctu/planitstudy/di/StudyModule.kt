package com.ctu.planitstudy.di

import com.ctu.planitstudy.core.util.CoreData
import com.ctu.planitstudy.core.util.network.NullOnEmptyConverterFactory
import com.ctu.planitstudy.feature.data.remote.StudyApi
import com.ctu.planitstudy.feature.data.repository.StudyRepositoryImp
import com.ctu.planitstudy.feature.domain.repository.StudyRepository
import com.ctu.planitstudy.feature.domain.use_case.study.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object StudyModule {

    @Provides
    fun providerStudyApi(okHttpClient: OkHttpClient): StudyApi =
        Retrofit.Builder()
            .baseUrl(CoreData.BASE_SERVER_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory().nullOnEmptyConverterFactory)
            .build()
            .create(StudyApi::class.java)

    @Provides
    fun providerStudyRepository(studyApi: StudyApi): StudyRepository =
        StudyRepositoryImp(studyApi)

    @Provides
    fun providerStudyValidatedTitleUseCase(studyRepository: StudyRepository): StudyValidatedTitleUseCase =
        StudyValidatedTitleUseCase(studyRepository)

    @Provides
    fun providerAddStudyUseCase(studyRepository: StudyRepository): AddStudyUseCase =
        AddStudyUseCase(studyRepository)

    @Provides
    fun providerGetListStudyUseCase(studyRepository: StudyRepository): GetStudyListUseCase =
        GetStudyListUseCase(studyRepository)

    @Provides
    fun providerEditStudyUseCase(studyRepository: StudyRepository): EditStudyUseCase =
        EditStudyUseCase(studyRepository)

    @Provides
    fun providerDeleteStudyUseCase(studyRepository: StudyRepository): DeleteStudyUseCase =
        DeleteStudyUseCase(studyRepository)

    @Provides
    fun providerStudyUseCase(
        addStudyUseCase: AddStudyUseCase,
        getStudyListUseCase: GetStudyListUseCase,
        validatedTitleUseCase: StudyValidatedTitleUseCase,
        editStudyUseCase: EditStudyUseCase,
        deleteStudyUseCase: DeleteStudyUseCase
    ): StudyUseCase = StudyUseCase(
        addStudyUseCase = addStudyUseCase,
        getStudyListUseCase = getStudyListUseCase,
        studyValidatedTitleUseCase = validatedTitleUseCase,
        editStudyUseCase = editStudyUseCase,
        deleteStudyUseCase = deleteStudyUseCase
    )
}
