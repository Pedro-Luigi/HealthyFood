package com.udemy.healthyfood.helper

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class SettingsFirebase {

    private var referenciaFirebase:DatabaseReference? = null
    private var referenciaAutenticacao:FirebaseAuth? = null
    private var referenciaStorage:StorageReference? = null

    fun getIdUser():String{
        val autenticacao = getFirebaseAuth()
        return autenticacao.currentUser!!.uid
    }

    //retorna a referencia do firebase
    fun getFirebase():DatabaseReference{
        referenciaFirebase = FirebaseDatabase.getInstance().reference
        return referenciaFirebase as DatabaseReference
    }

    //retorna a instancia do firebase Auth
    fun getFirebaseAuth():FirebaseAuth{
        referenciaAutenticacao = FirebaseAuth.getInstance()
        return referenciaAutenticacao as FirebaseAuth
    }

    //retorna a instancia do firebaseStorage
    fun getFirebaseStorage():StorageReference{
        referenciaStorage = FirebaseStorage.getInstance().reference
        return referenciaStorage as StorageReference
    }
}