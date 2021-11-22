package com.ctu.planitstudy.feature.domain.use_case.study

import android.util.Log
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
    val TAG = "GetStudyUseCase - 로그"
    
    operator fun invoke(date: String): Flow<Resource<StudyListDto>> = flow {
        try {
            emit(Resource.Loading<StudyListDto>(null))
            val studyList =
                JsonConverter.jsonToStudyListDto(studyRepository.getStudyList(date).asJsonObject)
            emit(Resource.Success(studyList))
        } catch (e: Exception) {
            emit( Resource.Error<StudyListDto>(message = "Exception" + e.message))
            if (e is HttpException) {
                emit(
                    Resource.Error<StudyListDto>(
                        message = JSONObject(
                            e.response()!!.errorBody()!!.string()
                        ).toString()
                    )
                )
            }
        } catch (e: Throwable){
            emit( Resource.Error<StudyListDto>(message = "Throwable" + e.message))
        }
    }
}