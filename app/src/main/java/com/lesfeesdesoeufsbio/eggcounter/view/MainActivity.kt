package com.lesfeesdesoeufsbio.eggcounter.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import com.lesfeesdesoeufsbio.eggcounter.R
import com.lesfeesdesoeufsbio.eggcounter.databinding.ActivityMainBinding
import com.lesfeesdesoeufsbio.eggcounter.model.DaySale
import com.lesfeesdesoeufsbio.eggcounter.model.DaySaleReposytory
import com.lesfeesdesoeufsbio.eggcounter.model.EggNumber
import com.lesfeesdesoeufsbio.eggcounter.model.EggSale
import com.lesfeesdesoeufsbio.eggcounter.model.EggSize

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private val sizes = EggSize.entries.toTypedArray()
    private val quantities = EggNumber.entries.toTypedArray()

    var daySaleRepository : DaySaleReposytory? = null
    var daySale : DaySale? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        daySaleRepository = DaySaleReposytory.getInstance(applicationContext)
        daySale = daySaleRepository!!.getDay()

        val gridLayout: GridLayout = findViewById(R.id.grid_layout)
        val inflater = LayoutInflater.from(this)

        for (quantity in quantities) {
            for (size in sizes) {
                val itemView: View = inflater.inflate(R.layout.grid_item, gridLayout, false)

                val itemText: TextView = itemView.findViewById(R.id.item_text)
                itemText.text = "Quantité: ${quantity.nb} Taille: ${size.size}"

                val valueText: TextView = itemView.findViewById(R.id.text_value)
                valueText.text = daySale!!.getNumberSaleForType(quantity, size).toString()

                val addButton: Button = itemView.findViewById(R.id.button_add)
                addButton.setOnClickListener {
                    daySale!!.addSale(EggSale(quantity, size))
                    updateButton(itemView,quantity,size)
                    daySaleRepository!!.save(daySale!!)
                }

                val removeButton: Button = itemView.findViewById(R.id.button_remove)
                removeButton.setOnClickListener {
                    daySale!!.removeSale(quantity, size)
                    updateButton(itemView,quantity,size)
                    daySaleRepository!!.save(daySale!!)
                }

                val params = GridLayout.LayoutParams()
                params.width = 0
                params.height = GridLayout.LayoutParams.WRAP_CONTENT
                // le undefined ici je le changerai probablement j'ai juste pas tout capté sur le grid.layout
                params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                itemView.layoutParams = params

                gridLayout.addView(itemView)
            }
        }


        // Bouton Historique (mène à l'activité HistoryActivity)
        val buttonHistory: Button = findViewById(R.id.button_history)
        buttonHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // le menu il est aigri il veut pas s'afficher
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // test pour l'affichage de la barre menu (équivalent du bouton historique)
        return when (item.itemId) {
            R.id.action_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_history -> {
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateButton(item: View,eggNumber: EggNumber,eggSize: EggSize) {
        val valueText = item.findViewById<TextView>(R.id.text_value)
        valueText.text = daySale!!.getNumberSaleForType(eggNumber, eggSize).toString()
    }
}