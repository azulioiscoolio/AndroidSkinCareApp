package com.example.androidskincareapp

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.groupproject.R

class SkinConcernOne: AppCompatActivity() {

    lateinit var darkSpot:RadioButton
    lateinit var redness:RadioButton
    lateinit var pores:RadioButton
    lateinit var dryness:RadioButton
    lateinit var acne:RadioButton
    lateinit var aging:RadioButton
    lateinit var nextButton: Button
    lateinit var skinConcern: TextView
    lateinit var radiogroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.skin_concern1)

        //Retrive the width and height from screen
        val width : Int = Resources.getSystem().displayMetrics.widthPixels
        val height : Int = Resources.getSystem().displayMetrics.heightPixels


        skinConcern = findViewById(R.id.skinConcern1)
        radiogroup = findViewById(R.id.radioButtons)
        darkSpot = findViewById(R.id.darkSpots)
        redness = findViewById(R.id.redness)
        pores = findViewById(R.id.pores)
        dryness = findViewById(R.id.dryness)
        acne = findViewById(R.id.acne)
        aging = findViewById(R.id.aging)

        // Set padding of layout dynamically
        skinConcern.setPadding(0,(height/15)*2,0,height/30)

        // progress bar
        var progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.progress = 1
    }

    fun goToSecondQ(v: View) {

        //if at least one button is checked
        if(radiogroup.getCheckedRadioButtonId() != -1){
            var progressBar = findViewById<ProgressBar>(R.id.progressBar)
            progressBar.progress = 2
        }


        var intent: Intent = Intent(this, SkinConcernTwo::class.java)

        if (darkSpot.isChecked || redness.isChecked || pores.isChecked
            || dryness.isChecked || acne.isChecked || aging.isChecked
        ) {
            intent.putExtra("key", checkProblem())
            SkinType.skin.setFirstProblem(checkProblem())
            this.startActivity(intent)
            overridePendingTransition( R.anim.slide_left, 0 )
        }
    }

    fun goToSkin(v: View){
        var intent: Intent = Intent(this, SkinType::class.java)
        this.startActivity(intent)
        overridePendingTransition(R.anim.slide_left, 0)
    }

    fun progressSelect(v:View){
        var progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.progress = 2
    }

    // Checks to see what the problem is to pass it into the next activity.
    fun checkProblem(): String{
        return if(darkSpot.isChecked){
            "dark spots"
        } else if(redness.isChecked){
            "redness"
        } else if (pores.isChecked){
            "pores"
        } else if (dryness.isChecked){
            "dryness"
        } else if (acne.isChecked){
            "acne"
        } else if (aging.isChecked){
            "aging"
        } else {
            ""
        }
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
        Log.w("MainActivity", "inside MainActivity:onPause")
    }
    override fun onStop() {
        super.onStop()
        Log.w("MainActivity", "inside MainActivity:onStop")
    }
    override fun onResume() {
        super.onResume()
        Log.w("MainActivity", "inside MainActivity:onResume")
        // updateView()
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.w("MainActivity", "inside MainActivity:onDestroy")
    }
}