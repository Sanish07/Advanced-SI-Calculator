package com.example.ca3_assignment

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class CalculatorRedirect : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator_redirect)

        var resultText = findViewById<TextView>(R.id.showRes)
        var creditRed = findViewById<TextView>(R.id.credits)

        val bundle:Bundle? = intent.extras
        var principal = bundle?.get("principal").toString().toDouble()
        var rate = bundle?.get("rate").toString().toDouble()
        var years = bundle?.get("years").toString().toInt()
        var months = bundle?.get("months").toString().toInt()
        var denom = bundle?.get("denomination").toString()
        var intCollDuration = bundle?.get("intrCollDur").toString()
        var intPrintDuration = bundle?.get("printInInter").toString()

        if(intCollDuration == "yearly"){ //For Interest collected yearly
            var time = years + (months*(1/12)).toInt()
            if(intPrintDuration == "Years"){
                resultText.text = calculateSI(principal,rate,denom,time,12,true,intPrintDuration)
            } else if(intPrintDuration == "Months") {
                Toast.makeText(applicationContext,"Cross-duration info details have not been supported!",Toast.LENGTH_LONG).show()
                resultText.text = calculateSI(principal,rate,denom,time,12,false,intPrintDuration)
            } else{ // -working
                resultText.text = calculateSI(principal,rate,denom,time,12,false,intPrintDuration)
            }
        } else{ //For Interest collected monthly
            var time = (years*12) + months
            if(intPrintDuration == "Years"){
                Toast.makeText(applicationContext,"Cross-duration info details have not been supported!",Toast.LENGTH_LONG).show()
                resultText.text = calculateMonthlySI(principal,rate,denom,time,1,false,intPrintDuration)
            } else if(intPrintDuration == "Months") {
                resultText.text = calculateMonthlySI(principal,rate,denom,time,1,true,intPrintDuration)
            } else{
                resultText.text = calculateMonthlySI(principal,rate,denom,time,1,false,intPrintDuration)
            }
        }

        creditRed.setOnClickListener{
            val url = "https://www.thecalculatorsite.com/finance/calculators/simple-interest-calculator.php"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.parse(url),"text/plain")
            val choose = Intent.createChooser(intent,"Open Credits URL")
            startActivity(choose)
        }

    }

    fun calculateSI(principal:Double, rate:Double, denom:String, time :Int, skip:Int, printDetails:Boolean, duration:String):String{
        var siString:String = ""
        var interest:Double = (principal * rate * time) / 100
        var si = principal

        for(i in 0 until time step skip){
            if(printDetails){
                    siString += "After ${i} ${duration} : ${denom} ${si} \n"
                    si += interest
            } else{
                si += interest
            }
        }
        siString += "Final Balance : ${denom} ${si}"
        return siString
    }

    fun calculateMonthlySI(principal:Double, rate:Double, denom:String, time :Int, skip:Int, printDetails:Boolean, duration:String):String{
        var siString:String = ""
        var interest:Double = (principal * rate) / 100
        var si = principal

        for(i in 0 until time step skip){
            if(printDetails){
                siString += "After ${i} ${duration} : ${denom} ${si} \n"
                si += interest
            } else{
                si += interest
            }
        }
        siString += "Final Balance : ${denom} ${si}"
        return siString
    }
}