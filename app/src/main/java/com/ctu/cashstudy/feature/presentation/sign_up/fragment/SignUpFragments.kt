package com.ctu.cashstudy.feature.presentation.sign_up.fragment

sealed class SignUpFragments(val page : Int){
    object Name : SignUpFragments(0)
    object Gender : SignUpFragments(1)
    object DateOfBirth : SignUpFragments(2)
    object Category : SignUpFragments(3)
}
