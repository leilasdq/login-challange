package com.example.jecpackchallange

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.jecpackchallange.register.LoginScreen
import com.example.jecpackchallange.register.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel by viewModels<RegisterViewModel>()

        setContent {
            LoginScreen(viewModel = viewModel)
        }

    }
}