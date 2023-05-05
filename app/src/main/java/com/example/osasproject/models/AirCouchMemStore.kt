package com.example.osasproject.models

var lastId = 0L
internal fun getId(): Long {
    return lastId++
}

class AirCouchMemStore: AirCouchStore {
    val aircouchs = ArrayList<AirCouchModel>()

    override fun findAll(): List<AirCouchModel> { // holds list of different variabels from my aircouch model
            return aircouchs
    }

    override fun create(aircouch: AirCouchModel) {
        aircouch.id = getId()
        aircouchs.add(aircouch)
        logAll()
    }

    override fun update(aircouch: AirCouchModel) {
        var foundAirCouch: AirCouchModel? = aircouchs.find { p -> p.id == aircouch.id }
        if (foundAirCouch != null) {
            foundAirCouch.title = aircouch.title
            foundAirCouch.address = aircouch.address
            foundAirCouch.type = aircouch.type
            foundAirCouch.spaces = aircouch.spaces
            foundAirCouch.image = aircouch.image
            logAll()
        }
    }

    override fun delete(aircouch: AirCouchModel) {
        TODO("Not yet implemented")
    }

    fun logAll(){
        aircouchs.forEach{  "${it}"  }
    }

}

