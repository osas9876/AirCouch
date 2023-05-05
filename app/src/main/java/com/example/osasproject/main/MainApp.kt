package com.example.osasproject.main
import android.app.Application
import com.example.osasproject.models.AirCouchJSONStore
import com.example.osasproject.models.AirCouchMemStore
import com.example.osasproject.models.AirCouchModel
import com.example.osasproject.models.AirCouchStore

class MainApp : Application() {

    //val aircouchs = ArrayList<AirCouchModel>()
    lateinit var aircouchs : AirCouchStore




    override fun onCreate() {
        super.onCreate()
        aircouchs = AirCouchJSONStore(applicationContext)

        ("Aircouch started")
       // aircouchs.add(AirCouchModel("One", "About one..."))
       // aircouchs.add(AirCouchModel("Two", "About two..."))
       // aircouchs.add(AirCouchModel("Three", "About three..."))
    }

}