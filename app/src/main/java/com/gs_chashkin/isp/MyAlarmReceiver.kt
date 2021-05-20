package com.gs_chashkin.isp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyAlarmReceiver  : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val requestCode = intent!!.getIntExtra("REQUEST_CODE", -1)
        Toast.makeText(context, "Alarm fired with code $requestCode", Toast.LENGTH_SHORT).show()
    }

}
