package com.theoriginalcover.server.adapters.repositories

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient

open class FirebaseRepository {
    companion object {
        init {
            val serviceAccount = javaClass.classLoader.getResourceAsStream("google-key.json")
            val options = FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://theoriginalcover-47206.firebaseio.com")
                .build()
            FirebaseApp.initializeApp(options)
        }
    }

    fun getDatabase(): Firestore {
        return FirestoreClient.getFirestore()
    }
}
