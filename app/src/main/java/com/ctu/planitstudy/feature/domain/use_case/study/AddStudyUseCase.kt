package com.ctu.planitstudy.feature.domain.use_case.study

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.domain.model.study.RepeatedStudy
import com.ctu.planitstudy.feature.domain.model.study.Study
import com.ctu.planitstudy.feature.domain.repository.StudyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class AddStudyUseCase @Inject constructor(
    private val studyRepository: StudyRepository
) {
    operator fun invoke(study: Study): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading<Boolean>(null))
            studyRepository.addStudy(study)
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

    operator fun invoke(repeatedStudy: RepeatedStudy): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading<Boolean>(null))
            studyRepository.addStudy(repeatedStudy)
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
