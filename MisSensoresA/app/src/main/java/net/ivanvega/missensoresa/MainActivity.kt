package net.ivanvega.missensoresa

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener {
    private var mSensor: Sensor? = null
    private lateinit var sensorManager: SensorManager
    private var mLight: Sensor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val deviceSensors =   sensorManager.getSensorList(Sensor.TYPE_ALL)

        deviceSensors.forEach {
            Log.i("MisSensores", it.toString())
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            // Success! There's a magnetometer.
            Log.i("MisSensores", "MAGNETOMETRO ENCONTRADO")
        } else {
            // Failure! No magnetometer.
            Log.i("MisSensores", "MAGNETOMETRO NO ENCONTRADO")
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null) {
            val gravSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_GRAVITY)
            // Use the version 3 gravity sensor.
            mSensor = gravSensors.firstOrNull { it.vendor.contains("Google LLC") && it.version == 3 }
        }
        if (mSensor == null) {
            // Use the accelerometer.
            mSensor = if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            } else {
                // Sorry, there are no accelerometers on your device.
                // You can't play this game.
                null
            }
        }

        Log.i("MisSensores", mSensor.toString())

        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)


    }

    override fun onResume() {
        super.onResume()
        mLight?.also { light ->
            sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        //TODO("Not yet implemented")
        val lux =  p0!!.values[0]
        (findViewById(R.id.txt) as TextView).text = lux.toString()
        Log.i("LUZhay", lux.toString())

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        //TODO("Not yet implemented")
    }
}