package com.dancc.basketballstatskeeper.service;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.dancc.basketballstatskeeper.CustomApplication;
import com.dancc.basketballstatskeeper.R;

public class NotificationWorker extends Worker {
  private Context context;
  private int notificationId = 0;

  public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters params) {
    super(context, params);
    this.context = context;
  }

  @NonNull
  @Override
  public Result doWork() {
    triggerNotification();
    return Result.success();
  }

  private void triggerNotification() {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CustomApplication.CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_notifications)
        .setContentTitle(context.getString(R.string.notif_title))
        .setContentText(context.getString(R.string.notif_text))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true);

    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
    notificationManager.notify(notificationId, builder.build());
  }
}
