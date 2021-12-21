package com.ctu.planitstudy.feature.data.repository

import com.ctu.planitstudy.feature.data.remote.dto.study.Report
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyDto
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyListDto
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyTimeLineDto
import com.ctu.planitstudy.feature.domain.model.study.RepeatedStudy
import com.ctu.planitstudy.feature.domain.model.study.Study
import com.ctu.planitstudy.feature.domain.repository.StudyRepository
import retrofit2.Response

class FakeStudyRepository : StudyRepository {

    val studies = mutableListOf<Study>()
    val repeatedStudies = mutableListOf<RepeatedStudy>()
    var fakeStudyDtoList = mutableListOf<StudyDto>()
    val report = mutableListOf<Report>()

    override suspend fun addStudy(study: Study) {
        studies.add(study)
    }

    override suspend fun addStudy(repeatedStudy: RepeatedStudy) {
        repeatedStudies.add(repeatedStudy)
    }

    override suspend fun validateTitle(title: String) {
        for (study in studies) {
            if (study.title == title) return
        }

        for (study in repeatedStudies) {
            if (study.title == title) return
        }
    }

    override suspend fun getStudyList(date: String): StudyListDto {
        val studyDtoList = mutableListOf<StudyDto>()
        studies.forEachIndexed { index, study ->
            studyDtoList.add(
                StudyDto(
                    endAt = study.startAt,
                    isDone = false,
                    recordedTime = 0,
                    repeatedDays = null,
                    rest = 0,
                    startAt = study.startAt,
                    studyGroupId = index,
                    studyId = index,
                    studyScheduleId = index,
                    title = study.title
                )
            )
        }
        repeatedStudies.forEachIndexed { index, study ->
            studyDtoList.add(
                StudyDto(
                    endAt = study.endAt,
                    isDone = false,
                    recordedTime = 0,
                    repeatedDays = study.repeatedDays,
                    rest = 0,
                    startAt = study.startAt,
                    studyGroupId = index + studies.size,
                    studyId = index + studies.size,
                    studyScheduleId = index + studies.size,
                    title = study.title
                )
            )
        }
        fakeStudyDtoList = studyDtoList

        return StudyListDto(
            "Test",
            fakeStudyDtoList
        )
    }

    override suspend fun getStudyTimeLine(date: String): StudyTimeLineDto {
        TODO("Not yet implemented")
    }

    override suspend fun editStudy(studyGroupId: String, studyScheduleId: String, study: Study) {
        fakeStudyDtoList[studyGroupId.toInt()] = StudyDto(
            endAt = study.startAt,
            isDone = false,
            recordedTime = 0,
            repeatedDays = null,
            rest = 0,
            startAt = study.startAt,
            studyGroupId = studyGroupId.toInt(),
            studyId = 0,
            studyScheduleId = studyScheduleId.toInt(),
            title = study.title
        )
    }

    override suspend fun editStudy(
        studyGroupId: String,
        studyScheduleId: String,
        repeatedStudy: RepeatedStudy
    ) {
        fakeStudyDtoList[studyGroupId.toInt()] = StudyDto(
            endAt = repeatedStudy.startAt,
            isDone = false,
            recordedTime = 0,
            repeatedDays = repeatedStudy.repeatedDays,
            rest = 0,
            startAt = repeatedStudy.startAt,
            studyGroupId = studyGroupId.toInt(),
            studyId = 0,
            studyScheduleId = studyScheduleId.toInt(),
            title = repeatedStudy.title
        )
    }

    override suspend fun editStudyIsDone(studyId: String, isDone: Boolean) {
        fakeStudyDtoList[studyId.toInt()] = fakeStudyDtoList[studyId.toInt()].copy(
            isDone = isDone
        )
    }

    override suspend fun deleteStudy(studyGroupId: String): Response<Unit> {
        fakeStudyDtoList.removeAt(studyGroupId.toInt())
        return Response.success(null)
    }
}
