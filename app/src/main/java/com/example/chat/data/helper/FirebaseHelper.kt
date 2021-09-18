package com.example.chat.data.helper

import com.example.chat.data.N
import com.example.chat.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class FirebaseHelper(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage
) {
    fun signUp(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun signIn(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun addUserToDb(
        email: String,
        username: String,
        byteArray: ByteArray,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val compressedPostRef = storage.reference.child("userImages/${UUID.randomUUID()}")
        val upLoadTask = compressedPostRef.putBytes(byteArray)
        upLoadTask.addOnSuccessListener {
            compressedPostRef.downloadUrl.addOnSuccessListener {
                val user = User(auth.currentUser!!.uid, email, username, it.toString())
                db.document("${N.USERS}/${auth.currentUser!!.uid}").set(user)
                    .addOnSuccessListener {
                        onSuccess.invoke()
                    }
                    .addOnFailureListener { e ->
                        onFailure.invoke(e.localizedMessage)
                    }
            }
        }
    }

    fun getAllUsers(onSuccess: (user: List<User>) -> Unit, onFailure: (msg: String?) -> Unit) {
        db.collection(N.USERS).get()
            .addOnSuccessListener { doc ->
                val users = doc.documents.map {
                    it.toObject(User::class.java)!!
                }
                onSuccess.invoke(users)
            }
            .addOnFailureListener {
                onFailure.invoke(it.message)
            }
    }

}