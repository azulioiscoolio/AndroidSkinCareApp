package com.example.groupproject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.groupproject.R

class TakenQuiz: AppCompatActivity(){

    lateinit var results: Button
    lateinit var quiz: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.took_quiz)

    }

    fun goToQuiz(v: View){
        var pref = this.getSharedPreferences( this.packageName + "_preferences", Context.MODE_PRIVATE )

        var editor: SharedPreferences.Editor = pref.edit()
        editor.putBoolean("Took Quiz", false)
        editor.commit()


        var intent: Intent = Intent(this, SkinType::class.java)
        this.startActivity(intent)
        overridePendingTransition( R.anim.slide_left, 0 )

    }

    fun goToResults(v: View){
        var intent: Intent = Intent(this, Result::class.java)
        this.startActivity(intent)
        overridePendingTransition( R.anim.fade_in_and_scale, 0 )
    }




}