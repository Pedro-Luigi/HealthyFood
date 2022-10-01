package com.udemy.healthyfood.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isEmpty
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.udemy.healthyfood.databinding.ActivityAuthenticationBinding
import com.udemy.healthyfood.helper.SettingsFirebase

class AuthenticationActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAuthenticationBinding.inflate(layoutInflater) }
    private var autenticacao: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        login()
        register()
        autenticacao = SettingsFirebase().getFirebaseAuth()

        //verificar usuario logado
        checkingLogin()

    }

    private fun checkingLogin() {
        val usuarioAtual: FirebaseUser? = autenticacao?.currentUser
        if (usuarioAtual != null){
            startHome()
        }
    }

    fun login(){
        binding.btnSignIn.setOnClickListener {
            if(binding.tilEmail.editText!!.text.isEmpty() || binding.tillPass.editText!!.text.isEmpty()){
                Snackbar.make(it,"Preencha os campos vazios", Snackbar.LENGTH_SHORT).show()
            } else {
                autenticacao?.signInWithEmailAndPassword(
                    binding.tilEmail.editText?.text.toString(),
                    binding.tillPass.editText?.text.toString()
                )?.addOnCompleteListener{
                    if (it.isSuccessful){
                        Toast.makeText(this, "Logado com sucesso!", Toast.LENGTH_SHORT).show()
                        startHome()
                    } else {
                        Toast.makeText(this, "Erro ao fazer login: ${it.exception}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun register(){
        binding.btnRegister.setOnClickListener { it ->
            if(binding.tilEmail.isEmpty() || binding.tillPass.isEmpty()){
                Snackbar.make(it,"Preencha os campos vazios", Snackbar.LENGTH_SHORT).show()
            } else {
                autenticacao?.createUserWithEmailAndPassword(
                    binding.tilEmail.editText?.text.toString(),
                    binding.tillPass.editText?.text.toString()
                )!!.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                        startHome()
                    } else {
                        val erroMessage:String
                        try {
                            throw it.exception!!
                        } catch (e:FirebaseAuthWeakPasswordException){
                            erroMessage = "Digite uma senha mais forte!"
                        } catch (e:FirebaseAuthInvalidCredentialsException){
                            erroMessage = "Por favor, digite um e-mail válido"
                        } catch (e:FirebaseAuthUserCollisionException){
                            erroMessage = "Essa conta já foi cadastrada"
                        } catch (e:Exception){
                            erroMessage = "ao cadastrar usuário: " + e.message
                            e.printStackTrace()
                        }
                        Toast.makeText(this, "Erro: $erroMessage", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun startHome() {
        startActivity(Intent(this, HomeActivity::class.java))
    }

}