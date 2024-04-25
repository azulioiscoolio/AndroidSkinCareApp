package com.example.androidskincareapp

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import java.io.InputStream
import java.lang.Exception
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

class Result: AppCompatActivity() {

    lateinit var set: HashSet<String>

    lateinit var productTitle : TextView
    lateinit var product1Image : ImageView
    lateinit var product1Name : TextView
    lateinit var learn1Button : Button
    lateinit var productRate1: RatingBar

    lateinit var product2Image : ImageView
    lateinit var product2Name : TextView
    lateinit var learn2Button : Button
    lateinit var productRate2: RatingBar

    lateinit var productNameBack: TextView
    lateinit var productImgBack: ImageView

    private var listSize: Int = 0
    private var currentIdx = 0
    private var learnIdx = 0

    lateinit var front_animation:AnimatorSet
    lateinit var back_animation: AnimatorSet
    lateinit var card_front: LinearLayout
    lateinit var card_back: LinearLayout
    var isFront = true

    lateinit var left_arrow: ImageButton
    lateinit var right_arrow: ImageButton
    lateinit var product_link_button:Button
    lateinit var review_submit: Button
    lateinit var hearts: RatingBar
    lateinit var review: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        var pref = this.getSharedPreferences( this.packageName + "_preferences", Context.MODE_PRIVATE )

        super.onCreate(savedInstanceState)
        set = HashSet<String>()

        if(pref.getBoolean("Took Quiz", false)){
            var skintype: String = pref.getString("SkinType", "")!!
            var firstproblem: String = pref.getString("SkinProblemOne", "")!!
            var secondproblem: String = pref.getString("SkinProblemTwo", "")!!

            SkinType.skin = PersonalSkin()

            SkinType.skin.setSkinType(skintype)
            SkinType.skin.setFirstProblem(firstproblem)
            SkinType.skin.setSecondProblem(secondproblem)
        }

        setPreferences()
        setContentView(R.layout.result)
        animateCard()

        //Retrive the width and height from screen
        val width: Int = Resources.getSystem().displayMetrics.widthPixels
        val height: Int = Resources.getSystem().displayMetrics.heightPixels

        sortParse()
        Log.w("Product List", SkinType.skin.getProducts().toString())
        listSize = SkinType.skin.getProducts().size

