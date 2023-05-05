package com.example.osasproject.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.example.osasproject.R
import com.example.osasproject.databinding.ActivityAircouchBinding
import com.example.osasproject.helpers.readImage
import com.example.osasproject.helpers.readImageFromPath
import com.example.osasproject.helpers.showImagePicker
import com.example.osasproject.main.MainApp
import com.example.osasproject.models.AirCouchModel
import com.example.osasproject.models.Location
import kotlinx.android.synthetic.main.activity_aircouch.*
import kotlinx.android.synthetic.main.activity_aircouch.Type
import kotlinx.android.synthetic.main.activity_aircouch.aircouchAddress
import kotlinx.android.synthetic.main.activity_aircouch.aircouchTitle
import kotlinx.android.synthetic.main.activity_aircouch.spacesAvailable
import kotlinx.android.synthetic.main.card_aircouch.*
import timber.log.Timber.i


class AirCouchActivity : AppCompatActivity() {

    var aircouch = AirCouchModel()
    lateinit var app: MainApp
    var edit = false
    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2
    var location = Location(52.245696, -7.139102, 15f)
    private lateinit var binding: ActivityAircouchBinding
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aircouch)
        binding = ActivityAircouchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp

        val items = listOf("bunk bed", "couch", "single bed", "double bed")
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, items)
        binding.Type.setAdapter(adapter)

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        binding.chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }
        binding.btnDelete.isVisible=false

        if (intent.hasExtra("aircouch_edit")) {
            edit = true
            aircouch = intent.extras?.getParcelable<AirCouchModel>("aircouch_edit")!!
            aircouchTitle.setText(aircouch.title)
            aircouchAddress.setText(aircouch.address)
            Type.setText(aircouch.type)
            spacesAvailable.setText(aircouch.spaces)
            binding.btnDelete.isVisible=true
            btnAdd.setText(R.string.save_aircouch)
            aircouchImage.setImageBitmap(readImageFromPath(this, aircouch.image))
            if (aircouch.image != null) {
                chooseImage.setText(R.string.change_aircouch_image)
            }
            btnAdd.setText(R.string.save_aircouch)
        }
        binding.btnDelete.setOnClickListener(){
            app.aircouchs.delete(aircouch)

            ("delete Button Pressed: $aircouchTitle")
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }

        btnAdd.setOnClickListener() {
            aircouch.title = aircouchTitle.text.toString()
            aircouch.address = aircouchAddress.text.toString()
            aircouch.spaces = spacesAvailable.text.toString()
            aircouch.lat= location.lat
            aircouch.lng = location.lng
            aircouch.zoom = location.zoom
            aircouch.type = Type.text.toString()
            if (aircouch.title.isEmpty()) {
                (R.string.enter_aircouch_title)
            } else {
                if (edit) {
                    app.aircouchs.update(aircouch.copy())
                } else {
                    app.aircouchs.create(aircouch.copy())
                }
            }
            ("add Button Pressed: $aircouchTitle")
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }
registerMapCallback()
        aircouchLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            val launcherIntent = Intent(this, MapsActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_aircouch, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                finish()
            }
            R.id.btnDelete  -> {
                app.aircouchs.delete(aircouch)
                ("delete Button Pressed: $aircouchTitle")
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    aircouch.image = data.getData().toString()
                    aircouchImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.change_aircouch_image)

                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    location = data.extras?.getParcelable<Location>("location")!!
                }
            }
        }
    }
    // delete aircouch should be here

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            location = result.data!!.extras?.getParcelable("location")!!
                            i("Location == $location")
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}


