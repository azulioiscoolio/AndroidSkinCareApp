package com.example.groupproject

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.groupproject.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import java.io.InputStream
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

class MainActivity : AppCompatActivity() {
    private lateinit var adView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.welcome_screen)

        //Retrive the width and height from screen
        val width : Int = Resources.getSystem().displayMetrics.widthPixels
        val height : Int = Resources.getSystem().displayMetrics.heightPixels

        //Retreive views
        //var welcome: TextView = findViewById<TextView>(R.id.welcome)
        var description: TextView = findViewById<TextView>(R.id.description)
        var start: TextView = findViewById<TextView>(R.id.startButton)

        //Set the padding dynamically so it looks good on all devices.
        //welcome.setPadding(0,height/10,0,0)
        description.setPadding(width/10,height/10,width/10,height/10)
        start.setPadding(width/12,0,width/12,0)

        createAd()

        // Parse the skin product files
    }

    fun createAd() {
        adView = AdView( this )
        var adSize : AdSize = AdSize( AdSize.FULL_WIDTH, AdSize.AUTO_HEIGHT )
        adView.setAdSize( adSize )

        var adUnitId : String = // "ca-app-pub-3940256099942544/1033173712"
            "ca-app-pub-3940256099942544/6300978111"
        adView.adUnitId = adUnitId

        var builder : AdRequest.Builder = AdRequest.Builder( )
        builder.addKeyword( "skincare" ).addKeyword( "cleanser" ).addKeyword("moisturizer")
        var request : AdRequest = builder.build()

        // add adView to linear layout
        var layout : LinearLayout = findViewById( R.id.ad_view )
        layout.addView( adView )

        // load the ad
        adView.loadAd( request )
    }

    private fun getPreferences(): Boolean{
        var bool: Boolean = false
        var pref = this.getSharedPreferences( this.packageName + "_preferences", Context.MODE_PRIVATE )
        return pref.getBoolean("Took Quiz", bool )
    }

    override fun onStart(){
        super.onStart()
        Log.w("MainActivity", "inside MainActivity:onStart")
    }
    override fun onRestart() {
        super.onRestart()
        Log.w("MainActivity", "inside MainActivity:onReStart")
    }
    override fun onPause() {
        super.onPause()
        if( adView != null )
            adView.pause()
        Log.w("MainActivity", "inside MainActivity:onPause")
    }
    override fun onStop() {
        super.onStop()
        Log.w("MainActivity", "inside MainActivity:onStop")
    }
    override fun onResume() {
        super.onResume()
        if( adView != null )
            adView.resume()
        Log.w("MainActivity", "inside MainActivity:onResume")
        // updateView()
    }
    override fun onDestroy() {
        if( adView != null )
            adView.destroy()
        super.onDestroy()
        Log.w("MainActivity", "inside MainActivity:onDestroy")
    }

    // Changes the pages to the first page of the quiz when the button is clicked
    fun goToQuiz(v: View){
        var intent: Intent

        if(getPreferences()){
           intent = Intent (this, TakenQuiz::class.java)
        } else {
            intent = Intent (this, SkinType::class.java)

        }

        this.startActivity(intent)
        overridePendingTransition( R.anim.slide_left, 0 )
    }
}