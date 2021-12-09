package com.ctu.planitstudy.feature.domain.use_case.study

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyTimeLineDto
import com.ctu.planitstudy.feature.domain.repository.StudyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class GetStudyTimeLineUseCase @Inject constructor(
    private val studyRepository: StudyRepository
) {
    val TAG = "GetStudyUseCase - 로그"

    operator fun invoke(date: String): Flow<Resource<StudyTimeLineDto>> = flow {
        try {
            emit(Resource.Loading<StudyTimeLineDto>(null))
            val studyTimeLine = studyRepository.getStudyTimeLine(date)
            emit(Resource.Success(studyTimeLine))
        } catch (e: NullPointerException) {
            emit(Resource.Error<StudyTimeLineDto>(message = "NullPointerException" + e.message))
        } catch (e: Exception) {
            emit(Resource.Error<StudyTimeLineDto>(message = "Exception" + e.message))
            if (e is HttpException) {
                emit(
                    Resource.Error<StudyTimeLineDto>(
                        message = JSONObject(
                            e.response()!!.errorBody()!!.string()
                        ).toString()
                    )
                )
            }
        } catch (e: Throwable) {
            emit(Resource.Error<StudyTimeLineDto>(message = "Throwable" + e.message))
        }
    }
}
