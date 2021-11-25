package com.ctu.planitstudy.feature.domain.use_case.study

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.domain.model.study.AddRepeatedStudy
import com.ctu.planitstudy.feature.domain.model.study.AddStudy
import com.ctu.planitstudy.feature.domain.repository.StudyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class AddStudyUseCase @Inject constructor(
    private val studyRepository: StudyRepository
) {
    operator fun invoke(addStudy: AddStudy): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading<Boolean>(null))
            studyRepository.addStudy(addStudy)
            emit(Resource.Success(true))
        } catch (e: HttpException) {
            emit(
                Resource.Error<Boolean>(
                    message = e.localizedMessage
                )
            )
        } catch (e: Throwable) {
            emit(
                Resource.Error<Boolean>(
                    message = e.message!!
                )
            )
        }
    }

    operator fun invoke(addRepeatedStudy: AddRepeatedStudy): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading<Boolean>(null))
            studyRepository.addStudy(addRepeatedStudy)
            emit(Resource.Success(true))
        } catch (e: HttpException) {
            emit(
                Resource.Error<Boolean>(
                    message = e.localizedMessage
                )
            )
        } catch (e: Throwable) {
            emit(
                Resource.Error<Boolean>(
                    message = e.message!!
                )
            )
        }
    }
}
