package com.abdullah996.shakesensorapp

import android.annotation.SuppressLint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abdullah996.shakesensorapp.databinding.ActivityMainBinding
import kotlin.math.abs
import kotlin.math.sqrt

class MainActivity : AppCompatActivity(),SensorEventListener {

    private  var accelerationCurrentValue: Float =0f
    private  var accelerationPreviousValue: Float =0f
    private  var changeInAcceleration: Float =0f
    private lateinit var binding: ActivityMainBinding

    //define the values of sensors
    private lateinit var mSensorManger:SensorManager
    private lateinit var mAccel:Sensor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize the accelerometer  sensor
        mSensorManger=getSystemService(SENSOR_SERVICE) as SensorManager
        mAccel=mSensorManger.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    }

    override fun onResume() {
        super.onResume()
        mSensorManger.registerListener(this,mAccel,SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        mSensorManger.unregisterListener(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        val x= sensorEvent?.values?.get(0)
        val y= sensorEvent?.values?.get(1)
        val z= sensorEvent?.values?.get(2)

        if (x != null) {
            if (y != null) {
                if (z != null) {
                    accelerationCurrentValue= sqrt(x*x+y*y+z*z)
                }
            }
        }
        changeInAcceleration= abs( accelerationCurrentValue-accelerationPreviousValue)
        accelerationPreviousValue=accelerationCurrentValue



        // update textFiled
        binding.txtCurrentAccel.text="Current = $accelerationCurrentValue"
        binding.txtAccel.text="change = $changeInAcceleration"
        binding.txtPrevAccel.text="prev = $accelerationPreviousValue"

    }



    override fun onAccuracyChanged(sensor: Sensor?, i: Int) {

    }


}