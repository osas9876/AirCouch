package com.example.osasproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.osasproject.main.MainApp
import kotlinx.android.synthetic.main.activity_aircouch.*
import kotlinx.android.synthetic.main.activity_aircouch_list.*
import kotlinx.android.synthetic.main.card_aircouch.view.*
import com.example.osasproject.models.AirCouchModel
import kotlinx.android.synthetic.main.card_aircouch.*
import androidx.activity.result.contract.ActivityResultContracts
import com.example.osasproject.activities.AirCouchActivity
import com.example.osasproject.activities.AirCouchAdapter
import com.example.osasproject.activities.AirCouchListener
import kotlinx.android.synthetic.main.activity_aircouch.view.*
import kotlinx.android.synthetic.main.card_aircouch.view.aircouchAddress
import kotlinx.android.synthetic.main.card_aircouch.view.aircouchTitle
import kotlinx.android.synthetic.main.card_aircouch.view.spacesAvailable
import kotlinx.android.synthetic.main.card_aircouch.view.Type


class AirCouchListActivity : AppCompatActivity(), AirCouchListener {
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aircouch_list)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = AirCouchAdapter(app.aircouchs.findAll() as ArrayList<AirCouchModel>, this)
        loadAircouchs()
        toolbar.title = title
        setSupportActionBar(toolbar)
    }

    override fun onAirCouchClick(aircouch: AirCouchModel) {

        val launcherIntent = Intent(this, AirCouchActivity::class.java)
        launcherIntent.putExtra("aircouch_edit",aircouch)
        getResult.launch(launcherIntent)


    }

    override  fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //recyclerView is a widget in activity_placemark_list.xml
        recyclerView.adapter?.notifyDataSetChanged()
        loadAircouchs()
        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, AirCouchActivity::class.java)
                getResult.launch(launcherIntent)
            }


        }
        return super.onOptionsItemSelected(item)
    }


    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                //This is where you will add yuor code to populate
                //the adaptor with the new object

                recyclerView.adapter?.notifyDataSetChanged()
            }
        }

    private fun loadAircouchs() {
        showAirCouch(app.aircouchs.findAll())
    }

    fun showAirCouch (aircouchs: List<AirCouchModel>) {
        recyclerView.adapter = AirCouchAdapter(aircouchs as ArrayList<AirCouchModel>, listener = this)
        recyclerView.adapter?.notifyDataSetChanged()
    }


    class AircouchAdapter(private var aircouchs: ArrayList<AirCouchModel>) :
        RecyclerView.Adapter<AircouchAdapter.MainHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
            return MainHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.card_aircouch,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: MainHolder, position: Int) {
            val aircouch = aircouchs[holder.adapterPosition]
            holder.bind(aircouch)
        }

        override fun getItemCount(): Int = aircouchs.size

        class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(aircouch: AirCouchModel) {
                itemView.aircouchTitle.text = aircouch.title
                itemView.aircouchAddress.text = aircouch.address
                itemView.spacesAvailable.text = aircouch.spaces
                itemView.Type.text = aircouch.type
            }
        }
    }


}