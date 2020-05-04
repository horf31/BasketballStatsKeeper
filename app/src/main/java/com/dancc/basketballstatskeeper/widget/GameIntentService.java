package com.dancc.basketballstatskeeper.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in a service on a
 * separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static helper methods.
 */
public class GameIntentService extends IntentService {
  private static final String ACTION_SHOW_GAME_DETAIL = "com.dancc.basketballstatskeeper.widget.action.show";

  private static final String EXTRA_PARAM_GAME_ID = "com.dancc.basketballstatskeeper.widget.extra.game.id";

  public GameIntentService() {
    super("GameIntentService");
  }

  /**
   * Starts this service to perform action Foo with the given parameters. If the service is already
   * performing a task this action will be queued.
   *
   * @see IntentService
   */
  // TODO: Customize helper method
  public static void startActionFoo(Context context, String param1) {
    Intent intent = new Intent(context, GameIntentService.class);
    intent.setAction(ACTION_SHOW_GAME_DETAIL);
    intent.putExtra(EXTRA_PARAM_GAME_ID, param1);
    context.startService(intent);
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    if (intent != null) {
      final String action = intent.getAction();
      if (ACTION_SHOW_GAME_DETAIL.equals(action)) {
        final String gameId = intent.getStringExtra(EXTRA_PARAM_GAME_ID);
        handleActionShowGameDetails(gameId);
      }
    }
  }

  /**
   * Handle action Foo in the provided background thread with the provided parameters.
   */
  private void handleActionShowGameDetails(String gameId) {
    Context context = getApplicationContext();
    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
    int[] appWidgetIds =
        appWidgetManager.getAppWidgetIds(new ComponentName(this, GameAppWidget.class));
  }
}
