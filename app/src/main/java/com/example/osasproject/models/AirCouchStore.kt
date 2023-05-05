package com.example.osasproject.models

interface AirCouchStore {
    fun findAll(): List<AirCouchModel>
    fun create(aircouch: AirCouchModel)
    fun update(aircouch: AirCouchModel)
    fun delete(aircouch: AirCouchModel)
}

