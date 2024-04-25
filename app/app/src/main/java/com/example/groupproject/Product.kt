package com.example.groupproject

// Class for the different products we have in our xml files for the user
// Each product has a name, a url (mainly Amazon), what problem it will fix
// (dryness, oilyness, etc), and what type of product it is (toner, cleanser, etc)
class Product {
    private var type: String
    private var name: String
    private var url: String
    private var problem: String
    private var rate: String
    private var location: String
    private var image: String

    constructor(type:String, name: String, url:String, problem: String, rate: String, location:String, image:String){
        this.type = type
        this.name = name
        this.url = url
        this.problem = problem
        this.rate = rate
        this.location = location
        this.image = image
    }

    fun setType(type: String){
        this.type = type
    }

    fun setName(name: String){
        this.name = name

    }

    fun setImage(image:String){
        this.image = image
    }
    fun setUrl(url: String){
        this.url = url
    }
    fun setProblem(problem: String){
        this.problem = problem
    }
    fun setRate(rate:String){
        this.rate = rate
    }

    fun setLocation(location: String){
        this.location = location
    }

    fun getProblem():String{
        return this.problem
    }

    fun getType(): String {
        return type
    }

    fun getImage(): String{
        return this.image
    }

    fun getUrl(): String{
        return url
    }

    fun getName(): String{
        return name
    }

    fun getRate():String{
        return rate
    }

    fun getLocation():String{
        return  location
    }

    override fun toString(): String{
        return this.name + " " + this.type + " " + this.url + " " +this.rate + " " + this.location + "" + this.image
    }
}