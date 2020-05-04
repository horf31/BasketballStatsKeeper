package com.dancc.basketballstatskeeper.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.dancc.basketballstatskeeper.R;
import com.dancc.basketballstatskeeper.db.GameDatabase;
import com.dancc.basketballstatskeeper.model.Game;
import java.util.List;

public class GridWidgetService extends RemoteViewsService {
  @Override
  public RemoteViewsFactory onGetViewFactory(Intent intent) {
    return new GridRemoteViewsFactory(this.getApplicationContext());
  }

  public class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final String TAG = "GridRemoteViewsFactory";

    Context context;
    List<Game> games;

    public GridRemoteViewsFactory(Context context) {
      this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
      games = GameDatabase.getInstance(context)
          .gameDao()
          .getAllBlocking();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
      Log.d(TAG, "getCount");
      if (games == null) return 0;
      return games.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
      Log.d(TAG, "getViewAt");
      if (games == null || games.size() == 0) return null;
      Game game = games.get(position);
      RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_cell);

      String gameId = String.format("%d", game.gameId);

      views.setTextViewText(R.id.appwidget_name, gameId);

      Intent fillIntent = new Intent();
      views.setOnClickFillInIntent(R.id.appwidget_name, fillIntent);

      return views;
    }

    @Override
    public RemoteViews getLoadingView() {
      return null;
    }

    @Override
    public int getViewTypeCount() {
      return 1;
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

    @Override
    public boolean hasStableIds() {
      return false;
    }
  }
}
