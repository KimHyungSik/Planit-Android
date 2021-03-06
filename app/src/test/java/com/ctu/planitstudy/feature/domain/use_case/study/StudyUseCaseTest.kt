package com.ctu.planitstudy.feature.domain.use_case.study

import app.cash.turbine.test
import com.ctu.planitstudy.feature.data.repository.FakeStudyRepository
import com.ctu.planitstudy.feature.domain.model.study.RepeatedStudy
import com.ctu.planitstudy.feature.domain.model.study.Study
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class StudyUseCaseTest {
    private lateinit var studyUseCase: StudyUseCase
    private lateinit var studyRepository: FakeStudyRepository

    private val study = mutableListOf<Study>()
    private val repeatedStudy = mutableListOf<RepeatedStudy>()

    @Before
    fun setUp() {
        studyRepository = FakeStudyRepository()
        studyUseCase = StudyUseCase(
            addStudyUseCase = AddStudyUseCase(studyRepository),
            studyValidatedTitleUseCase = StudyValidatedTitleUseCase(studyRepository),
            editStudyUseCase = EditStudyUseCase(studyRepository),
            getStudyListUseCase = GetStudyListUseCase(studyRepository),
            getStudyTimeLineUseCase = GetStudyTimeLineUseCase(studyRepository),
            deleteStudyUseCase = DeleteStudyUseCase(studyRepository),
            editStudyIsDoneUseCase = EditStudyIsDoneUseCase(studyRepository)
        )

        ('a'..'h').forEachIndexed { index, c ->
            study.add(
                Study(
                    title = c.toString(),
                    startAt = index.toString()
                )
            )
        }
        ('h'..'z').forEachIndexed { index, c ->
            repeatedStudy.add(
                RepeatedStudy(
                    title = c.toString(),
                    startAt = index.toString(),
                    endAt = (index + 1).toString(),
                    repeatedDays = arrayListOf<String>()
                )
            )
        }
        runBlocking {
            study.forEach {
                studyUseCase.addStudyUseCase(it).test { cancelAndConsumeRemainingEvents() }
            }
            repeatedStudy.forEach {
                studyUseCase.addStudyUseCase(it).test { cancelAndConsumeRemainingEvents() }
            }
        }
    }

    @Test
    fun `??????????????? ?????? ?????????`() = runBlocking {
        studyUseCase.getStudyListUseCase("0000-00-00").test {
            val loading = awaitItem()
            val result = awaitItem().data!!
            assertEquals(study.size + repeatedStudy.size, result.studies.size)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `?????? ?????? ?????????`() = runBlocking {
        val newStudy = Study(
            title = "??? ??????",
            startAt = "0000-00-00"
        )
        study.add(newStudy)

        studyUseCase.addStudyUseCase(newStudy).test { cancelAndConsumeRemainingEvents() }
        studyUseCase.getStudyListUseCase("0000-00-00").test {
            awaitItem()
            val result = awaitItem().data!!
            assertEquals(study.size + repeatedStudy.size, result.studies.size)
            cancelAndConsumeRemainingEvents()
        }
    }
}
