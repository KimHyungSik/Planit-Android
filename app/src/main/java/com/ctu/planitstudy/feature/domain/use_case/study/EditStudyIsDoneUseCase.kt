package com.ctu.planitstudy.feature.domain.use_case.study

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.domain.repository.StudyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class EditStudyIsDoneUseCase @Inject constructor(
    private val studyRepository: StudyRepository
) {
    operator fun invoke(studyId: String, isDone: Boolean): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading(false))
            studyRepository.editStudyIsDone(studyId, isDone)
            emit(Resource.Success(true))
        } catch (e: NullPointerException) {
            emit(Resource.Error<Boolean>(message = "NullPointerException" + e.message))
        } catch (e: Exception) {
            emit(Resource.Error<Boolean>(message = "Exception" + e.message))
            if (e is HttpException) {
                emit(
                    Resource.Error<Boolean>(
                        message = JSONObject(
                            e.response()!!.errorBody()!!.string()
                        ).toString()
                    )
                )
            }
        } catch (e: Throwable) {
            emit(Resource.Error<Boolean>(message = "Throwable" + e.message))
        }
    }
}
