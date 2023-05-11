package com.example.jecpackchallange

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import com.example.jecpackchallange.login.LoginScreen
import com.example.jecpackchallange.login.LoginViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = LoginViewModel()

        setContent {
            LoginScreen(viewModel = viewModel)
        }

    }
}