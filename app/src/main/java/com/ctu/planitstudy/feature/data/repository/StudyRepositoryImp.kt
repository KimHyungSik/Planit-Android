package com.ctu.planitstudy.feature.data.repository

import com.ctu.planitstudy.feature.data.remote.StudyApi
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyListDto
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyTimeLineDto
import com.ctu.planitstudy.feature.domain.model.study.RepeatedStudy
import com.ctu.planitstudy.feature.domain.model.study.Study
import com.ctu.planitstudy.feature.domain.repository.StudyRepository
import retrofit2.Response
import javax.inject.Inject

class StudyRepositoryImp @Inject constructor(
    private val studyApi: StudyApi
) : StudyRepository {
    override suspend fun addStudy(study: Study) : Response<Unit> = studyApi.addStudy(study)
    override suspend fun addStudy(repeatedStudy: RepeatedStudy) : Response<Unit> = studyApi.addStudy(repeatedStudy)
    override suspend fun validateTitle(title: String) : Response<Unit> = studyApi.validateTitle(title)
    override suspend fun getStudyList(date: String): Response<StudyListDto> = studyApi.getStudyList(date)
    override suspend fun getStudyTimeLine(date: String): Response<StudyTimeLineDto> = studyApi.getStudyTimeLine(date)
    override suspend fun editStudy(studyGroupId: String, studyScheduleId: String, study: Study): Response<Unit> = studyApi.editStudy(studyGroupId, studyScheduleId, study)
    override suspend fun editStudy(studyGroupId: String, studyScheduleId: String, repeatedStudy: RepeatedStudy): Response<Unit> = studyApi.editStudy(studyGroupId, studyScheduleId, repeatedStudy)
    override suspend fun editStudyIsDone(studyId: String, isDone: Boolean): Response<Unit> = studyApi.editStudyIsDone(studyId, isDone)
    override suspend fun deleteStudy(studyGroupId: String): Response<Unit> = studyApi.deleteStudy(studyGroupId)
}
