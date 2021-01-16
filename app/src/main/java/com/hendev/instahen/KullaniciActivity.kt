package com.hendev.instahen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_kullanici.*

class KullaniciActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kullanici)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null){
            Toast.makeText(applicationContext,"Hosgeldin ${auth.currentUser!!.email.toString()}",Toast.LENGTH_LONG).show()
            startActivity(Intent(this, FeedActivity::class.java))
            finish()
        }

    }

    fun userLogin(x: View) {
        auth.signInWithEmailAndPassword(txtEmail.text.toString(), txtPw.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext,"Hosgeldin ${auth.currentUser!!.email.toString()}",Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, FeedActivity::class.java))
                    finish()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()
            }

    }

    fun userRegister(x: View) {
        val email = txtEmail.text.toString()
        val pw = txtPw.text.toString()

        auth.createUserWithEmailAndPassword(email, pw)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext,"${auth.currentUser!!.email.toString()} Adresi Kaydedildi.",Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, FeedActivity::class.java))
                    finish()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()
            }

    }
}