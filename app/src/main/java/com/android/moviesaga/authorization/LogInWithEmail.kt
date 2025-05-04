package com.example.moviesaga.authorization

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

fun LogInUser(
    auth: FirebaseAuth,
    email: String,
    password: String,
    context: Context,
    openMainScreen: () -> Unit
) {
    if (email.isNotBlank() && password.isNotBlank()) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null && user.isEmailVerified) {
                        Toast.makeText(context, "Log In Successfully", Toast.LENGTH_SHORT).show()
                        openMainScreen()
                    } else {
                        Toast.makeText(context, "Please verify your email first.", Toast.LENGTH_LONG).show()
                        auth.signOut()
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Log In Failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}

