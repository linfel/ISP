package com.gs_chashkin.isp.presentation.thirdfragment

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import com.gs_chashkin.isp.MyAlarmReceiver
import com.gs_chashkin.isp.R
import com.gs_chashkin.isp.databinding.ThirdFragmentBinding
import com.gs_chashkin.isp.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ThirdFragment : Fragment() {

    companion object {
        fun newInstance() = ThirdFragment()
        const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
        private const val NOTIFICATION_ID = 0
        private const val ACTION_UPDATE_NOTIFICATION =
            "com.gs_chashkin.isp.ACTION_UPDATE_NOTIFICATION"
    }

    lateinit var binding: ThirdFragmentBinding
    lateinit var notificationManager: NotificationManager

    private val myReceiver = MyNotificationReceiver()
    @Inject lateinit var viewModel: ThirdViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ThirdFragmentBinding.inflate(layoutInflater)
        createNotificationChannel()
        requireActivity().registerReceiver(myReceiver, IntentFilter(ACTION_UPDATE_NOTIFICATION))
        setNotificationButtonState(true, false, false);

        binding.txtTimer.text = viewModel.number

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        binding.apply {
            notify.setOnClickListener {
                sendNotification()
            }
            alarm.setOnClickListener {
                setAlarm(5)
            }
            update.setOnClickListener {
                updateNotification()
            }
            cancel.setOnClickListener {
                cancelNotification()
            }
        }

    }

    private fun setNotificationButtonState(
        isNotifyEnabled: Boolean,
        isUpdateEnabled: Boolean,
        isCancelEnabled: Boolean
    ) {
        binding.apply {
            notify.isEnabled = isNotifyEnabled
            update.isEnabled = isUpdateEnabled
            cancel.isEnabled = isCancelEnabled
        }
    }

    private fun sendNotification() {
        val updateIntent = Intent(ACTION_UPDATE_NOTIFICATION)
        val updatePendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT)
        val notifyBuilder = getNotificationBuilder().apply {
            addAction(R.drawable.ic_action_name, "Update Notification", updatePendingIntent);
        }
        notificationManager.notify(NOTIFICATION_ID, notifyBuilder.build())
        setNotificationButtonState(false, true, true);
    }

    private fun cancelNotification() {
        notificationManager.cancel(NOTIFICATION_ID)
        setNotificationButtonState(true, false, false);
    }

    private fun updateNotification() {
        val androidImage = BitmapFactory.decodeResource(resources, R.drawable.mascot_1)
        val notifyBuilder = getNotificationBuilder().apply {
            setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(androidImage)
                    .setBigContentTitle("Notification Updated!")
            )
        }
        notificationManager.notify(NOTIFICATION_ID, notifyBuilder.build())
        setNotificationButtonState(false, false, true);
    }

    fun createNotificationChannel() {
        notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) {
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                "Mascot Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.apply {
                enableLights(true);
                setLightColor(Color.RED);
                enableVibration(true);
                setDescription("Notification from Mascot");
            }
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private fun getNotificationBuilder() : NotificationCompat.Builder {
        val notificationIntent = Intent(requireContext(), MainActivity::class.java)
        val notificationPendingIntent = PendingIntent.getActivity(
            requireContext(),
            NOTIFICATION_ID,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notifyBuilder = NotificationCompat.Builder(requireContext(), PRIMARY_CHANNEL_ID).apply {
            setContentTitle("You've been notified!")
            setContentText("This is your notification text.")
            setSmallIcon(R.drawable.ic_android);
            setContentIntent(notificationPendingIntent)
            setAutoCancel(true)
            setPriority(NotificationCompat.PRIORITY_MAX)
            setDefaults(NotificationCompat.DEFAULT_ALL)
        }
        return notifyBuilder
    }

    override fun onDestroy() {
        requireActivity().unregisterReceiver(myReceiver)
        super.onDestroy()
    }

    private fun setAlarm(number: Int) {

        val simpleDateFormat = SimpleDateFormat("HH:mm:ss")

        val am = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val now = Calendar.getInstance()
        val calendarList = ArrayList<Calendar>()
        for (i in 1..number) {
            calendarList.add(now)
        }
        val text_timer = StringBuilder()
        for (calendar in calendarList) {
            calendar.add(Calendar.SECOND, 10)
            val requestCode = Random().nextInt()
            val intent = Intent(context, MyAlarmReceiver::class.java)
            intent.putExtra("REQUEST_CODE", requestCode)
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)

            val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                am.setAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            else
                am.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            text_timer.append(simpleDateFormat.format(calendar.timeInMillis)).append("\n")
        }
        binding.txtTimer.text = text_timer
        Toast.makeText(context, "Alarm has been set", Toast.LENGTH_SHORT).show()
    }

    inner class MyNotificationReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateNotification()
        }
    }

}

