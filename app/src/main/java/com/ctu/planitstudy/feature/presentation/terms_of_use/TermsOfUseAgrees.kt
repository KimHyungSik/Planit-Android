package com.ctu.planitstudy.feature.presentation.terms_of_use

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TermsOfUseAgrees(
    val personalInformationAgree: Boolean,
    val marketingInformationAgree: Boolean
) : Parcelable
