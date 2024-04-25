package com.example.groupproject

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

class SkinConcernTwo: AppCompatActivity(){
    lateinit var darkSpot: RadioButton
    lateinit var redness: RadioButton
    lateinit var pores: RadioButton
    lateinit var dryness: RadioButton
    lateinit var acne: RadioButton
    lateinit var aging: RadioButton
    lateinit var nothing: RadioButton
    lateinit var viewResult: Button
    lateinit var skinConcern: TextView
    lateinit var radiogroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.skin_concern2)

        //Retrive the width and height from screen
        val width : Int = Resources.getSystem().displayMetrics.widthPixels
        val height : Int = Resources.getSystem().displayMetrics.heightPixels
        skinConcern = findViewById(R.id.skinConcern2)
        radiogroup = findViewById(R.id.radioButtons2)
        darkSpot = findViewById(R.id.darkSpots2)
        redness = findViewById(R.id.redness2)
        pores = findViewById(R.id.pores2)
        dryness = findViewById(R.id.dryness2)
        acne = findViewById(R.id.acne2)
        aging = findViewById(R.id.aging2)
        nothing = findViewById(R.id.nothing)


        // removes the button they already clicked
        val extras = intent.extras
        if (extras != null) {
            val problem = extras.getString("key")
            removeButton(problem)
        }

        // Set padding of layout dynamically
        skinConcern.setPadding(0,(height/15)*2,0,height/30)

        //progress bar
        var progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.progress = 2
    }

    fun goToResult(v: View){

        //if at least one button is checked
        if(radiogroup.getCheckedRadioButtonId() != -1){
            var progressBar = findViewById<ProgressBar>(R.id.progressBar)
            progressBar.progress = 2
        }

        var intent: Intent = Intent(this, Result::class.java)
        if(darkSpot.isChecked || redness.isChecked || pores.isChecked
            || dryness.isChecked || acne.isChecked || aging.isChecked || nothing.isChecked){

            SkinType.skin.setSecondProblem(checkProblem())
            var progressBar = findViewById<ProgressBar>(R.id.progressBar)
            progressBar.progress = 3
            this.startActivity(intent)
            overridePendingTransition( R.anim.fade_in_and_scale, 0 )
        }
    }

    fun goToConcernOne(v:View){
        var intent: Intent = Intent(this, SkinConcernOne::class.java)
        this.startActivity(intent)
        overridePendingTransition(R.anim.slide_left, 0)
    }

    fun progressSelect(v:View){
        var progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.progress = 3
    }


    // Checks to see what problem was input on the first problem page.
    fun removeButton(result :String?): Unit{
         if("dark spots" == result){
             darkSpot.visibility = View.GONE;
        } else if("redness"== result){
             redness.visibility = View.GONE;
        } else if ("pores" == result){
             pores.visibility = View.GONE;
        } else if ("dryness" == result){
             dryness.visibility = View.GONE;
        } else if ("acne" == result){
             acne.visibility = View.GONE;
        } else if ("aging" == result){
             aging.visibility = View.GONE;
        }
    }

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
        } else if (nothing.isChecked){
            "nothing"
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