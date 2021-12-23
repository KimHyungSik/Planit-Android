package com.ctu.planitstudy.feature.domain.use_case.reward

import app.cash.turbine.test
import com.ctu.planitstudy.feature.data.remote.dto.reward.PlanetDto
import com.ctu.planitstudy.feature.data.remote.dto.reward.RewardDto
import com.ctu.planitstudy.feature.data.repository.FakeRewardRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RewardUseCaseTest {
    lateinit var rewardUseCase: RewardUseCase
    private lateinit var rewardRepository: FakeRewardRepository

    private var reward = RewardDto(star = 150, point = 0, planetPass = 3)
    val planetDto = mutableListOf<PlanetDto>()

    @Before
    fun setUp() {
        rewardRepository = FakeRewardRepository()

        rewardUseCase = RewardUseCase(
            getRewardUseCase = GetRewardUseCase(rewardRepository),
            getPlanetPassListUseCase = GetPlanetPassListUseCase(rewardRepository),
            convertStarToPointUseCase = ConvertStarToPointUseCase(rewardRepository),
            convertPlanitPassToPointUseCase = ConvertPlanitPassToPointUseCase(rewardRepository)
        )
    }

    @Test
    fun `리워드 조회 테스트`() = runBlocking {
        rewardUseCase.getRewardUseCase().test {
            awaitItem()
            val result = awaitItem().data
            assertEquals(result, reward)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `리워드 별 전환 테스트`() = runBlocking {
        rewardUseCase.convertStarToPointUseCase().test {
            awaitItem()
            val result = awaitItem().data
            assertEquals(result, reward.copy(star = reward.star - 50, point = reward.point + 5))
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `리워드 포인트 전환 테스트`() = runBlocking {
        rewardUseCase.convertPlanitPassToPointUseCase(1).test {
            awaitItem()
            val result = awaitItem().data
            assertEquals(result, reward.copy(planetPass = reward.planetPass - 1))
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `플래닛 패스 리스트 조회 테스트`() = runBlocking {
        rewardUseCase.getPlanetPassListUseCase().test {
            awaitItem()
            val result = awaitItem().data
            assertEquals(result!!.planets, planetDto)
            cancelAndConsumeRemainingEvents()
        }
    }
}
