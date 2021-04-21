package com.gs_chashkin.isp.isp_files

import android.os.Handler
import android.os.Looper
import android.util.Log
import java.util.*


class WeatherStation {

    private val people: MutableList<ManBehaviour> = LinkedList()

    var degrees = 0

    fun updateWeather() {
        degrees = Random().nextInt(200)
        people.forEach {
            it.getCloth(degrees = degrees)
        }

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed( {
            updateWeather()
        }, 2000)
    }

    fun addMan(manBehaviour: ManBehaviour) {
        people.add(manBehaviour)
    }

    fun removeMan(manBehaviour: ManBehaviour) {
        people.remove(manBehaviour)
    }

}

class Man(val firstName: String, lastName: String) : ManBehaviour {

    private val TAG = Man::class.java.simpleName

    override fun getCloth(degrees: Int) {
        if(degrees > 100) {
            Log.v(TAG, "warm")
        }
        else {
            Log.v(TAG, "cold")
        }
    }

}

interface ManBehaviour {

    fun getCloth(degrees : Int)

}