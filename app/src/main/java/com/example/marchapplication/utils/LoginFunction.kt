package com.example.marchapplication.utils

import com.google.firebase.auth.FirebaseAuth

val auth = FirebaseAuth.getInstance()

fun createAccount(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onError(task.exception?.message ?: "Đăng ký thất bại")
            }
        }
}
fun signIn(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onError(task.exception?.message ?: "Đăng nhập thất bại")
            }
        }
}

