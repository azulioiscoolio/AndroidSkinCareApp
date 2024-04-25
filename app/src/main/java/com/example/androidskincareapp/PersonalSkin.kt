package com.example.androidskincareapp

import android.util.Log

// Class for the personalized skin care
// Each skin care will have a skin type, two problems, and the products they need in an array list
class PersonalSkin {
    private var skinType: String
    private var firstProblem: String
    private var secondProblem: String
    private var products: ArrayList<Product> = ArrayList<Product>()

    init {
        skinType = ""
        firstProblem = ""
        secondProblem = ""
        products = ArrayList<Product>()
    }

    fun setSkinType(skinType: String){
        this.skinType = skinType
    }

    fun setFirstProblem(firstProblem: String){
        this.firstProblem = firstProblem
    }

    fun setSecondProblem(secondProblem:String){
        this.secondProblem = secondProblem
    }

    fun setProducts(products: ArrayList<Product>){
        this.products = products
    }

    fun getSkinType(): String{
        return this.skinType
    }

    fun getFirstProblem(): String{
        return this.firstProblem
    }

    fun getSecondProblem(): String{
        return this.secondProblem
    }

    fun getProducts(): ArrayList<Product>{
        return this.products
    }

    //Determines which products in the file are useful to the user and puts that in the products
    fun setProblemProducts(problemProducts:ArrayList<Product>){

        Log.w("Problem Products", firstProblem)
        // Determines products for their first problem
        for(i in problemProducts){
            if(i.getProblem() == this.firstProblem){
                Log.w("Problem Products", i.toString())
                this.products.add(i)
            }
        }
        Log.w("Problem Products", secondProblem)
        // Determines products for their second problem
        for(i in problemProducts){
            if (i.getProblem() == this.secondProblem){
                Log.w("Problem Products", i.toString())
                this.products.add(i)
            }
        }
    }

    override fun toString(): String {
        return this.skinType + " " +  this.firstProblem + " " + this.secondProblem + " " + this.products
    }
}