package br.edu.mouralacerda.dm2y2023projeto5

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity(), SensorEventListener, LocationListener {

    var mSensorManager: SensorManager? = null
    var mAccelerometer: Sensor? = null
    var mLocationManager: LocationManager? = null

    var txtX: TextView? = null
    var txtY: TextView? = null
    var txtZ: TextView? = null

    var txtLatitude: TextView? = null
    var txtLongitude: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtX = findViewById(R.id.txtX)
        txtY = findViewById(R.id.txtY)
        txtZ = findViewById(R.id.txtZ)

        txtLatitude = findViewById(R.id.txtLatitude)
        txtLongitude = findViewById(R.id.txtLongitude)


        // ACELEROMETRO
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        mSensorManager!!.flush(this)
        mSensorManager!!.registerListener(this, mAccelerometer, 1000)


        // GPS

        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Permita a localização", Toast.LENGTH_LONG).show()
            // PEÇA A PERMISSAO DE LOCALIZAÇÃO
            return
        }

        mLocationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0f, this)


    }

    override fun onSensorChanged(p0: SensorEvent) {
        txtX!!.text = p0.values[0].toString()
        txtY!!.text = p0.values[1].toString()
        txtZ!!.text = p0.values[2].toString()

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onLocationChanged(p0: Location) {
        txtLatitude!!.text = p0.latitude.toString()
        txtLongitude!!.text = p0.longitude.toString()
    }
}