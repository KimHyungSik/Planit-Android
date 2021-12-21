package com.ctu.planitstudy.feature.domain.use_case.study

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyListDto
import com.ctu.planitstudy.feature.domain.repository.StudyRepository
import com.ctu.planitstudy.feature.domain.use_case.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class GetStudyListUseCase @Inject constructor(
    private val studyRepository: StudyRepository
) : BaseUseCase<StudyListDto>() {

    operator fun invoke(date: String): Flow<Resource<StudyListDto>> = useCase { studyRepository.getStudyList(date) }
}
