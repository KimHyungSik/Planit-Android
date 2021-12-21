package com.ctu.planitstudy.feature.domain.use_case.study

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyTimeLineDto
import com.ctu.planitstudy.feature.domain.repository.StudyRepository
import com.ctu.planitstudy.feature.domain.use_case.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class GetStudyTimeLineUseCase @Inject constructor(
    private val studyRepository: StudyRepository
) : BaseUseCase<StudyTimeLineDto>() {
    operator fun invoke(date: String): Flow<Resource<StudyTimeLineDto>> = useCase { studyRepository.getStudyTimeLine(date) }
}
