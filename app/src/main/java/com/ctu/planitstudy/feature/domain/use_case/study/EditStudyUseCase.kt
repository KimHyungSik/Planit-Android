package com.ctu.planitstudy.feature.domain.use_case.study

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.domain.model.study.RepeatedStudy
import com.ctu.planitstudy.feature.domain.model.study.Study
import com.ctu.planitstudy.feature.domain.repository.StudyRepository
import com.ctu.planitstudy.feature.domain.use_case.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EditStudyUseCase @Inject constructor(
    private val studyRepository: StudyRepository
) : BaseUseCase<Unit>() {

    operator fun invoke(
        studyGroupId: String,
        studyScheduleId: String,
        study: Study
    ): Flow<Resource<Unit>> =
        useCase { studyRepository.editStudy(studyGroupId, studyScheduleId, study) }

    operator fun invoke(
        studyGroupId: String,
        studyScheduleId: String,
        repeatedStudy: RepeatedStudy
    ): Flow<Resource<Unit>> =
        useCase { studyRepository.editStudy(studyGroupId, studyScheduleId, repeatedStudy) }
}
