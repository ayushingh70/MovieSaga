package com.example.moviesaga.authorization

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

fun SignUpUser(
    databaseReference: DatabaseReference,
    auth: FirebaseAuth,
    username: String,
    email: String,
    password: String,
    context: Context,
    signSuccessGoToLoginScreen: () -> Unit
) {

    if (email.isNotBlank() && password.isNotBlank()) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Send Email Verification
                    val user = auth.currentUser
                    user?.sendEmailVerification()?.addOnCompleteListener { emailTask ->
                        if (emailTask.isSuccessful) {
                            // Save User Data to Firebase Database
                            databaseReference.child("users").child(auth.currentUser!!.uid)
                                .child("username").setValue(username)

                            // Show Email Verification Message
                            Toast.makeText(context, "Sign Up Successful! Please verify your email.", Toast.LENGTH_LONG).show()

                            // Redirect to Login Screen
                            signSuccessGoToLoginScreen()
                        } else {
                            Toast.makeText(
                                context,
                                "Failed to send verification email. ${emailTask.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                } else {
                    // Handle Sign Up Failure
                    Toast.makeText(
                        context,
                        "Sign Up Failed: ${task.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}
