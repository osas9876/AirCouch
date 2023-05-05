package com.example.osasproject.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

import com.example.osasproject.helpers.exists
import com.example.osasproject.helpers.read
import com.example.osasproject.helpers.write
import java.util.*


val JSON_FILE = "aircouchs.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<AirCouchModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class AirCouchJSONStore : AirCouchStore {

    val context: Context
    var aircouchs = mutableListOf<AirCouchModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<AirCouchModel> {
        return aircouchs
    }

    override fun create(aircouch: AirCouchModel) {
        aircouch.id = generateRandomId()
        aircouchs.add(aircouch)
        serialize()
    }


    override fun update(aircouch: AirCouchModel) {
        val aircouchsList = findAll() as ArrayList<AirCouchModel>
        var foundAircouch: AirCouchModel? = aircouchsList.find { p -> p.id == aircouch.id }
        if (foundAircouch != null) {
            foundAircouch.title = aircouch.title
            foundAircouch.address = aircouch.address
            foundAircouch.spaces = aircouch.spaces
            foundAircouch.type = aircouch.type
            foundAircouch.image = aircouch.image
            foundAircouch.lat = aircouch.lat
            foundAircouch.lng = aircouch.lng
            foundAircouch.zoom = aircouch.zoom
        }
        serialize()
    }

    override fun delete(aircouch: AirCouchModel) {
        aircouchs.remove(aircouch)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(aircouchs, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        aircouchs = Gson().fromJson(jsonString, listType)
    }
}