package com.ctu.planitstudy.feature.domain.use_case.study

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayListDto
import com.ctu.planitstudy.feature.data.remote.dto.JsonConverter
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyListDto
import com.ctu.planitstudy.feature.domain.repository.StudyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class GetStudyListUseCase @Inject constructor(
    private val studyRepository: StudyRepository
) {
    operator fun invoke(date: String): Flow<Resource<StudyListDto>> = flow {
        try {
            emit(Resource.Loading<StudyListDto>(null))
            val studyList =
                JsonConverter.jsonToStudyListDto(studyRepository.getStudyList(date).asJsonObject)
            emit(Resource.Success(studyList))
        } catch (e: Throwable) {
            if (e is HttpException) {
                emit(
                    Resource.Error<StudyListDto>(
                        message = JSONObject(
                            e.response()!!.errorBody()!!.string()
                        ).toString()
                    )
                )
            }
        }
    }
}