        updateResultPage(currentIdx)
    }


    fun animateCard(){
        card_front = findViewById(R.id.cardFront)
        card_back = findViewById(R.id.cardBack)

        productTitle = findViewById(R.id.product_title)
        product1Image = findViewById(R.id.product1_image)
        product1Name = findViewById(R.id.product1_name)
        learn1Button = findViewById(R.id.product1_learn)

        product2Image = findViewById(R.id.product2_image)
        product2Name = findViewById(R.id.product2_name)
        learn2Button = findViewById(R.id.product2_learn)

        left_arrow = findViewById(R.id.leftButton)
        right_arrow = findViewById(R.id.rightButton)
        product_link_button = findViewById(R.id.go_product_link)

        productNameBack = findViewById(R.id.product_name_back)
        productImgBack = findViewById(R.id.product_img_back)

        // Flipping card
        var scale = applicationContext.resources.displayMetrics.density

        val front : LinearLayout = findViewById(R.id.cardFront)
        val back : LinearLayout = findViewById(R.id.cardBack)
        val flip : ImageButton = findViewById(R.id.flip_card_button)

        front.cameraDistance = 8000 * scale
        back.cameraDistance = 8000 * scale

        // Setting the front animation
        front_animation = AnimatorInflater.loadAnimator(applicationContext, R.animator.front_animator) as AnimatorSet
        back_animation = AnimatorInflater.loadAnimator(applicationContext, R.animator.back_animator) as AnimatorSet

        flip.setOnClickListener{
            flip.visibility = View.GONE
            learn2Button.visibility = View.VISIBLE
            front_animation.setTarget(back)
            back_animation.setTarget(front)
            back_animation.start()
            front_animation.start()
            isFront =true
            Handler().postDelayed({
                left_arrow.visibility = View.VISIBLE
                right_arrow.visibility = View.VISIBLE
            }, 900)
        }

        learn1Button.setOnClickListener{

            try{
                learnIdx = currentIdx

                // Retrieve product name
                productNameBack.text = SkinType.skin.getProducts()[currentIdx].getName()

                // Retrieve product image
                var image: String = SkinType.skin.getProducts()[currentIdx].getImage()
                var id: Int = this.resources.getIdentifier(image, "drawable", this.packageName)
                productImgBack.setImageResource(id)
                var name = productNameBack.text
                val ratingBack = findViewById<RatingBar>(R.id.rating_ind_prod)
                ratingBack.stepSize = 0.1F
                getRating(name.toString())

                left_arrow.visibility = View.GONE
                right_arrow.visibility = View.GONE
                front_animation.setTarget(front)
                back_animation.setTarget(back)
                front_animation.start()
                back_animation.start()
                isFront = false
                resetReviewPage(name.toString())
                Handler().postDelayed({
                    flip.visibility = View.VISIBLE
                    learn2Button.visibility = View.GONE
                }, 900)
            }catch (e: Exception){
                Log.w("Result", e.toString())
            }
        }

        learn2Button.setOnClickListener{
            learnIdx = currentIdx + 1

            // Retrieve product name
            productNameBack.text = SkinType.skin.getProducts()[currentIdx+1].getName()

            var image: String = SkinType.skin.getProducts()[currentIdx+1].getImage()
            var id: Int = this.resources.getIdentifier(image, "drawable", this.packageName)
            productImgBack.setImageResource(id)

            var name = productNameBack.text
            val ratingBack = findViewById<RatingBar>(R.id.rating_ind_prod)
            ratingBack.stepSize = 0.1F
            getRating(name.toString())

            left_arrow.visibility = View.GONE
            right_arrow.visibility = View.GONE
            front_animation.setTarget(front)
            back_animation.setTarget(back)
            front_animation.start()
            back_animation.start()
            isFront = false
            Handler().postDelayed({
                flip.visibility = View.VISIBLE
                learn2Button.visibility = View.GONE
            }, 900)
            resetReviewPage(name.toString())
        }
    }

    fun resetReviewPage(name: String){
        Log.w("Hearts", "reset: $name")
        try{
            if(!set.contains(name)){
                Log.w("Hearts", "in reset")
                var linLayout = findViewById<LinearLayout>(R.id.reviewList)
                Log.w("Hearts", "REVIEWWW")

                linLayout.removeAllViews()
                var reviewTitle = TextView(this@Result)
                reviewTitle.text = "Be the first to review! \n"
                reviewTitle.textSize = 15.0f
                reviewTitle.setTypeface(null, Typeface.BOLD)
                linLayout.addView(reviewTitle)

                var numReview = findViewById<TextView>(R.id.num_reviews)
                numReview.text = "0 Terp Reviews"

                var hearts = findViewById<RatingBar>(R.id.rating_ind_prod)
                hearts.rating = 0.0f
            }
        } catch ( e: Exception){
            Log.e("Hearts", e.toString())
        }
    }

    fun maps (v:View){
        var intent: Intent = Intent(this, MapsActivity::class.java)
        this.startActivity(intent)
    }

    private fun setPreferences() {
        var pref = this.getSharedPreferences( this.packageName + "_preferences", Context.MODE_PRIVATE )
        var editor: SharedPreferences.Editor = pref.edit()

        editor.putString("SkinType", SkinType.skin.getSkinType())
        editor.putString("SkinProblemOne", SkinType.skin.getFirstProblem())
        editor.putString("SkinProblemTwo", SkinType.skin.getSecondProblem())
        editor.putBoolean("Took Quiz", true)
        editor.commit()
        Log.w("CMSC","commit")
    }

    // Determines what file to parse
    private fun sortParse(){
        // Depending on what the user puts we can parse the oily, combinations, or dry
        // skin file.
        var handler = SAXHandlerSkintype()
        var handler2 = SAXHandlerProblem()

        if(SkinType.skin.getSkinType() == "oily"){
            SkinType.skin.setProducts(parseFile(R.raw.oily, handler, null))
        } else if (SkinType.skin.getSkinType() == "combination"){
            SkinType.skin.setProducts(parseFile(R.raw.combination, handler, null))
        } else if (SkinType.skin.getSkinType() == "dry"){
            SkinType.skin.setProducts(parseFile(R.raw.dry, handler, null))
        } else if (SkinType.skin.getSkinType() == "normal"){
            SkinType.skin.setProducts(parseFile(R.raw.normal, handler, null))
        }

        // Then we can parse the problem area file which has a complete list of all the products
        SkinType.skin.setProblemProducts(parseFile(R.raw.products, null, handler2))
    }

    /* Takes a resource int of a file and parses that file depending on
       if the file is a problem area file or a skin type file.
       You also pass in the parser you want to use.
    */
    private fun parseFile(resource: Int, type: SAXHandlerSkintype?, problem: SAXHandlerProblem?): ArrayList<Product>{
        var products: ArrayList<Product> = ArrayList<Product>()

        var factory: SAXParserFactory = SAXParserFactory.newInstance()
        var saxParser: SAXParser = factory.newSAXParser()
        var iStream: InputStream = resources.openRawResource(resource)

        if (type == null) {
            saxParser.parse(iStream, problem)
            products = problem!!.getItems()

        } else {
            saxParser.parse(iStream, type)
            products = type!!.getItems()
        }
        return products
    }

    fun goToProduct(v: View){
        val url = SkinType.skin.getProducts()[learnIdx].getUrl()
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    fun goToReview(v: View){
        val dialog = Dialog(this)
        var ratingValue: Float = 0.0f;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.write)
        review_submit = dialog.findViewById<Button>(R.id.submit)
        hearts = dialog.findViewById(R.id.hearts)
        review = dialog.findViewById<EditText>(R.id.edit)

        hearts.setOnRatingBarChangeListener(OnRatingBarChangeListener { ratingBar, rating, fromUser ->
            ratingValue = rating
        })
        review_submit.setOnClickListener {
            Log.w("Hearts", "" + ratingValue)
            Log.w("Hearts", "Text review is: " + review.text)
            val product = findViewById<TextView>(R.id.product_name_back)
            val name = product.text.toString()
            addDatabase(name, ratingValue, review.text.toString())
            getRating(name)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun addDatabase(product: String, rating:Float, review:String){
        val database = Firebase.database("https://skincareapp-d5004-default-rtdb.firebaseio.com/")
        var myRef = database.getReference(product)
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Reference exists
                    myRef.child("Rating").get().addOnSuccessListener {
                        var value = it.getValue(Double::class.java)
                        myRef.child("Rating").setValue(rating + value!!)
                    }.addOnFailureListener{
                        Log.e("firebase", "Error getting data", it)
                    }

                    myRef.child("People").get().addOnSuccessListener {
                        var value = it.getValue(Double::class.java)
                        myRef.child("People").setValue(1.0 + value!!)
                    }.addOnFailureListener{
                        Log.e("firebase", "Error getting data", it)
                    }
                    val newReviewRef = myRef.child("Reviews").push()
                    newReviewRef.setValue(review)
                } else {
                    Log.w("Hearts", "could not find reference")
                    // Reference does not exist
                    myRef.child("Rating").setValue(rating)
                    myRef.child("People").setValue(1.0)
                    val newReviewRef = myRef.child("Reviews").push()
                    newReviewRef.setValue(review)
                    set.add(product)
                }
                getRating(product)
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun getRating(product: String){ //rating = 0 if fails
        val database = Firebase.database("https://skincareapp-d5004-default-rtdb.firebaseio.com/")
        var myRef = database.getReference(product)
        var rating = 0.0

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Reference exists
                    myRef.child("Rating").get().addOnSuccessListener {dataRatingshot:DataSnapshot ->
                        val value = dataRatingshot.getValue(Double::class.java)
                        rating += value!!
                        Log.w("Hearts", rating.toString() + " value in rating")
                    }

                    myRef.child("People").get().addOnSuccessListener {dataPeopleshot:DataSnapshot ->
                        val value = dataPeopleshot.getValue(Double::class.java)
                        rating /= value!!
                        Log.w("Hearts", value.toString() + " value in people")
                        val ratingBack = findViewById<RatingBar>(R.id.rating_ind_prod)
                        val numPeople = findViewById<TextView>(R.id.num_reviews)
                        numPeople.text = value.toInt().toString() + " Terp Reviews"
                        ratingBack.rating = rating.toFloat()
                    }

                    myRef.child("Reviews").get().addOnSuccessListener { dataReviewshot:DataSnapshot ->
                        val value = dataReviewshot.getValue<Map<String, String>>()
                        var linLayout = findViewById<LinearLayout>(R.id.reviewList)
                        Log.w("Hearts", "REVIEWWW")

                        linLayout.removeAllViews()
                        var reviewTitle = TextView(this@Result)
                        reviewTitle.text = "See what Terps are saying: \n"
                        reviewTitle.textSize = 15.0f
                        reviewTitle.setTypeface(null, Typeface.BOLD)
                        linLayout.addView(reviewTitle)

                        value?.let {reviewsMap: Map <String, String>->
                            for ((reviewId, reviewText) in reviewsMap) {
                                // Iterate through the reviews and add them
                                Log.w("Reviews", "Review Text: $reviewText")

                                if (reviewText.isNotBlank()){
                                    var newReviewText = TextView(this@Result)
                                    newReviewText.text = "‣" + " \"" + reviewText + "\"" + "\n"
                                    linLayout.addView(newReviewText)
                                }
                            }
                        }
                    }
                } else {
                    // Reference does not exist
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun updateResultPage(currentIdx : Int) {
        Log.w("Products", SkinType.skin.getSecondProblem())
        if(currentIdx < 6){
            productTitle.text = "Best " +  SkinType.skin.getProducts()[currentIdx].getType() + 's'
        } else {
            var problem = SkinType.skin.getProducts()[currentIdx].getProblem().replaceFirstChar{ it.uppercase()}
            productTitle.text = "Best Products for \n" + problem
        }

        product1Name.text = SkinType.skin.getProducts()[currentIdx].getName()
        var image: String = SkinType.skin.getProducts()[currentIdx].getImage()
        var id: Int = this.resources.getIdentifier(image, "drawable", this.packageName)
        product1Image.setImageResource(id)

        product2Name.text = SkinType.skin.getProducts()[currentIdx + 1].getName()
        image = SkinType.skin.getProducts()[currentIdx+1].getImage()
        id = this.resources.getIdentifier(image, "drawable", this.packageName)
        product2Image.setImageResource(id)
    }

    fun leftClicked(v: View){
        Log.w("MainActivity", "left button clicked result page")
        if(currentIdx > 0){
            currentIdx -= 2
        }
        updateResultPage(currentIdx)
    }

    fun rightClicked(v:View){
        if(listSize == 10 && currentIdx < 8){
            currentIdx += 2
            updateResultPage(currentIdx)
        } else if(listSize == 8 && currentIdx < 6){
            currentIdx += 2
            updateResultPage(currentIdx)
        }
    }
}