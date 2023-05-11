package com.example.jecpackchallange

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.jecpackchallange.login.LoginScreen
import com.example.jecpackchallange.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel by viewModels<LoginViewModel>()

        setContent {
            LoginScreen(viewModel = viewModel)
        }

    }
}