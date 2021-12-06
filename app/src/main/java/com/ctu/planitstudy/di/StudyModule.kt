package com.ctu.planitstudy.di

import com.ctu.planitstudy.feature.data.remote.StudyApi
import com.ctu.planitstudy.feature.data.repository.StudyRepositoryImp
import com.ctu.planitstudy.feature.domain.repository.StudyRepository
import com.ctu.planitstudy.feature.domain.use_case.study.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object StudyModule {

    @Provides
    fun providerStudyApi(retrofit: Retrofit): StudyApi =
        retrofit
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
    fun providerEditStudyIsDoneUseCase(studyRepository: StudyRepository): EditStudyIsDoneUseCase =
        EditStudyIsDoneUseCase(studyRepository)

    @Provides
    fun providerDeleteStudyUseCase(studyRepository: StudyRepository): DeleteStudyUseCase =
        DeleteStudyUseCase(studyRepository)

    @Provides
    fun providerStudyUseCase(
        addStudyUseCase: AddStudyUseCase,
        getStudyListUseCase: GetStudyListUseCase,
        validatedTitleUseCase: StudyValidatedTitleUseCase,
        editStudyUseCase: EditStudyUseCase,
        deleteStudyUseCase: DeleteStudyUseCase,
        editStudyIsDoneUseCase: EditStudyIsDoneUseCase
    ): StudyUseCase = StudyUseCase(
        addStudyUseCase = addStudyUseCase,
        getStudyListUseCase = getStudyListUseCase,
        studyValidatedTitleUseCase = validatedTitleUseCase,
        editStudyUseCase = editStudyUseCase,
        deleteStudyUseCase = deleteStudyUseCase,
        editStudyIsDoneUseCase = editStudyIsDoneUseCase
    )
}
