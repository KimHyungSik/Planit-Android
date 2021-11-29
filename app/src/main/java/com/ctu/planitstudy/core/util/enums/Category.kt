package com.ctu.planitstudy.core.util.enums

import com.ctu.planitstudy.R

interface CategoryEnums {
    val category: String
    val signItem: Int
    val editUser: Int
}

enum class Category : CategoryEnums {
    ELEMENTARY_SCHOOL {
        override val category: String
            get() = "ELEMENTARY_SCHOOL"
        override val signItem: Int
            get() = R.id.sign_up_category_radio_elementary_school
        override val editUser: Int
            get() = R.id.edit_user_category_radio_elementary_school
    },
    HIGH_SCHOOL {
        override val category: String
            get() = "HIGH_SCHOOL"
        override val signItem: Int
            get() = R.id.sign_up_category_radio_high_school
        override val editUser: Int
            get() = R.id.edit_user_category_radio_high_school
    },
    UNIVERSITY {
        override val category: String
            get() = "UNIVERSITY"
        override val signItem: Int
            get() = R.id.sign_up_category_radio_college
        override val editUser: Int
            get() = R.id.edit_user_category_radio_college
    },
    JOB_PREP {
        override val category: String
            get() = "JOB_PREP"
        override val signItem: Int
            get() = R.id.sign_up_category_radio_job_seeker
        override val editUser: Int
            get() = R.id.edit_user_category_radio_job_seeker
    },
    MIDDLE_SCHOOL {
        override val category: String
            get() = "MIDDLE_SCHOOL"
        override val signItem: Int
            get() = R.id.sign_up_category_radio_middle_school
        override val editUser: Int
            get() = R.id.edit_user_category_radio_middle_school
    },
    NTH_EXAM {
        override val category: String
            get() = "NTH_EXAM"
        override val signItem: Int
            get() = R.id.sign_up_category_radio_repeater
        override val editUser: Int
            get() = R.id.edit_user_category_radio_repeater
    },
    EXAM_PREP {
        override val category: String
            get() = "EXAM_PREP"
        override val signItem: Int
            get() = R.id.sign_up_category_radio_civil_service_exam
        override val editUser: Int
            get() = R.id.edit_user_category_radio_civil_service_exam
    },
    WORKER {
        override val category: String
            get() = "WORKER"
        override val signItem: Int
            get() = R.id.sign_up_category_radio_workers
        override val editUser: Int
            get() = R.id.edit_user_category_radio_workers
    }
}

fun findCategoryFromString(category: String): Category? {
    var find: Category? = null
    for (n in Category.values()) {
        if (category == n.category) {
            find = n
            break
        }
    }
    return find
}

fun findCategoryFromView(category: Int): Category? {
    var find: Category? = null
    for (n in Category.values()) {
        if (category == n.editUser) {
            find = n
            break
        }
        if (category == n.signItem) {
            find = n
            break
        }
    }
    return find
}
