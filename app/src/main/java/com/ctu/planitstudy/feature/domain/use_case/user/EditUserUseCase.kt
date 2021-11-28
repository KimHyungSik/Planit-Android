package com.ctu.planitstudy.feature.domain.use_case.user

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.data.remote.UserApi
import com.ctu.planitstudy.feature.data.remote.dto.timer.TimerMeasurementDto
import com.ctu.planitstudy.feature.data.remote.dto.util.MessageDto
import com.ctu.planitstudy.feature.domain.model.user.EditUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class EditUserUseCase @Inject constructor(
    val userApi: UserApi
) {

    operator fun invoke(editUser: EditUser): Flow<Resource<MessageDto>> = flow{
        try {
            emit(Resource.Loading<MessageDto>(null))
            val message = userApi.editUser(editUser)
            emit(Resource.Success(message))
        }catch (e: NullPointerException) {
            emit(Resource.Error<MessageDto>(message = "NullPointerException" + e.message))
        } catch (e: Exception) {
            emit(Resource.Error<MessageDto>(message = "Exception" + e.message))
            if (e is HttpException) {
                emit(
                    Resource.Error<MessageDto>(
                        message = JSONObject(
                            e.response()!!.errorBody()!!.string()
                        ).toString()
                    )
                )
            }
        } catch (e: Throwable) {
            emit(Resource.Error<MessageDto>(message = "Throwable" + e.message))
        }
    }
}