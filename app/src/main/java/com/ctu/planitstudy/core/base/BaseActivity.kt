package com.ctu.planitstudy.core.base

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.ctu.planitstudy.feature.presentation.util.Screens

open class BaseActivity :
    AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val requestActivity: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult() // StartActivityForResult 처리를 담당
    ) { activityResult -> resultActivity(activityResult) }

    open fun moveIntent(activity: Class<*>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }

    open fun moveIntent(intent: Intent) {
        startActivity(intent)
    }

    open fun moveIntent(screen: Screens) {
        val intent = Intent(this, screen.activity)
        startActivity(intent)
    }

    // 리턴 값을 가지는 화면 이동
    open fun moveIntentResult(activity: Class<*>) {
        val intent = Intent(this, activity)
        requestActivity.launch(intent)
    }

    open fun moveIntentAllClear(activity: Class<*>) {
        val intent = Intent(this, activity)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
            Intent.FLAG_ACTIVITY_CLEAR_TASK or
            Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    open fun moveIntentAllClear(intent: Intent) {
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
            Intent.FLAG_ACTIVITY_CLEAR_TASK or
            Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    open fun showDialogFragment(arg: Bundle, fragment: DialogFragment) {
        fragment.arguments = arg
        fragment.show(supportFragmentManager, "dialog")
    }

    // 리턴 값을 가지고 반환된 액티비티 설정
    open fun resultActivity(activityResult: ActivityResult) {}
}
