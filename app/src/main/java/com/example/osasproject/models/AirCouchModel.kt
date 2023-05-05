package com.example.osasproject.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AirCouchModel( var id: Long = 0,
                            var title: String = "",
                          var address: String = "",
                            var type: String = "",
                            var spaces: String = "",
                         var image: String = "",
                          var lat : Double = 0.0,
                          var lng: Double = 0.0,
                          var zoom: Float = 0f) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable
