package com.example.anew

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

public class Firestore_db {
    private val db = Firebase.firestore
    private val usersRef = db.collection("users")

    public fun setUserTodb(
        user: User,
        id: String
    ): String {
        var result = ""
        usersRef.document(id).set(user).addOnSuccessListener {
            result = "success"
        }.addOnFailureListener {
            result = "faillure"
        }
        return result
    }
data class Userdd(
     var Name: String? = "",

            val Phone: String = "",

                    val Niveau: String = "",

                            val Email: String = "",

                                    val Password: String? = ""
        )

    public fun getUserFromDb(userId: String) : Userdd {
       var use = Userdd()
            usersRef.document(userId).get()
                .addOnSuccessListener {
                    var name = it.get("Name") as String
                    use.Name = name
                }
                .addOnFailureListener { }
        return  use
    }


}