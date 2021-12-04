package com.example.ca3_assignment

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.*
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.marginLeft
import androidx.core.view.marginStart
import androidx.core.view.setPadding

class CalculatorMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getCurMenu = findViewById<Button>(R.id.currChoose)
        var principal = findViewById<EditText>(R.id.Principal) //Initial Balance
        var rate = findViewById<EditText>(R.id.Rate)   //Interest Rate
        var years = findViewById<EditText>(R.id.yearInput)  //Years input = 12 months
        var months = findViewById<EditText>(R.id.monInput)  //Month Input
        var showInterest = findViewById<CheckBox>(R.id.interCheck)   //Option for showing interest in intervals
        var intervalRadios = findViewById<RadioGroup>(R.id.intervalSelect) //Will be functional when checkbox is active
        var submitInfo = findViewById<Button>(R.id.calculateBut) //Submit Info Button

        var choseFiat = ""
        var choseDuration = "yearly"
        var printDuration:String ?= null

        //For Fetching Fiat Currency Denomination - Popup Menu
        getCurMenu.setOnClickListener{
            val popup = PopupMenu(this,getCurMenu)
            popup.menuInflater.inflate(R.menu.options,popup.menu)
            popup.setOnMenuItemClickListener{
                choseFiat = it.title.toString()
                getCurMenu.text = choseFiat
                Toast.makeText(applicationContext,"Currency Set : ${choseFiat}", Toast.LENGTH_LONG).show()
                true
            }
            popup.show()
        }

        //Set interest obtaining duration - Spinner
        val ratedur = arrayOf("yearly","monthly")
        val spin = findViewById<Spinner>(R.id.Ratedurat)
        spin.setBackgroundColor(Color.parseColor("#ffffff"))
        if(spin!=null)
        {
            val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,ratedur)
            spin.adapter = adapter
        }

        spin.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                choseDuration = ratedur[position]
                Toast.makeText(applicationContext,"Interest obtained set to ${choseDuration}",Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                TODO("Not Yet Implemented")
            }
        })

        showInterest.setOnClickListener{
            var rad1 = RadioButton(this)
            var rad2 = RadioButton(this)
            if(showInterest.isChecked){

                rad1.text = getString(R.string.text4hint)
                rad1.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                rad1.setPadding(10,10,10,10)
                rad1.setTextColor(Color.parseColor("#ffffff"))

                rad2.text = getString(R.string.text4hint2)
                rad2.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                rad2.setPadding(10,10,10,10)
                rad2.setTextColor(Color.parseColor("#ffffff"))

                intervalRadios.addView(rad1)
                intervalRadios.addView(rad2)

                rad1.setOnClickListener{
                    printDuration = rad1.text.toString()
                }

                rad2.setOnClickListener{
                    printDuration = rad2.text.toString()
                }
            } else{
                intervalRadios.removeAllViews()
                printDuration = null
            }

        }

        submitInfo.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Data Submit Confirmation")
            builder.setMessage("Are you sure, you want to keep the same data and compute?")
            builder.setCancelable(false)
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            builder.setPositiveButton("Yes"){ dialogInterface,which ->
                val intent = Intent(this,CalculatorRedirect::class.java)
                intent.putExtra("principal",principal.text.toString())
                intent.putExtra("rate",rate.text.toString())
                intent.putExtra("years",years.text.toString())
                intent.putExtra("months",months.text.toString())
                intent.putExtra("denomination",choseFiat)
                intent.putExtra("intrCollDur",choseDuration)
                intent.putExtra("printInInter",printDuration)
                startActivity(intent)
            }

            builder.setNegativeButton("Make Changes"){ dialogInterface,which ->
                Toast.makeText(applicationContext,"You can make changes now", Toast.LENGTH_LONG).show()

            }
            val alertDia : AlertDialog = builder.create()
            alertDia.show()
        }

    }
}