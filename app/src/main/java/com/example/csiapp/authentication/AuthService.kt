package com.hackathon.chegg.services.authentication

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.csiapp.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class AuthService(val auth: FirebaseAuth, val firestore: FirebaseFirestore) {


    fun saveUserDetails(email: String, name: String, id: String) {
        val details = hashMapOf("name" to name, "email" to email)
        firestore.collection("Students").document("Authentication").collection("Student data")
            .document(id).set(details).addOnSuccessListener {
                Log.d("UserDetails","User details saved successfully")
        }


    }


    fun signUpUser(email: String, password: String,name:String, context: Context) {

        try {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Register", "createUserWithEmail:success")
                    //  val user = auth.currentUser
                    val intent = Intent(context, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

                    saveUserDetails(email,name,auth.currentUser!!.uid)
                    startActivity(context, intent, null)


                } else {
                    Log.w("registerFail", "createUserWithEmail:failure", task.exception)

                }
            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
        }


    }


    fun signInUser(email: String, password: String, context: Context) {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("SignIn", "signInWithEmail:success")

                        val intent = Intent(context, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

                        startActivity(context, intent, null)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("SignInFail", task.exception?.message.toString())
                        Toast.makeText(
                            context, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
        } catch (e: Exception) {
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
        }

    }
}