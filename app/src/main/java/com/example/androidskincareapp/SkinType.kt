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
import androidx.appcompat.app.AppCompatActivity
import com.example.groupproject.R

class SkinType: AppCompatActivity() {

    lateinit var oilyButton:RadioButton
    lateinit var normalButton:RadioButton
    lateinit var combinationButton:RadioButton
    lateinit var dryButton:RadioButton
    lateinit var nextButton:Button
    lateinit var buttons: RadioGroup

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.quiz_skin_type)

         oilyButton = findViewById(R.id.oily)
         oilyButton.tooltipText = "Your skin often gets oily/shiny"

         normalButton = findViewById(R.id.normal)
         normalButton.tooltipText = "Your skin is neither oily or dry"

         combinationButton = findViewById(R.id.combination)
         combinationButton.tooltipText = "Your skin gets oily in certain spots and dry in others"

         dryButton = findViewById(R.id.dry)
         dryButton.tooltipText = "Your skin often gets dry"

         nextButton = findViewById(R.id.next)

        //Retrive the width and height from screen
        val width : Int = Resources.getSystem().displayMetrics.widthPixels
        val height : Int = Resources.getSystem().displayMetrics.heightPixels


         skin = PersonalSkin()



    }

    // goes to the next page if one of the options is checked
    fun goToNext(v: View){
        var intent: Intent = Intent(this, SkinConcernOne::class.java)
        if(oilyButton.isChecked || normalButton.isChecked || combinationButton.isChecked || dryButton.isChecked){
            checkType()
            this.startActivity(intent)
            overridePendingTransition( R.anim.slide_left, 0 )
        }
    }

    // goes to the previous page if one of the options is checked
    fun goToMain(v: View){
        var intent: Intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
        overridePendingTransition(R.anim.slide_left, 0)
    }

    fun progressSelect(v:View){
        var progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.progress = 1
    }

    fun checkType(){
        if(oilyButton.isChecked){
            skin.setSkinType("oily")
        } else if (normalButton.isChecked){
            skin.setSkinType("normal")
        } else if (combinationButton.isChecked){
            skin.setSkinType("combination")
        } else if(dryButton.isChecked){
            skin.setSkinType("dry")
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


    companion object {
        lateinit var skin: PersonalSkin
    }

    fun rightClicked(view: View) {}
    fun leftClicked(view: View) {}


}