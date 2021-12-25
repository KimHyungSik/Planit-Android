package com.ctu.planitstudy.feature.domain.use_case.user

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.data.remote.dto.util.MessageDto
import com.ctu.planitstudy.feature.domain.model.user.EditUser
import com.ctu.planitstudy.feature.domain.repository.UserRepository
import com.ctu.planitstudy.feature.domain.use_case.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class EditUserUseCase @Inject constructor(
    val userApiRepository: UserRepository
) : BaseUseCase<MessageDto>() {

    operator fun invoke(editUser: EditUser): Flow<Resource<MessageDto>> = useCase { userApiRepository.editUser(editUser) }
}
