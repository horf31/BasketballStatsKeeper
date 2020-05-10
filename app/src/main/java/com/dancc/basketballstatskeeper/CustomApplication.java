package com.dancc.basketballstatskeeper;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import com.dancc.basketballstatskeeper.service.NotificationWorker;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

public class CustomApplication extends Application {
  public static final String CHANNEL_ID = "channel_id";
  boolean debugMode = true;
  Scheduler ioScheduler = Schedulers.io();
  Scheduler uiScheduler = AndroidSchedulers.mainThread();

  @Override
  public void onCreate() {
    super.onCreate();

    createNotificationChannel();

    // Recurring
    PeriodicWorkRequest notificationWorkRequest = new PeriodicWorkRequest.Builder(NotificationWorker.class, 30,
        TimeUnit.MINUTES).build();

    WorkManager.getInstance(this).enqueue(notificationWorkRequest);
  }

  private void createNotificationChannel() {
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      CharSequence name = getString(R.string.channel_name);
      String description = getString(R.string.channel_description);
      int importance = NotificationManager.IMPORTANCE_DEFAULT;
      NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
      channel.setDescription(description);
      // Register the channel with the system; you can't change the importance
      // or other notification behaviors after this
      NotificationManager notificationManager = getSystemService(NotificationManager.class);
      notificationManager.createNotificationChannel(channel);
    }
  }
}
