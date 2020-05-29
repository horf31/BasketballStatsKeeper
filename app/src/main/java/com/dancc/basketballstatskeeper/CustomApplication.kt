package com.dancc.basketballstatskeeper

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.work.PeriodicWorkRequest.Builder
import androidx.work.WorkManager
import com.dancc.basketballstatskeeper.R.string
import com.dancc.basketballstatskeeper.service.NotificationWorker
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit.MINUTES

const val ANNOYING_MODE = false

class CustomApplication : Application() {
  val debugMode = true
  val ioScheduler: Scheduler = Schedulers.io()
  val uiScheduler: Scheduler = AndroidSchedulers.mainThread()

  override fun onCreate() {
    super.onCreate()

    // Disable for now
    if (ANNOYING_MODE) {
      setUpNotifications();
    }
  }

  private fun setUpNotifications() {
    createNotificationChannel()

    // Recurring
    val notificationWorkRequest = Builder(
        NotificationWorker::class.java, 30,
        MINUTES).build()
    WorkManager.getInstance(this).enqueue(notificationWorkRequest)
  }

  private fun createNotificationChannel() {
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    if (VERSION.SDK_INT >= VERSION_CODES.O) {
      val name: CharSequence = getString(string.channel_name)
      val description = getString(string.channel_description)
      val importance = NotificationManager.IMPORTANCE_DEFAULT
      val channel = NotificationChannel(CHANNEL_ID, name, importance)
      channel.description = description
      // Register the channel with the system; you can't change the importance
      // or other notification behaviors after this
      val notificationManager = getSystemService(
          NotificationManager::class.java)
      notificationManager?.createNotificationChannel(channel)
    }
  }

  companion object {
    const val CHANNEL_ID = "channel_id"
  }
}