package com.example.osasproject.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.osasproject.R
import com.example.osasproject.helpers.readImageFromPath
import com.example.osasproject.models.AirCouchModel
import kotlinx.android.synthetic.main.card_aircouch.view.*

interface AirCouchListener {
    fun onAirCouchClick(aircouch: AirCouchModel)
}

class AirCouchAdapter constructor(private var aircouchs: List<AirCouchModel>,
                                   private val listener: AirCouchListener) : RecyclerView.Adapter<AirCouchAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_aircouch, parent, false))
    }


    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val aircouch = aircouchs[holder.adapterPosition]
        holder.bind(aircouch, listener)
    }

    override fun getItemCount(): Int = aircouchs.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(aircouch: AirCouchModel,  listener : AirCouchListener) {
            itemView.aircouchTitle.text = aircouch.title
            itemView.aircouchAddress.text = aircouch.address
            itemView.spacesAvailable.text = aircouch.spaces
            itemView.Type.text = aircouch.type
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, aircouch.image))
            itemView.setOnClickListener { listener.onAirCouchClick(aircouch) }
        }
    }
}