package com.example.groupproject

import android.util.Log
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

//SAX Parser for the different skin types, the problem xml will have to use a different
//parser because we use a counter to keep track of the type (Cleanser, Mositrizer, etc) of
//product


class SAXHandlerProblem:DefaultHandler() {
    private var products: ArrayList<Product> = ArrayList<Product>()
    private var currentProduct : Product? = null
    private var element : String = ""
    private var validText : Boolean = false

    // This counter is for keeping track of what product we are on
    private var counter: Int = 0

    override fun startElement( uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
        super.startElement(uri, localName, qName, attributes)
        // Log.w( "MainActivity", "startElement = " + qName )
        element = qName!!
        validText = true
        if( qName.equals( "product" ) ) {
            currentProduct = Product("","","","", "", "", "")
        }
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        super.endElement(uri, localName, qName)
        validText = false
        if( qName != null && currentProduct != null && qName!!.equals( "product" ) ) {
            products.add(currentProduct!!)
            counter += 1
        }
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
        super.characters(ch, start, length)
        if( ch != null ) {
            var text : String = String( ch!!, start, length )
            if( currentProduct != null && validText && element.equals( "name" ) ){
                currentProduct!!.setName(text)

                if(counter == 0 || counter == 1){
                    currentProduct!!.setProblem("dark spots")
                } else if (counter == 2 || counter == 3){
                    currentProduct!!.setProblem("pores")
                } else if (counter == 4 || counter == 5){
                    currentProduct!!.setProblem("acne")
                } else if (counter == 6 || counter == 7){
                    currentProduct!!.setProblem("redness")
                } else if (counter == 8 || counter == 9){
                    currentProduct!!.setProblem("dryness")
                } else if (counter == 10 || counter == 11){
                    currentProduct!!.setProblem("aging")
                }
            }

            else if ( currentProduct != null && validText && element.equals( "url" ) )
                currentProduct!!.setUrl(text)

            else if (currentProduct!=null && validText && element.equals("rate"))
                currentProduct!!.setRate(text)

            else if (currentProduct!=null && validText && element.equals("location"))
                currentProduct!!.setLocation(text)
            else if (currentProduct!=null && validText && element.equals("image"))
                currentProduct!!.setImage(text)
        }
    }

    fun getItems( ) : ArrayList<Product> {
        Log.w("CMSC", products.toString())
        return products
    }
}