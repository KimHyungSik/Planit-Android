package com.ctu.planitstudy.feature.domain.use_case.study

data class StudyUseCase(
    val addStudyUseCase: AddStudyUseCase,
    val studyValidatedTitleUseCase: StudyValidatedTitleUseCase,
    val editStudyUseCase: EditStudyUseCase,
    val getStudyListUseCase: GetStudyListUseCase,
    val deleteStudyUseCase: DeleteStudyUseCase,
    val editStudyIsDoneUseCase: EditStudyIsDoneUseCase
)
