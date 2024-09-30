package com.example.weather_app_iti.view.alert

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.weather_app_iti.model.local.LocalDataSource
import com.example.weather_app_iti.view.MainActivity
import com.example.weather_app_iti.R
import com.example.weather_app_iti.view.setting.Setting
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch




class AlertReceiver : BroadcastReceiver() {
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var mediaPlayer: MediaPlayer

    @OptIn(DelicateCoroutinesApi::class)
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onReceive(context: Context, intent: Intent) {
        val alert= Setting.alert[0]
        Setting.alert.removeAt(0)
        GlobalScope.launch {
             LocalDataSource(context).deleteAlert(alert)
        }

        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        sharedPreferences = context.getSharedPreferences("setup_setting", Context.MODE_PRIVATE)
        when (sharedPreferences.getString(Setting.alertKey, context.getString(R.string.Alarm))) {
            context.getString(R.string.Alarm) -> {
                playAlarmWithMediaPlayer(context)
                showAlarmNotification(context)
            }

            context.getString(R.string.Notify) -> showNotification(context)
        }

    }

    private fun playAlarmWithMediaPlayer(context: Context) {
        val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        mediaPlayer = MediaPlayer.create(context, alarmSound)
        mediaPlayer?.apply {
            isLooping = true // Looping the sound if you want
            start()         // Start the alarm sound
        }
    }

    fun stopAlarm() {
        mediaPlayer?.apply {
            if (isPlaying) {
                stop()
                release()
            }
        }
    }

    private fun showNotification(context: Context) {
        val channelId = "alarm_channel"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create NotificationChannel for Android 8.0+
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Alarm Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(context.getString(R.string.weather_app_alarm))
            .setContentText(context.getString(R.string.it_is_time_to_know_weather_news))
            .setSmallIcon(R.drawable.alert)
            .setSound(notificationSound)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(false)
            .build()

        notificationManager.notify(0, notification)
    }

    private fun showAlarmNotification(context: Context) {
        val channelId = "alarm_notification_channel"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Alarm Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        // Create an intent to stop the alarm
        val stopIntent = Intent(context, StopAlarmReceiver::class.java)
        val stopPendingIntent = PendingIntent.getBroadcast(
            context, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Create the custom notification layout

        // Set the Stop Alarm button to trigger the StopAlarmReceiver
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        // Build the notification
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.alert) // Set a small icon
            .setContentTitle(context.getString(R.string.weather_app_alarm))
            .setContentText(context.getString(R.string.it_is_time_to_know_weather_news))
            .addAction(android.R.drawable.ic_delete, "Stop Alarm", stopPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        NotificationManagerCompat.from(context).notify(1, notification)
    }

    inner class StopAlarmReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // Stop the alarm sound
            stopAlarm()

        }
    }
}



