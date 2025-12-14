package com.example.learnapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.designsystem.theme.LearnAppTheme
import com.example.navigation.LearnAppNavigation
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    override fun onCreate(savedInstanceState : Bundle?) {
        (application as LearnApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        setContent {
            LearnAppTheme {
                LearnAppNavigation(
                    viewModelFactory = viewModelFactory, onExitApp = {
                        finishAffinity()
                    })
            }
        }
    }
}