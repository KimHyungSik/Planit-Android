package com.ctu.planitstudy.feature.domain.use_case.study

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.domain.model.study.RepeatedStudy
import com.ctu.planitstudy.feature.domain.model.study.Study
import com.ctu.planitstudy.feature.domain.repository.StudyRepository
import com.ctu.planitstudy.feature.domain.use_case.BaseUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class AddStudyUseCase @Inject constructor(
    private val studyRepository: StudyRepository
) : BaseUseCase<Unit>() {
    operator fun invoke(study: Study): Flow<Resource<Unit>> = useCase { studyRepository.addStudy(study) }

    operator fun invoke(repeatedStudy: RepeatedStudy): Flow<Resource<Unit>> = useCase { studyRepository.addStudy(repeatedStudy) }
}
