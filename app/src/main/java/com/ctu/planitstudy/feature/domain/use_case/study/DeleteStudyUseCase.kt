package com.ctu.planitstudy.feature.domain.use_case.study

import android.util.Log
import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.domain.repository.StudyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class DeleteStudyUseCase @Inject constructor(
    val studyRepository: StudyRepository
) {
    val TAG = "DeleteStudyUseCase - 로그"
    
    operator fun invoke(studyGroupId: String): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading<Boolean>(null))
            studyRepository.deleteStudy(studyGroupId)
            emit(Resource.Success(true))
        } catch (e: HttpException) {
            emit(
                Resource.Error<Boolean>(
                    message = e.localizedMessage
                )
            )
        }
    }
}
