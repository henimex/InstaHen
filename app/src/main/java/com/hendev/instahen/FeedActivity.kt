package com.hendev.instahen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_feed.*


class FeedActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    private lateinit var adaptor: FeedAdaptor
    var postList = ArrayList<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        verileriAl()

        val layoutManager = LinearLayoutManager(this)
        rv.layoutManager = layoutManager
        adaptor = FeedAdaptor(postList)
        rv.adapter = adaptor
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.fotograf_paylas) {
            startActivity(Intent(this, FotografPaylasmaActivity::class.java))
        } else if (item.itemId == R.id.cikis_yap) {
            auth.signOut()
            startActivity(Intent(this, KullaniciActivity::class.java))
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    //bu fonksiyonda unutulan sey view parametresi
    fun signOut(view: View) {
        auth.signOut()
        startActivity(Intent(this, KullaniciActivity::class.java))
        finish()
    }

    fun verileriAl() {
        database.collection("Post")
            .orderBy("tarih", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exp ->
                if (exp != null) {
                    Toast.makeText(this, exp.localizedMessage, Toast.LENGTH_LONG).show()
                } else {
                    if (snapshot != null && !snapshot.isEmpty) {
                        val icerikListesi = snapshot.documents
                        postList.clear()
                        for (i in icerikListesi) {
                            val gorselurl = i.get("gorselurl") as String
                            val kullanici = i.get("kullanici") as String
                            val yorum = i.get("yorum") as String

                            /**val tarih = i.get("tarih") as Timestamp
                            //SU TARİHİ ADAM GİBİ Bİ ALAMADIM AQ

                            println("FireBase TS v0: :  ${tarih.toString()}")
                            println("FireBase TS v1: :  ${tarih.toDate().time}")
                            println("FireBase TS v2: :  ${tarih}")
                             */
                            val indirilenPost = Post(kullanici, yorum, gorselurl)
                            postList.add(indirilenPost)
                        }
                        adaptor.notifyDataSetChanged()
                    }
                }
            }
    }
}