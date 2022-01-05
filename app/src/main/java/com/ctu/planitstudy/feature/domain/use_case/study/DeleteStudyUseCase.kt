package com.ctu.planitstudy.feature.domain.use_case.study

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.domain.repository.StudyRepository
import com.ctu.planitstudy.feature.domain.use_case.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteStudyUseCase @Inject constructor(
    val studyRepository: StudyRepository
) : BaseUseCase<Unit>() {

    operator fun invoke(studyGroupId: String): Flow<Resource<Unit>> = useCase { studyRepository.deleteStudy(studyGroupId) }
}
