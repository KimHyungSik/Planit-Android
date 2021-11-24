package com.ctu.planitstudy.feature.domain.use_case.study

import com.ctu.planitstudy.feature.domain.repository.StudyRepository
import javax.inject.Inject

class StudyValidatedTitleUseCase @Inject constructor(
    private val studyRepository: StudyRepository
) {
    suspend operator fun invoke(title: String) = studyRepository.validateTitle(title)
}
