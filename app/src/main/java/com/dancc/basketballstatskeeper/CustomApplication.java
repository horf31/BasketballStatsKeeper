package com.dancc.basketballstatskeeper;

import android.app.Application;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CustomApplication extends Application {
  Scheduler ioScheduler = Schedulers.io();
  Scheduler uiScheduler = AndroidSchedulers.mainThread();
}
