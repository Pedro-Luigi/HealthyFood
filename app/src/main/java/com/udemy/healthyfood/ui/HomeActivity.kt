package com.udemy.healthyfood.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.udemy.healthyfood.databinding.ActivityHomeBinding
import com.udemy.healthyfood.helper.SettingsFirebase

class HomeActivity : AppCompatActivity() {

    private val bindingHome by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private var autenticacao: FirebaseAuth = SettingsFirebase().getFirebaseAuth()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindingHome.root)

        bindingHome.btnGoOut.setOnClickListener {
            autenticacao.signOut()
            finish()
        }


    }
}